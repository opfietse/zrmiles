/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: ConnectionFactory.java,v 1.1 2007/06/01 15:24:07 rvk Exp $
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
import java.sql.DriverManager;
import java.sql.SQLException;

import nl.wobble.zrmiles.exception.ConnectionCreateException;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Creates connections to the database.
 * Alter the implementation to use by editing and recompile.
 * @author rvk
 */
public class ConnectionFactory {

    private static final int USE_MYSQL = 0;
    private static final int IMPLEMENTATION_TO_USE = USE_MYSQL;

    private static ConnectionFactory instance = new ConnectionFactory();

//    private Log log;

    private ConnectionFactory() {
    }

    /**
     * Returns an instance of the factory.
     * @return A factory
     */
    public static ConnectionFactory getInstance() {
        return instance;
    }
    
    /**
     * Returns a RidersDao.
     * @return A DAO for the riders data.
     * @throws DaoCreateException
     */
    public Connection getConnection(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword)
        throws ConnectionCreateException {

        Connection connection;

//        log = LogFactory.getLog(this.getClass());
//        if (log.isInfoEnabled()) {
//            log.info("start getConnection(dburl: " + dburl + ")");
//        }

        connection = null;
        try {
            switch (IMPLEMENTATION_TO_USE) {
                case USE_MYSQL :
                    Class.forName(dbclass);
                    connection =
                        DriverManager.getConnection(dburl, dbuser, dbpassword);
                    break;
                default :
                    connection = null;
                    break;
            }
        } catch (ClassNotFoundException cnfe) {

//            log.error("Driver " + dbclass + " not found");

            throw new ConnectionCreateException(
                "Driver " + dbclass + " not found",
                cnfe);

        } catch (SQLException sqle) {

//            log = LogFactory.getLog(this.getClass());
//            log.error("Error creating connection (" + sqle.getMessage() +")");

            throw new ConnectionCreateException(
                "Error creating connection, errorcode: "
                    + sqle.getErrorCode()
                    + ", message: "
                    + sqle.getMessage(),
                sqle);
        }

        if (connection == null) {
            throw new ConnectionCreateException("Don't know what implementation to use");
        }

//        if (log.isInfoEnabled()) {
//            log.info("end getConnection()");
//        }

        return connection;
    }
}
