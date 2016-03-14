/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: RidersDao.java,v 1.1 2007/06/01 15:24:07 rvk Exp $
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import nl.wobble.zrmiles.common.Rider;
import nl.wobble.zrmiles.common.RiderIdAndNameVO;
import nl.wobble.zrmiles.exception.ConnectionCreateException;
import nl.wobble.zrmiles.exception.DaoException;
import nl.wobble.zrmiles.exception.DaoFinderException;

/**
 * @author rvk
 */
public abstract class RidersDao extends ZrMilesDao {

    public RidersDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation) {
        super(dbclass, dburl, dbuser, dbpassword, implementation);
    }

    /**
     * Retrieves all riders from the database. Sort order can be specified.
     * @param orderBy The fields to order the output by.
     * @return a List of Rider objects.
     * @throws DaoException When a database error occurs.
     */
    public List findAll(String orderBy) throws DaoException {

        List result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findAll(" + orderBy + ")");
        //        }

        try {
            result = findAllRiders(orderBy);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end findAll(): " + result);
        //        }

        return result;
    } // findAll()

    /**
     * Retrieves all rider ids and names from the database. Sort order can be specified.
     * @param orderBy The fields to order the output by.
     * @return a List of RiderIdAndNameVO objects.
     * @throws DaoException When a database error occurs.
     */
    public List findAllAsVo(String orderBy) throws DaoException {

        List result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findAll(" + orderBy + ")");
        //        }

        try {
            result = findAllRidersAsVo(orderBy);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end findAll(): " + result);
        //        }

        return result;
    } // findAllAsVo()

    /**
     * Retrieves one rider from the database.
     * @param id The record id of the rider to retrieve.
     * @return The rider found for the specified id.
     * @throws DaoFinderException If the record with the specified id cannot be found.
     * @throws DaoException When a database error occurs.
     */
    public Rider findById(int id) throws DaoFinderException, DaoException {
        Rider result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findById(" + id + ")");
        //        }

        try {
            result = findRider(id);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end findById(): " + result);
        //        }

        return result;
    } // findById()

    /**
     * Adds a rider to the database and returns the generated key value.
     * @param rider The rider that has to be added to the database.
     * @return The generated key value of the new record.
     * @throws DaoException When a database error occurs.
     */
    public int add(Rider rider) throws DaoException {

        int result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start add(" + rider + ")");
        //        }

        try {
            result = addRider(rider);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end add(), result: " + result);
        //        }

        return result;
    } // add()

    /**
     * Updates rider information in the database.
     * @param rider The rider that has to be updated.
     * @throws DaoException When a database error occurs.
     */
    public void update(Rider rider) throws DaoException {
        try {

            updateRider(rider);

        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }
    }

    /**
     * Deletes rider information from the database.
     * @param rider The rider that has to be deleted. Only the id is used.
     * @throws DaoException When a database error occurs.
     */
    public void delete(Rider rider) throws DaoException {
        try {

            deleteRider(rider);

        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }
    }

    public abstract String getCreateTableQuery();

    public abstract String[] getCreateIndexQueries();

    /* ******************************************************************* */
    /*                       private methods                               */
    /* ******************************************************************* */

    private List findAllRiders(String orderBy)
        throws ConnectionCreateException, SQLException {
        StringBuffer query;
        ArrayList result;
        Statement statement;
        ResultSet rs;

        Integer id;
        String firstName;
        String lastName;
        String emailAddress;
        String streetAddress;
        String userName;
        String userPassword;
        int userRole;

        rs = null;
        statement = null;

        try {
            connection = getConnection();

            result = new ArrayList();

            query =
                new StringBuffer(
                    "SELECT"
                        + " ID, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, STREET_ADDRESS"
                        + " FROM ZR_RIDERS");

            if (orderBy != null) {
                query.append(" ORDER BY ");
                query.append(orderBy);
            }

            statement = connection.createStatement();
            rs = statement.executeQuery(query.toString());

            while (rs.next()) {
                id = new Integer(rs.getInt("ID"));
                firstName = rs.getString("FIRST_NAME");
                lastName = rs.getString("LAST_NAME");
                emailAddress = rs.getString("EMAIL_ADDRESS");
                streetAddress = rs.getString("STREET_ADDRESS");
                userName = null; // TODO
                userPassword = null; // TODO
                userRole = 0; // TODO

                result.add(
                    new Rider(
                        id,
                        firstName,
                        lastName,
                        emailAddress,
                        streetAddress,
                        userName,
                        userPassword,
                        userRole));
            }
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(connection);
        }

        return result;
    } // findAllRiders()

    private List findAllRidersAsVo(String orderBy)
        throws ConnectionCreateException, SQLException {
        StringBuffer query;
        ArrayList result;
        Statement statement;
        ResultSet rs;

        int id;
        String firstName;
        String lastName;

        rs = null;
        statement = null;

        try {
            connection = getConnection();

            result = new ArrayList();

            query =
                new StringBuffer(
                    "SELECT"
                        + " ID, FIRST_NAME, LAST_NAME"
                        + " FROM ZR_RIDERS");

            if (orderBy != null) {
                query.append(" ORDER BY ");
                query.append(orderBy);
            }

            statement = connection.createStatement();
            rs = statement.executeQuery(query.toString());

            while (rs.next()) {
                id = rs.getInt("ID");
                firstName = rs.getString("FIRST_NAME");
                lastName = rs.getString("LAST_NAME");

                result.add(
                    new RiderIdAndNameVO(
                        id,
                        firstName + " " +
                        lastName));
            }
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(connection);
        }

        return result;
    } // findAllRidersAsVo()

    private int addRider(Rider rider)
        throws ConnectionCreateException, DaoException, SQLException {
        String query;
        PreparedStatement statement;
        int nextId;
        int recordsAffected;

        statement = null;

        try {
            connection = getConnection();

            if (rider.getId() == null || rider.getId() == 0) {
                nextId = getNextRiderId(connection);
            } else {
                nextId = rider.getId().intValue();
            }

            query =
                "INSERT INTO ZR_RIDERS"
                    + " (ID, FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, STREET_ADDRESS"
                    + ", USER_NAME, USER_PASSWORD, USER_ROLE)"
                    + " VALUES (?, ?, ?, ?, ?, 'X', null, 0)";

            statement = connection.prepareStatement(query);
            statement.setInt(1, nextId);

            if (rider.getFirstName() == null) {
                statement.setNull(2, Types.CHAR);
            } else {
                statement.setString(2, rider.getFirstName());
            }

            if (rider.getLastName() == null) {
                statement.setNull(3, Types.CHAR);
            } else {
                statement.setString(3, rider.getLastName());
            }

            if (rider.getEmailAddress() == null) {
                statement.setNull(4, Types.CHAR);
            } else {
                statement.setString(4, rider.getEmailAddress());
            }

            if (rider.getStreetAddress() == null) {
                statement.setNull(5, Types.CHAR);
            } else {
                statement.setString(5, rider.getStreetAddress());
            }

            // TODO username, password, role

            recordsAffected = statement.executeUpdate();
            if (recordsAffected == 0) {
                throw new DaoException("No error occurred, but record NOT inserted");
            }
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }

        return nextId;
    } // addRider()

    private int getNextRiderId(Connection connection) throws SQLException {
        int result;
        StringBuffer query;
        Statement statement;
        ResultSet rs;

        query = new StringBuffer("SELECT" + " max(ID)" + " FROM ZR_RIDERS");

        statement = connection.createStatement();
        rs = statement.executeQuery(query.toString());

        if (rs.next()) {

            result = rs.getInt(1) + 1;

        } else {
            result = 1;
        }

        return result;

    } // getNextRiderId()

    private Rider findRider(int id)
        throws ConnectionCreateException, DaoFinderException, SQLException {
        String query;
        Rider result;
        Statement statement;
        ResultSet rs;

        String firstName;
        String lastName;
        String emailAddress;
        String streetAddress;
        String userName;
        String userPassword;
        int userRole;

        rs = null;
        statement = null;

        try {
            connection = getConnection();

            result = null;

            query =
                "SELECT"
                    + " FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, STREET_ADDRESS"
                    + " FROM ZR_RIDERS"
                    + " WHERE "
                    + " ID = "
                    + id;

            statement = connection.createStatement();
            rs = statement.executeQuery(query.toString());

            if (rs.next()) {
                firstName = rs.getString("FIRST_NAME");
                lastName = rs.getString("LAST_NAME");
                emailAddress = rs.getString("EMAIL_ADDRESS");
                streetAddress = rs.getString("STREET_ADDRESS");
                userName = null; // TODO
                userPassword = null; // TODO
                userRole = 0; // TODO

                result =
                    new Rider(
                        new Integer(id),
                        firstName,
                        lastName,
                        emailAddress,
                        streetAddress,
                        userName,
                        userPassword,
                        userRole);
            } else {
                throw new DaoFinderException("No rider with id " + id);
            }
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(connection);
        }

        return result;
    } // findRider()

    private void updateRider(Rider rider)
        throws ConnectionCreateException, SQLException {

        String query;
        PreparedStatement statement;

        statement = null;

        try {
            connection = getConnection();

            query =
                "UPDATE ZR_RIDERS"
                    + " SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL_ADDRESS = ?,"
                    + " STREET_ADDRESS = ?"
                    + " WHERE ID = ?";

            statement = connection.prepareStatement(query);

            statement.setString(1, rider.getFirstName());
            statement.setString(2, rider.getLastName());

            if (rider.getEmailAddress() != null) {
                statement.setString(3, rider.getEmailAddress());
            } else {
                statement.setNull(3, Types.CHAR);
            }

            if (rider.getStreetAddress() != null) {
                statement.setString(4, rider.getStreetAddress());
            } else {
                statement.setNull(4, Types.CHAR);
            }
            statement.setInt(5, rider.getId().intValue());

            statement.executeUpdate();
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    } // updateRider()

    private void deleteRider(Rider rider)
        throws ConnectionCreateException, SQLException, DaoException {

        String query;
        Statement statement;

        statement = null;

        try {

            query = "DELETE FROM ZR_RIDERS" + " WHERE ID = " + rider.getId();

            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs =
                statement.executeQuery(
                    "SELECT COUNT(*) FROM ZR_MOTORCYCLES WHERE RIDER_ID = "
                        + rider.getId());

            try {
                rs.next();
                if (rs.getInt(1) > 0) {
                    throw new DaoException("This rider still has bike entries. Delete them first!");
                }
            } catch (SQLException s) {
                throw new DaoException(s.getMessage(), s);
            } finally {
                closeResultSet(rs);
                closeStatement(statement);
                closeConnection(connection);
            }

            connection = getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(query);
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
    } // deleteRider()
}
