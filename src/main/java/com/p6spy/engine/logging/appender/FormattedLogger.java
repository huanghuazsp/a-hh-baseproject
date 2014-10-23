package com.p6spy.engine.logging.appender;

import com.hh.system.service.impl.SaveErrorThread;
import com.hh.system.service.impl.SaveSqlThread;
import com.hh.system.util.SysParam;
import com.hh.system.util.ThreadUtil;
import com.opensymphony.xwork2.ActionContext;

public abstract class FormattedLogger {
	protected String lastEntry;

	public void logSQL(int connectionId, String now, long elapsed,
			String category, String prepared, String sql) {
		if ("statement".equals(category)) {
			String logEntry =
			// now + "|" +
			"耗时：" + elapsed + "毫秒；数据连接："
					+ (connectionId == -1 ? "" : String.valueOf(connectionId))
					// + "|" + category
					// + "|" + prepared
					+ "；SQL语句：\n" + sql;
			if (!sql.contains("HH_XT_SQL")) {
				if (1 == SysParam.hhSysParam.getLogSql()) {
					logText(logEntry);
				}
				if (1 == SysParam.hhSysParam.getDataBaseSql()) {
					if (ActionContext.getContext() != null) {
						Object userObject = ActionContext.getContext()
								.getSession().get("loginuser");
						String userid = userObject == null ? "null"
								: userObject.toString();
						Object currOrgObject = ActionContext.getContext()
								.getSession().get("currOrg");
						String currOrg = currOrgObject == null ? "null"
								: currOrgObject.toString();
//						new SaveSqlThread(sql, elapsed, userid, currOrg)
//								.start();
						ThreadUtil.getFixedThreadPool().execute(new SaveSqlThread(sql, elapsed, userid, currOrg));
					}
				}
			}
		}
	}

	public abstract void logText(String text);

	// they also all need to have the last entry thing
	public void setLastEntry(String inVar) {
		lastEntry = inVar;
	}

	public String getLastEntry() {
		return lastEntry;
	}
}
