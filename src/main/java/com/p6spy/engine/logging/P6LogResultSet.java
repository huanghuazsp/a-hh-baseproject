package com.p6spy.engine.logging;

import com.p6spy.engine.spy.*;
import com.p6spy.engine.common.*;
import java.sql.*;

public class P6LogResultSet extends P6ResultSet implements ResultSet {

	public P6LogResultSet(P6Factory factory, ResultSet resultSet,
			P6Statement statement, String preparedQuery, String query) {
		super(factory, resultSet, statement, preparedQuery, query);
	}

	public boolean next() throws SQLException {
		long startTime = System.currentTimeMillis();
		try {
			return super.next();
		} finally {
			P6Connection p6connection = (P6Connection) this.statement
					.getConnection();
			P6LogQuery.logElapsed(p6connection.getId(), startTime, "result",
					preparedQuery, query);
		}
	}

}
