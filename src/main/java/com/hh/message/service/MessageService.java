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
import org.springframework.stereotype.Service;

import com.hh.system.service.inf.LoadDataTime;
import com.hh.system.util.Convert;
import com.hh.system.util.Json;
import com.hh.system.util.StaticProperties;
import com.hh.system.util.date.DateFormat;

public class MessageService {
	static {
		DwrScriptSessionManagerUtil.baseInit();
	}

	public String message() {
		return "test";
	}

	public void onPageLoad(String config) {
		
		Map<String,Object> paramMap = Json.toMap(config);
		
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		scriptSession.setAttribute("user", paramMap);
		 ScriptBuffer script = new ScriptBuffer();
		 
		Map<String, Object> map = new HashMap<String, Object>();
		List<LoadDataTime> loadDataTimeList = StaticProperties.loadDataTimeList;
		for (LoadDataTime loadDataTime : loadDataTimeList) {
			map.putAll(loadDataTime.load());
		}
		 
		 String autoMessage = Json.toStr(map);
		 script.appendCall("showMessage", autoMessage);
		 
		 scriptSession.addScript(script);
	}

	public void sendMessageAuto(String config) {
		
		Map<String,Object> paramMap = Json.toMap(config);
		paramMap.put("date", DateFormat.getDate("yyyy-MM-dd HH::mm:ss"));
		paramMap.put("type", "you");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", paramMap);
		
		final String userId = Convert.toString(paramMap.get("userId"));
		final String config2 = Json.toStr(map);
		
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if (session.getAttribute("user") == null)
					return false;
				else{
					Map<String, Object> userMap = (Map<String, Object>) session.getAttribute("user");
					return (Convert.toString(userMap.get("userId"))).equals(userId);
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
}
