package com.p6spy.engine.common;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import com.p6spy.engine.logging.appender.*;

public class P6LogQuery {
	protected static PrintStream qlog;
	protected static String[] includeTables;
	protected static String[] excludeTables;
	protected static String[] includeCategories;
	protected static String[] excludeCategories;
	// protected static String lastEntry;
	protected static String lastStack;
	protected static P6Logger logger;

	static {
		initMethod();
	}

	public synchronized static void initMethod() {
		String appender = P6SpyOptions.getAppender();

		if (appender == null) {
			appender = "com.p6spy.engine.logging.appender.FileLogger";
		}

		try {
			logger = (P6Logger) P6Util.forName(appender).newInstance();
		} catch (Exception e1) {
			// try one more hack to load the thing
			try {
				ClassLoader loader = ClassLoader.getSystemClassLoader();
				logger = (P6Logger) loader.loadClass(appender).newInstance();

			} catch (Exception e) {
				System.err
						.println("Cannot instantiate "
								+ appender
								+ ", even on second attempt.  Logging to file log4jaux.log: "
								+ e);
			}
		}

		/**
		 * hh
		 */
		if (logger != null) {
			// if (logger instanceof FileLogger) {
			// String logfile = P6SpyOptions.getLogfile();
			// logfile = (logfile == null) ? "spy.log" : logfile;
			//
			// ((FileLogger) logger).setLogfile(logfile);
			// }
			if (logger instanceof FileLogger) {// file logger
				String logfile = P6SpyOptions.getLogfile();
				String suffix = "";
				String dataFormatingSrtyle = P6SpyOptions
						.getRollingDatePattern();
				if (dataFormatingSrtyle != null) {
					dataFormatingSrtyle = dataFormatingSrtyle.substring("'.'"
							.length());
					SimpleDateFormat sdf = new SimpleDateFormat(
							dataFormatingSrtyle);
					Date now = new Date();
					suffix = "." + sdf.format(now);
				}
				logfile = (logfile == null) ? "spy.log" + suffix : logfile
						+ suffix;// logfile
				// name
				((FileLogger) logger).setLogfile(logfile);// set log file
			}
		}

		if (P6SpyOptions.getFilter()) {
			includeTables = parseCSVList(P6SpyOptions.getInclude());
			excludeTables = parseCSVList(P6SpyOptions.getExclude());
		}
		includeCategories = parseCSVList(P6SpyOptions.getIncludecategories());
		excludeCategories = parseCSVList(P6SpyOptions.getExcludecategories());
	}

	static public PrintStream logPrintStream(String file) {
		PrintStream ps = null;
		try {
			String path = P6Util.classPathFile(file);
			file = (path == null) ? file : path;
			ps = P6Util.getPrintStream(file, P6SpyOptions.getAppend());
		} catch (IOException io) {
			P6LogQuery.logError("Error opening " + file + ", "
					+ io.getMessage());
			ps = null;
		}

		return ps;
	}

	static String[] parseCSVList(String csvList) {
		String array[] = null;
		if (csvList != null) {
			StringTokenizer tok = new StringTokenizer(csvList, ",");
			String item;
			ArrayList list = new ArrayList();
			while (tok.hasMoreTokens()) {
				item = tok.nextToken().toLowerCase().trim();
				if (item != "") {
					list.add(item.toLowerCase().trim());
				}
			}

			int max = list.size();
			Iterator it = list.iterator();
			array = new String[max];
			int i;
			for (i = 0; i < max; i++) {
				array[i] = (String) it.next();
			}
		}

		return array;
	}

	static protected void doLog(long elapsed, String category, String prepared,
			String sql) {
		doLog(-1, elapsed, category, prepared, sql);
	}

	// this is an internal method called by logElapsed
	static protected void doLogElapsed(int connectionId, long startTime,
			long endTime, String category, String prepared, String sql) {
		doLog(connectionId, (endTime - startTime), category, prepared, sql);
	}

