package com.hh.message.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

import com.hh.message.bean.SysMessage;
import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Check;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.StaticProperties;
import com.hh.system.util.ThreadUtil;
import com.hh.system.util.date.DateFormat;
import com.hh.usersystem.service.impl.LoginUserUtilService;

public class MessageService {
	static {
		DwrScriptSessionManagerUtil.baseInit();
	}

	private LoginUserUtilService loginUserUtilService = BeanFactoryHelper.getBeanFactory()
			.getBean(LoginUserUtilService.class);
	private SysMessageService sysMessageService = BeanFactoryHelper.getBeanFactory()
			.getBean(SysMessageService.class);
	public String message() {
		return "test";
	}

	public void onPageLoad(String config) {

		Map<String, Object> paramMap = Json.toMap(config);

		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		scriptSession.setAttribute("user", paramMap);
		ScriptBuffer script = new ScriptBuffer();

//		Map<String, Object> map = new HashMap<String, Object>();
//		List<LoadDataTime> loadDataTimeList = StaticProperties.loadDataTimeList;
//		for (LoadDataTime loadDataTime : loadDataTimeList) {
//			map.putAll(loadDataTime.load());
//		}
//		String autoMessage = Json.toStr(map);
//		script.appendCall("showMessage", autoMessage);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allMessage", sysMessageService.load().get("allMessage"));
		String autoMessage = Json.toStr(map);
		script.appendCall("showMessage", autoMessage);
		
		scriptSession.addScript(script);
	}

	public void sendMessageAuto(String config) {

		Map<String, Object> paramMap = Json.toMap(config);
		paramMap.put("date", DateFormat.getDate("yyyy-MM-dd HH::mm:ss"));
		paramMap.put("type", "you");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", paramMap);

		final String userId = Convert.toString(paramMap.get("userId"));
		final String orgId = Convert.toString(paramMap.get("orgId"));
		final String deptId = Convert.toString(paramMap.get("deptId"));
		final String sendUserId = Convert.toString(paramMap.get("sendUserId"));
		final String config2 = Json.toStr(map);
		saveMessage(paramMap, userId, orgId, deptId);

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if (session.getAttribute("user") == null)
					return false;
				else {
					Map<String, Object> userMap = (Map<String, Object>) session.getAttribute("user");
					return ((Convert.toString(userMap.get("userId"))).equals(userId)
							|| (Convert.toString(userMap.get("orgId"))).equals(orgId)
							|| (Convert.toString(userMap.get("deptId"))).equals(deptId))
							&& !sendUserId.equals(userMap.get("userId"));
				}
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();

			public void run() {
				script.appendCall("showMessage", config2);
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					System.out.println(scriptSession.getAttribute("user"));
					scriptSession.addScript(script);
				}
			}
		});
	}

	private void saveMessage(Map<String, Object> paramMap, final String userId, final String orgId,
			final String deptId) {
		SysMessage message = new SysMessage();
		message.setUserId(userId);
		message.setUserName(Convert.toString(paramMap.get("userName")));
		message.setDeptId(deptId);
		message.setDeptName(Convert.toString(paramMap.get("deptName")));
		message.setOrgId(orgId);
		message.setOrgName(Convert.toString(paramMap.get("orgName")));

		String currUserId = loginUserUtilService.findUserId();
		message.setSendUserId(currUserId);
		message.setSendUserName(loginUserUtilService.findUserName());
		message.setVcreate(currUserId);
		message.setVupdate(currUserId);
		message.setVorgid(loginUserUtilService.findOrgId());
		message.setVdeptid(loginUserUtilService.findDeptId());
		message.setVjobid(loginUserUtilService.findJobId());
		
		message.setContent(Convert.toString(paramMap.get("message")));
		
		String title = "";
		
		if (Check.isNoEmpty(message.getOrgName())) {
			title=message.getOrgName();
			message.setSendObjectId(message.getOrgId());
			message.setSendObjectName(message.getOrgName());
		}else if (Check.isNoEmpty(message.getDeptName())) {
			title=message.getDeptName();
			message.setSendObjectId(message.getDeptId());
			message.setSendObjectName(message.getDeptName());
		}else {
			title=message.getSendUserName();
			message.setSendObjectId(message.getSendUserId());
			message.setSendObjectName(message.getSendUserName());
		}
		
		message.setTitle(title);

		ThreadUtil.getThreadPool().execute(new MessageThread(message));
	}
}
