/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: ZrMilesDao.java,v 1.1 2007/06/01 15:24:07 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import nl.wobble.zrmiles.exception.ConnectionCreateException;

//import org.apache.commons.logging.Log;

//import org.apache.commons.logging.Log;

/**
 * Superclass for all DAO's
 * @author rvk
 */
public class ZrMilesDao {

    protected String dbclass;

    protected String dburl;
    protected String dbuser;
    protected String dbpassword;

    protected int implementation; // used to propagate to other dao's

    protected Connection connection;

    public static final int MAX_RECORDS_PER_SELECT = 20;

//    protected Log log;

    public ZrMilesDao(DbInfo dbInfo, int implementation) {

        this.dbclass = dbInfo.driver;
        this.dburl = dbInfo.url;
        this.dbuser = dbInfo.user;
        this.dbpassword = dbInfo.password;
        this.implementation = implementation;

    }

    public ZrMilesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation) {

        this.dbclass = dbclass;
        this.dburl = dburl;
        this.dbuser = dbuser;
        this.dbpassword = dbpassword;
        this.implementation = implementation;

    }

    protected Connection getConnection() throws ConnectionCreateException {
        Connection conn;

        //        if (log.isInfoEnabled()) {
        //            log.info("start getConnection()");
        //        }

        conn =
            ConnectionFactory.getInstance().getConnection(
                dbclass,
                dburl,
                dbuser,
                dbpassword);

        //        if (log.isInfoEnabled()) {
        //            log.info("end getConnection()");
        //        }

        return conn;
    }

    public int executeUpdate(String query)
        throws ConnectionCreateException, SQLException {
        int result;
        Connection conn = null;
        Statement stmt = null;

        result = -1;

        try {

            conn = getConnection();
            stmt = conn.createStatement();
            result = stmt.executeUpdate(query);

        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeStatement(stmt);
            closeConnection(conn);
        }

        return result;
    }

    protected void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                //                log.error("Error closing connection: " + e.getMessage(), e);
            }
        }
    }

    protected void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                //              log.error("Error closing statement: " + e.getMessage(), e);
            }
        }
    }

    protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                //              log.error("Error closing resultset: " + e.getMessage(), e);
            }
        }
    }

}