	// this is an internal procedure used to actually write the log information
	static protected synchronized void doLog(int connectionId, long elapsed,
			String category, String prepared, String sql) {
		if (logger != null) {
			java.util.Date now = P6Util.timeNow();
			SimpleDateFormat sdf = P6SpyOptions.getDateformatter();
			String stringNow;
			if (sdf == null) {
				stringNow = Long.toString(now.getTime());
			} else {
				stringNow = sdf.format(new java.util.Date(now.getTime()))
						.trim();
			}

			logger.logSQL(connectionId, stringNow, elapsed, category, prepared,
					sql);

			boolean stackTrace = P6SpyOptions.getStackTrace();
			String stackTraceClass = P6SpyOptions.getStackTraceClass();
			if (stackTrace) {
				Exception e = new Exception();
				if (stackTraceClass != null) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String stack = sw.toString();
					if (stack.indexOf(stackTraceClass) != -1) {
						lastStack = stack;
					} else {
						e = null;
					}
				}
				if (e != null) {
					logger.logException(e);
				}
			}

			// lastEntry = logEntry;
		}

		/*
		 * java.util.Date now = P6Util.timeNow(); SimpleDateFormat sdf =
		 * P6SpyOptions.getDateformatter(); String logEntry; if (sdf == null) {
		 * logEntry = Long.toString(now.getTime()); } else { logEntry =
		 * sdf.format(new java.util.Date(now.getTime())).trim(); }
		 * 
		 * logEntry += "|"+elapsed+"|"+(connectionId==-1 ? "" :
		 * String.valueOf(connectionId))+"|"+category+"|"+prepared+"|"+sql;
		 * qlog.println(logEntry); boolean stackTrace =
		 * P6SpyOptions.getStackTrace(); String stackTraceClass =
		 * P6SpyOptions.getStackTraceClass(); if(stackTrace) {
		 * if(stackTraceClass == null) { Exception e = new Exception();
		 * e.printStackTrace(qlog); } else { ByteArrayOutputStream byteStream =
		 * new ByteArrayOutputStream(); PrintStream printStream = new
		 * PrintStream(byteStream); Exception e = new
		 * Exception("P6Spy Stack Trace Log"); e.printStackTrace(printStream);
		 * String stack = byteStream.toString();
		 * if(stack.indexOf(stackTraceClass) != -1) { lastStack = stack;
		 * e.printStackTrace(qlog); } } }
		 * 
		 * lastEntry = logEntry;
		 */
	}

	static boolean isLoggable(String sql) {
		return (P6SpyOptions.getFilter() == false || queryOk(sql));
	}

	static boolean isCategoryOk(String category) {
		return (includeCategories == null || includeCategories.length == 0 || foundCategory(
				category, includeCategories))
				&& !foundCategory(category, excludeCategories);
	}

	static boolean foundCategory(String category, String categories[]) {
		if (categories != null) {
			for (int i = 0; i < categories.length; i++) {
				if (category.equals(categories[i])) {
					return true;
				}
			}
		}
		return false;
	}

	static boolean queryOk(String sql) {
		if (P6SpyOptions.getStringMatcherEngine() != null
				&& P6SpyOptions.getSQLExpression() != null) {
			return sqlOk(sql);
		} else
			return ((includeTables == null || includeTables.length == 0 || foundTable(
					sql, includeTables))) && !foundTable(sql, excludeTables);
	}

	static boolean sqlOk(String sql) {
		String sqlexpression = P6SpyOptions.getSQLExpression();
		try {
			return P6SpyOptions.getStringMatcherEngine().match(sqlexpression,
					sql);
		} catch (MatchException e) {
			P6LogQuery.logError("Exception during matching sqlexpression ["
					+ sqlexpression + "] to sql [" + sql + "]: ");
			return false;
		}

	}

	static boolean foundTable(String sql, String tables[]) {
		sql = sql.toLowerCase();
		boolean ok = false;
		int i;
		if (tables != null) {
			for (i = 0; !ok && i < tables.length; i++) {
				ok = tableOk(sql, tables[i]);
			}
		}

		return ok;
	}

	static boolean tableOk(String sql, String table) {
		try {
			return P6SpyOptions.getStringMatcherEngine().match(table, sql);
		} catch (MatchException e) {
			P6LogQuery.logError("Exception during matching expression ["
					+ table + "] to sql [" + sql + "]: ");
			return false;
		}
	}

	// ----------------------------------------------------------------------------------------------------------
	// public accessor methods for logging and viewing query data
	// ----------------------------------------------------------------------------------------------------------

	static public void clearLastStack() {
		lastStack = null;
	}

	static public String getLastEntry() {
		// return lastEntry;
		return logger.getLastEntry();
	}

	static public String getLastStack() {
		return lastStack;
	}

	static public String[] getIncludeTables() {
		return includeTables;
	}

	static public String[] getExcludeTables() {
		return excludeTables;
	}

	static public void setIncludeTables(String _includeTables) {
		P6LogQuery.includeTables = P6LogQuery.parseCSVList(_includeTables);
	}

	static public void setExcludeTables(String _excludeTables) {
		P6LogQuery.excludeTables = P6LogQuery.parseCSVList(_excludeTables);
	}

	static public void setIncludeCategories(String _includeCategories) {
		P6LogQuery.includeCategories = P6LogQuery
				.parseCSVList(_includeCategories);
	}

	static public void setExcludeCategories(String _excludeCategories) {
		P6LogQuery.excludeCategories = P6LogQuery
				.parseCSVList(_excludeCategories);
	}

	// this a way for an external to dump an unrestricted line of text into the
	// log
	// useful for the JSP demarcation tool
	static public void logText(String text) {
		logger.logText(text);
		// qlog.println(text);
	}

	static public void log(String category, String prepared, String sql) {
		// if (qlog != null) {
		if (logger != null) {
			doLog(-1, category, prepared, sql);
		}
	}

	static public void logElapsed(int connectionId, long startTime,
			String category, String prepared, String sql) {
		logElapsed(connectionId, startTime, System.currentTimeMillis(),
				category, prepared, sql);
	}

	static public void logElapsed(int connectionId, long startTime,
			long endTime, String category, String prepared, String sql) {
		if (logger != null && meetsThresholdRequirement(endTime - startTime)
				&& isLoggable(sql) && isCategoryOk(category)) {
			doLogElapsed(connectionId, startTime, endTime, category, prepared,
					sql);
		} else if (isDebugOn()) {
			logDebug("P6Spy intentionally did not log category: " + category
					+ ", statement: " + sql + "  Reason: logger=" + logger
					+ ", isLoggable=" + isLoggable(sql) + ", isCategoryOk="
					+ isCategoryOk(category));
		}
	}

	// ->JAW: new method that checks to see if this statement should be logged
	// based
	// on whether on not it has taken greater than x amount of time.
	static private boolean meetsThresholdRequirement(long timeTaken) {
		long executionThreshold = P6SpyOptions.getExecutionThreshold();

		if (executionThreshold <= 0) {
			return true;
		} else if (timeTaken > executionThreshold) {
			return true;
		} else {
			return false;
		}
	}

	// <-JAW

	static public void logInfo(String sql) {
		if (logger != null && isCategoryOk("info")) {
			doLog(-1, "info", "", sql);
		}
	}

	static public boolean isDebugOn() {
		return isCategoryOk("debug");
	}

	static public void logDebug(String sql) {
		if (isDebugOn()) {
			if (logger != null) {
				doLog(-1, "debug", "", sql);
			} else {
				System.err.println(sql);
			}
		}
	}

	static public void logError(String sql) {
		System.err.println("Warning: " + sql);
		if (logger != null) {
			doLog(-1, "error", "", sql);
		}
	}

}
