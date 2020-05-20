package project.models;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.log4j.Logger;
import project.core.TestRunParams;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {

    private static Logger log = Logger.getLogger("");

    private String server;

    private String port;

    private String username;

    private String password;

    private Connection conn;

    private Statement stmt;

    private ResultSet rs;

    public Db(
            final String server,
            final String port,
            final String username,
            final String password,
            final Connection conn,
            final Statement stmt) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.conn = conn;
        this.stmt = stmt;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConn() {
        return conn;
    }

    public Statement getStmt() {
        return stmt;
    }

    public String getPort() {
        return port;
    }

    public String getServer() {
        return server;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public void connect() {
        log.info("connect to DB on " + TestRunParams.getDbServer() + ":" + TestRunParams.getDbPort());
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(TestRunParams.getDbUsername());
        dataSource.setPassword(TestRunParams.getDbPassword());
        dataSource.setServerName(TestRunParams.getDbServer());
        dataSource.setPort(Integer.valueOf(TestRunParams.getDbPort()));
        try {
            this.conn = dataSource.getConnection();
            log.info("connection to DB is established");
        } catch (SQLException e) {
            log.warn("something went wrong during connection establishing");
            // e.printStackTrace();
        }
        try {
            this.stmt = conn.createStatement();
            log.info("statement is created");
        } catch (SQLException e) {
            log.warn("something went wrong during statement creation");
            // e.printStackTrace();
        }
    }

    public void close() {
        log.info("closing DB connection");
        if (("" + rs).equalsIgnoreCase("null") == false) {
            try {
                log.info("closing result set");
                rs.close();
                log.info("result set closed");
            } catch (SQLException e) {
                log.warn("something went wrong during result set closing");
                // e.printStackTrace();
            }
        }
        try {
            stmt.close();
            log.info("statement closed");
        } catch (SQLException e) {
            log.warn("something went wrong during statement closing");
            // e.printStackTrace();
        }
        try {
            conn.close();
            log.info("connection closed");
        } catch (SQLException e) {
            log.warn("something went wrong during connection closing");
            // e.printStackTrace();
        }
    }

    public void query(String sql) {
        try {
            log.info("execute query");
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            this.rs = rs;
        } catch (SQLException e) {
            log.warn("something went wrong during query execution");
            // e.printStackTrace();
        }
    }

    public ResultSet getQueryResultSet() {
        return rs;
    }

    public String getQueryStringResult() {
        String res = null;
        try {
            res = rs.getString(1);
            log.info("query string result is: " + res);
        } catch (SQLException e) {
            log.warn("something went wrong during getting a string result from result set");
            // e.printStackTrace();
        }
        return res;
    }

    public String getQueryStringResult(String column) {
        String res = null;
        try {
            res = rs.getString(column);
            log.info("query string result is: " + res);
        } catch (SQLException e) {
            log.warn("something went wrong during getting a string result from result set");
            // e.printStackTrace();
        }
        return res;
    }
}
