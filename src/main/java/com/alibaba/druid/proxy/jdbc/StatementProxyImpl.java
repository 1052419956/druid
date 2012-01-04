/*
 * Copyright 1999-2011 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.proxy.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterChainImpl;

/**
 * @author wenshao<szujobs@hotmail.com>
 */
public class StatementProxyImpl extends WrapperProxyImpl implements StatementProxy {

    private final ConnectionProxy connection;
    private final Statement       statement;

    // 通过CounterEventListener来维护
    protected long                lastExecuteStartTime;
    protected long                lastExecuteStartNano;
    protected long                lastExecuteCompleteTime;
    protected long                lastExecuteCompleteNano;
    protected String              lastExecuteSql;

    protected ArrayList<String>   batchSqlList;

    public StatementProxyImpl(ConnectionProxy connection, Statement statement, long id){
        super(statement, id);
        this.connection = connection;
        this.statement = statement;
    }

    public ConnectionProxy getConnectionProxy() {
        return connection;
    }

    public Statement getStatementRaw() {
        return this.statement;
    }

    public FilterChain createChain() {
        return new FilterChainImpl(this.connection.getDirectDataSource());
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        if (batchSqlList == null) {
            batchSqlList = new ArrayList<String>();
        }

        createChain().statement_addBatch(this, sql);
        batchSqlList.add(sql);
    }

    @Override
    public void cancel() throws SQLException {
        createChain().statement_cancel(this);
    }

    @Override
    public void clearBatch() throws SQLException {
        if (batchSqlList == null) {
            batchSqlList = new ArrayList<String>();
        }

        createChain().statement_clearBatch(this);
        batchSqlList.clear();
    }

    @Override
    public void clearWarnings() throws SQLException {
        createChain().statement_clearWarnings(this);
    }

    @Override
    public void close() throws SQLException {
        createChain().statement_close(this);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_execute(this, sql);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_execute(this, sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_execute(this, sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_execute(this, sql, columnNames);
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return createChain().statement_executeBatch(this);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_executeQuery(this, sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_executeUpdate(this, sql);
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_executeUpdate(this, sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_executeUpdate(this, sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        lastExecuteSql = sql;
        return createChain().statement_executeUpdate(this, sql, columnNames);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return createChain().statement_getConnection(this);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return createChain().statement_getFetchDirection(this);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return createChain().statement_getFetchSize(this);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return createChain().statement_getGeneratedKeys(this);
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return createChain().statement_getMaxFieldSize(this);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return createChain().statement_getMaxRows(this);
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return createChain().statement_getMoreResults(this);
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return createChain().statement_getMoreResults(this, current);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return createChain().statement_getQueryTimeout(this);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return createChain().statement_getResultSet(this);
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return createChain().statement_getResultSetConcurrency(this);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return createChain().statement_getResultSetHoldability(this);
    }

    @Override
    public int getResultSetType() throws SQLException {
        return createChain().statement_getResultSetType(this);
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return createChain().statement_getUpdateCount(this);
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return createChain().statement_getWarnings(this);
    }

    @Override
    public boolean isClosed() throws SQLException {
        return createChain().statement_isClosed(this);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return createChain().statement_isPoolable(this);
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        createChain().statement_setCursorName(this, name);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        createChain().statement_setEscapeProcessing(this, enable);
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        createChain().statement_setFetchDirection(this, direction);
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        createChain().statement_setFetchSize(this, rows);
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        createChain().statement_setMaxFieldSize(this, max);
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        createChain().statement_setMaxRows(this, max);
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        createChain().statement_setPoolable(this, poolable);
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        createChain().statement_setQueryTimeout(this, seconds);
    }

    @Override
    public List<String> getBatchSqlList() {
        if (batchSqlList == null) {
            batchSqlList = new ArrayList<String>();
        }

        return batchSqlList;
    }

    @Override
    public String getBatchSql() {
        List<String> sqlList = getBatchSqlList();
        StringBuffer buf = new StringBuffer();
        for (String item : sqlList) {
            if (buf.length() > 0) {
                buf.append("\n;\n");
            }
            buf.append(item);
        }
        return buf.toString();
    }

    public String getLastExecuteSql() {
        return lastExecuteSql;
    }

    public void closeOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    public boolean isCloseOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
    
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface == Statement.class) {
            return (T) statement;
        }
        
        return super.unwrap(iface);
    }
}
