package com.p6spy.engine.logging.appender;

import com.hh.system.service.impl.SaveErrorThread;
import com.hh.system.service.impl.SaveSqlThread;
import com.hh.system.util.SysParam;
import com.hh.system.util.ThreadUtil;
import com.hh.usersystem.IUser;
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
			if (!sql.toLowerCase().contains("sys_sql") && !sql.toLowerCase().contains("sys_oper_log")) {
				if (1 == SysParam.sysParam.getLogSql()) {
					logText(logEntry);
				}
				if (1 == SysParam.sysParam.getDataBaseSql()) {
					if (ActionContext.getContext() != null) {
						IUser userObject = (IUser)ActionContext.getContext().getSession()
								.get("loginuser");
						String userid = "";
						String orgid = "";
						if (userObject!=null) {
							userid=userObject.getId();
							orgid=userObject.getJobId();
						}
						ThreadUtil.getThreadPool().execute(new SaveSqlThread(sql, elapsed, userid, orgid));
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
