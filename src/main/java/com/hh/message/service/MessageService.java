package com.hh.message.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.LogUtil;
import com.hh.system.util.date.DateFormat;
import com.hh.usersystem.service.impl.LoginUserUtilService;

public class MessageService {
	static {
		DwrScriptSessionManagerUtil.baseInit();
		// Timer timer = new Timer();
		// timer.schedule(new DwrTask(), 1000,1 * 60 * 1000);
	}

	private SysMessageService sysMessageService = BeanFactoryHelper
			.getBeanFactory().getBean(SysMessageService.class);

	public String message() {
		return "test";
	}

	public void onPageLoad(String config) {

		Map<String, Object> paramMap = Json.toMap(config);

		ScriptSession scriptSession = WebContextFactory.get()
				.getScriptSession();
		scriptSession.setAttribute("user", paramMap);
		ScriptBuffer script = new ScriptBuffer();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allMessage", sysMessageService.load().get("allMessage"));
		String autoMessage = Json.toStr(map);
		script.appendCall("showMessage", autoMessage);

		scriptSession.addScript(script);
	}

	public void sendMessageAuto(String config) {
		if (Check.isEmpty(config)) {
			LogUtil.info("保持session");
			return;
		}

		Map<String, Object> paramMap = Json.toMap(config);
		paramMap.put("date", DateFormat.getDate("yyyy-MM-dd HH::mm:ss"));
		paramMap.put("type", "you");
		SysMessage sysMessage = sysMessageService.saveMessage(paramMap);

		paramMap.put("objectId", sysMessage.getObjectId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", paramMap);

		final String sendUserId = Convert.toString(paramMap.get("sendUserId"));
		final String toObjectId = sysMessage.getToObjectId();
		final String config2 = Json.toStr(map);

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if (session.getAttribute("user") == null)
					return false;
				else {
					Map<String, Object> userMap = (Map<String, Object>) session
							.getAttribute("user");
					return ((Convert.toString(userMap.get("userId")))
							.equals(toObjectId)
							|| (Convert.toString(userMap.get("orgId")))
									.equals(toObjectId)
							|| (Convert.toString(userMap.get("deptId")))
									.equals(toObjectId)
							|| (Convert.toString(userMap.get("usGroupIds")))
									.equals(toObjectId) || (Convert
								.toString(userMap.get("sysGroupIds")))
							.equals(toObjectId))
							&& !sendUserId.equals(userMap.get("userId"));
				}
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				script.appendCall("showMessage", config2);
				Collection<ScriptSession> sessions = Browser
						.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					// System.out.println(scriptSession.getAttribute("user"));
					scriptSession.addScript(script);
				}
			}
		});
	}

}
