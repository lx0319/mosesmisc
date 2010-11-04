package com.javaeye.liulu.test;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import javax.sql.DataSource;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;

import com.ibatis.common.resources.Resources;
import com.ibatis.db.util.ScriptRunner;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public abstract class BaseSqlMapTest extends DatabaseTestCase {
    protected static SqlMapClient sqlMap;

    protected IDatabaseConnection getConnection() throws Exception {
        return new DatabaseConnection(getJdbcConnection());
    }
    protected void setUp() throws Exception {
        super.setUp();
        init();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
        getConnection().close();
        if (sqlMap != null) {
            DataSource ds = sqlMap.getDataSource();
            Connection conn = ds.getConnection();
            conn.close();
        }
    }
    protected void init() throws Exception {
        initSqlMap("com/javaeye/liulu/maps/SqlMapConfig.xml", null);
    }
    protected SqlMapClient getSqlMapClient() {
        return sqlMap;
    }
    protected void initSqlMap(String configFile, Properties props)
            throws Exception {
        Reader reader = Resources.getResourceAsReader(configFile);
        sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader, props);
        reader.close();
    }
    protected void initScript(String script) throws Exception {
        DataSource ds = sqlMap.getDataSource();
        Connection conn = ds.getConnection();
        
        Reader reader = Resources.getResourceAsReader(script);
        ScriptRunner runner = new ScriptRunner();
        runner.setStopOnError(false);
        runner.setLogWriter(null);
        runner.setErrorLogWriter(null);

        runner.runScript(conn, reader);
        conn.commit();
        conn.close();
        reader.close();
    }
    private Connection getJdbcConnection() throws Exception {
    	/*
        Properties props = new Properties();
        props.load(Resources.getResourceAsStream("properties/database.properties"));
        Class driver = Class.forName(props.getProperty("driver"));
        Connection conn = DriverManager.getConnection(props.getProperty("url"), 
                props.getProperty("username"), props.getProperty("password"));
                */
    	Class driver = Class.forName("com.mysql.jdbc.Driver");
    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ibatis_schema","root","1234");
        return conn;
    }
}