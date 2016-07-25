package com.hh.message.util;

import java.util.Collection;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

import com.hh.system.service.impl.BeanFactoryHelper;
import com.hh.system.service.impl.SystemFileService;
import com.hh.system.util.Convert;
import com.hh.system.util.LogUtil;

public class DwrTask extends TimerTask {
	static{
		LogUtil.info("初始化，DwrTask...");
	}
	@Override
	public void run() {
		LogUtil.info("像客户端推送保持session！");
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				return true;
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				script.appendCall("showMessage", "");
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}

}
