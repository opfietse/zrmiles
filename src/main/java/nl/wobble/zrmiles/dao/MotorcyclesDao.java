/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MotorcyclesDao.java,v 1.1 2007/06/01 15:24:06 rvk Exp $
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

import nl.wobble.zrmiles.common.Motorcycle;
import nl.wobble.zrmiles.exception.ConnectionCreateException;
import nl.wobble.zrmiles.exception.DaoCreateException;
import nl.wobble.zrmiles.exception.DaoException;
import nl.wobble.zrmiles.exception.DaoFinderException;
import nl.wobble.zrmiles.exception.InvalidDataException;
import nl.wobble.zrmiles.exception.ParentNotFoundException;

/**
 * @author rvk
 */
public abstract class MotorcyclesDao extends ZrMilesDao {
    
    public MotorcyclesDao(String dbclass, String dburl, String dbuser, String dbpassword, int implementation) {
        super(dbclass, dburl, dbuser, dbpassword, implementation);

//        log = LogFactory.getLog(MySqlMotorcyclesDao.class);

    }

    /**
     * Retrieves all motorcycles from the database. Sort order can be specified.
     * @param orderBy The fields to order the output by.
     * @return a List of Motorcycle objects.
     * @throws DaoException When a database error occurs.
     */
    public List findAll(String orderBy) throws DaoException {
        
        List result;

//        if (log.isInfoEnabled()) {
//            log.info("start findAll(" + orderBy + ")");
//        }

        try {
            result = findAllMotorcyclesByRider(null, orderBy);
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
     * Retrieves one motorcycle from the database.
     * @param id The record id of the motorcycle to retrieve.
     * @return The motorcycle found for the specified id.
     * @throws DaoFinderException If the record with the specified id cannot be found.
     * @throws DaoException When a database error occurs.
     */
    public Motorcycle findById(int id) throws DaoFinderException, DaoException {
        Motorcycle result;
        
//        if (log.isInfoEnabled()) {
//            log.info("start findById(" + id + ")");
//        }

        try {
            result = findMotorcycle(id);
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
     * Retrieves the motorcycle of a particular rider from the database.
     * @param riderId The id of the rider.
     * @param orderBy The fields to order the output by.
     * @return The motorcycles found for the specified rider id.
     * @throws DaoException When a database error occurs.
     */
    public List findByRider(Integer riderId, String orderBy) throws DaoException {
        
            List result;

//            if (log.isInfoEnabled()) {
//                log.info("start findAll(" + orderBy + ")");
//            }

            try {
                result = findAllMotorcyclesByRider(riderId, orderBy);
            } catch (ConnectionCreateException cce) {
                throw new DaoException(cce.getMessage(), cce);
            } catch (SQLException sqle) {
                throw new DaoException(sqle.getMessage(), sqle);
            }

//            if (log.isInfoEnabled()) {
//                log.info("end findAll(): " + result);
//            }

            return result;
        } // findByRider()
    
    /**
     * Adds a motorcycle to the database and returns the generated key value.
     * @param motorcycle The motorcycle that has to be added to the database.
     * @return The generated key value of the new record.
     * @throws DaoException When a database error occurs.
     */
    public int add(Motorcycle motorcycle) throws DaoException {

        int result;

//        if (log.isInfoEnabled()) {
//            log.info("start add(" + motorcycle + ")");
//        }

        try {
            result = addMotorcycle(motorcycle);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (InvalidDataException ide) {
            throw new DaoException(ide.getMessage(), ide);
        } catch (ParentNotFoundException pnfe) {
            throw new DaoException(pnfe.getMessage(), pnfe);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

//        if (log.isInfoEnabled()) {
//            log.info("end add(), result: " + result);
//        }

        return result;
    } //add()
    
    /**
     * Updates motorcycle information in the database.
     * @param motorcycle The motorcycle that has to be updated.
     * @throws DaoException When a database error occurs.
     */
    public void update(Motorcycle motorcycle) throws DaoException {
        try {

            updateBike(motorcycle);

        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }
    }
    
    /**
     * Deletes motorcycle information from the database.
     * @param motorcycle The motorcycle that has to be deleted. Only the id is used.
     * @throws DaoException When a database error occurs.
     */
    public void delete(Motorcycle motorcycle) throws DaoException {
        try {

            deleteBike(motorcycle);

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

    private List findAllMotorcyclesByRider(Integer riderId, String orderBy)
        throws ConnectionCreateException, SQLException, DaoException {
        StringBuffer query;
        ArrayList result;
        Statement statement;
        ResultSet rs;
        MilesDao milesDao;

        Integer id;
        Integer dbRiderId;
        String make;
        String model;
        int year;
        int distanceUnit;

        String firstName;
        String lastName;

        rs = null;
        statement = null;
        milesDao = null;

        try {
            connection = getConnection();

            result = new ArrayList();

            query =
                new StringBuffer(
                    "SELECT"
                        + " ZR_MOTORCYCLES.ID, RIDER_ID, MAKE, MODEL, YEAR, DISTANCE_UNIT, FIRST_NAME, LAST_NAME"
                        + " FROM ZR_MOTORCYCLES, ZR_RIDERS"
                        + " WHERE ZR_RIDERS.ID = ZR_MOTORCYCLES.RIDER_ID");

            // find all bikes if riderId is null
            if (riderId != null) {
                query.append(" AND RIDER_ID = " + riderId);
            }

            if (orderBy != null) {
                query.append(" ORDER BY ");
                query.append(orderBy);
            }

            statement = connection.createStatement();
            rs = statement.executeQuery(query.toString());

            while (rs.next()) {
                id = new Integer(rs.getInt("ID"));
                dbRiderId = new Integer(rs.getInt("RIDER_ID"));
                make = rs.getString("MAKE");
                model = rs.getString("MODEL");
                year = rs.getInt("YEAR");
                distanceUnit = rs.getInt("DISTANCE_UNIT");
                firstName = rs.getString("FIRST_NAME");
                lastName = rs.getString("LAST_NAME");

                Motorcycle tmpM =
                    new Motorcycle(
                        id,
                        dbRiderId,
                        make,
                        model,
                        year,
                        distanceUnit,
                        (firstName + " " + lastName)
                        );
                try {
                    if (milesDao == null) {
                        milesDao = DaoFactory.getInstance().getMilesDao(dbclass, dburl, dbuser, dbpassword, implementation);
                    }
                    tmpM.setMiles(milesDao.findTotalForBike(id.intValue()));
                } catch (DaoCreateException dce) {
                    // we can live without these values
                }
                result.add(tmpM);
            }
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(connection);
            rs = null;
            statement = null;
            connection = null;
        }

        return result;
    }

    private int addMotorcycle(Motorcycle motorcycle)
        throws ConnectionCreateException, DaoException, SQLException, ParentNotFoundException, InvalidDataException {
        String query;
        PreparedStatement statement;
        int nextId;
        int recordsAffected;

        statement = null;

        try {

            //
            // Check if rider exists in database
            //
            if (motorcycle.getRiderId() == null) {
                throw new InvalidDataException("Rider id not set for motorcycle");
            }

            try {

                DaoFactory
                    .getInstance()
                    .getRidersDao(dbclass, dburl, dbuser, dbpassword)
                    .findById(motorcycle.getRiderId().intValue());

            } catch (DaoCreateException dce) {
                throw new DaoException(dce.getMessage(), dce);
            } catch (DaoFinderException dfe) {
                throw new ParentNotFoundException(
                    "Rider with id " + motorcycle.getRiderId() + " not found");
            }

            connection = getConnection();

            if (motorcycle.getId() == null) {
                nextId = getNextMotorcycleId(connection);
            } else {
                nextId = motorcycle.getId().intValue();
            }

            query =
                "INSERT INTO ZR_MOTORCYCLES"
                    + " (ID, RIDER_ID, MAKE, MODEL, YEAR, DISTANCE_UNIT)"
                    + " VALUES(?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(query);
            statement.setInt(1, nextId);

            statement.setInt(2, motorcycle.getRiderId().intValue());
            if (motorcycle.getMake() == null) {
                statement.setNull(3, Types.CHAR);
            } else {
                statement.setString(3, motorcycle.getMake());
            }

            if (motorcycle.getModel() == null) {
                statement.setNull(4, Types.CHAR);
            } else {
                statement.setString(4, motorcycle.getModel());
            }

            statement.setInt(5, motorcycle.getYear());
            statement.setInt(6, motorcycle.getDistanceUnit());

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
            statement = null;
            connection = null;
        }

        return nextId;
    }

    private int getNextMotorcycleId(Connection connection) throws SQLException {
        int result;
        StringBuffer query;
        Statement statement;
        ResultSet rs;
        
        query = new StringBuffer("SELECT"
        + " max(ID)"
        + " FROM ZR_MOTORCYCLES");
        
        statement = connection.createStatement();
        rs = statement.executeQuery(query.toString());

        if (rs.next()) {

            result = rs.getInt(1) + 1;

        } else {
            result = 1;
        }

        return result;
        
    }

    private Motorcycle findMotorcycle(int id)
        throws ConnectionCreateException, DaoFinderException, SQLException {
        String query;
        Motorcycle result;
        Statement statement;
        ResultSet rs;

        Integer riderId;
        String make;
        String model;
        int year;
        int distanceUnit;

        String firstName;
        String lastName;

        rs = null;
        statement = null;

        try {
            connection = getConnection();

            result = null;

            query =
                "SELECT"
                    + " RIDER_ID, MAKE, MODEL, YEAR, DISTANCE_UNIT, FIRST_NAME, LAST_NAME"
                    + " FROM ZR_MOTORCYCLES, ZR_RIDERS"
                    + " WHERE "
                    + " ZR_MOTORCYCLES.ID = "
                    + id
                    + " AND ZR_RIDERS.ID = ZR_MOTORCYCLES.RIDER_ID";

            statement = connection.createStatement();
            rs = statement.executeQuery(query.toString());

            if (rs.next()) {
                riderId = new Integer(rs.getInt("RIDER_ID"));
                make = rs.getString("MAKE");
                model = rs.getString("MODEL");
                year = rs.getInt("YEAR");
                distanceUnit = rs.getInt("DISTANCE_UNIT");
                firstName = rs.getString("FIRST_NAME");
                lastName = rs.getString("LAST_NAME");

                result =
                    new Motorcycle(
                        new Integer(id),
                        riderId,
                        make,
                        model,
                        year,
                        distanceUnit,
                        (firstName + " " + lastName)
                        );
            } else {
                throw new DaoFinderException("No motorcycle with id " + id);
            }
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeResultSet(rs);
            closeStatement(statement);
            closeConnection(connection);
            rs = null;
            statement = null;
            connection = null;
        }

        return result;
    }

    private void updateBike(Motorcycle motorcycle)
        throws ConnectionCreateException, SQLException {

        String query;
        PreparedStatement statement;

        statement = null;

        try {
            connection = getConnection();

            query =
                "UPDATE ZR_MOTORCYCLES"
                    + " SET RIDER_ID = ?, MAKE = ?, MODEL = ?,"
                    + " YEAR = ?, DISTANCE_UNIT = ?"
                    + " WHERE ID = ?";

            statement = connection.prepareStatement(query);

            statement.setInt(1, motorcycle.getRiderId().intValue());
            if (motorcycle.getMake() != null) {
                statement.setString(2, motorcycle.getMake());
            } else {
                statement.setNull(2, Types.CHAR);
            }

            if (motorcycle.getModel() != null) {
                statement.setString(3, motorcycle.getModel());
            } else {
                statement.setNull(3, Types.CHAR);
            }
            statement.setInt(4, motorcycle.getYear());
            statement.setInt(5, motorcycle.getDistanceUnit());
            statement.setInt(6, motorcycle.getId().intValue());

            statement.executeUpdate();
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeStatement(statement);
            closeConnection(connection);
            statement = null;
            connection = null;
        }
    } // updateBike()

    private void deleteBike(Motorcycle motorcycle)
        throws ConnectionCreateException, SQLException, DaoException {

        String query;
        Statement statement;

        statement = null;

        try {

            query =
                "DELETE FROM ZR_MOTORCYCLES"
                    + " WHERE ID = " + motorcycle.getId();

            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs =
                statement.executeQuery(
                    "SELECT COUNT(*) FROM ZR_MILES WHERE MOTORCYCLE_ID = "
                        + motorcycle.getId());

            try {
                rs.next();
                if (rs.getInt(1) > 0) {
                    throw new DaoException("This bike still has mileage entries. Delete them first!");
                }
            } catch (SQLException s) {
                throw new DaoException(s.getMessage(), s);
            } finally {
                closeResultSet(rs);
                closeStatement(statement);
                closeConnection(connection);
                rs = null;
                statement = null;
                connection = null;
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
            statement = null;
            connection = null;
        }
    } // deleteBike()
}
