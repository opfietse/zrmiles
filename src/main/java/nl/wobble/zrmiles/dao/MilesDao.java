/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MilesDao.java,v 1.1 2007/06/01 15:24:07 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import nl.wobble.zrmiles.common.Constants;
import nl.wobble.zrmiles.common.DateUtil;
import nl.wobble.zrmiles.common.MilesEntry;
import nl.wobble.zrmiles.common.MilesRankingVO;
import nl.wobble.zrmiles.common.NameMileageVO;
import nl.wobble.zrmiles.exception.ConnectionCreateException;
import nl.wobble.zrmiles.exception.DaoCreateException;
import nl.wobble.zrmiles.exception.DaoException;
import nl.wobble.zrmiles.exception.DaoFinderException;
import nl.wobble.zrmiles.exception.InvalidDataException;
import nl.wobble.zrmiles.exception.ParentNotFoundException;

// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;

/**
 * @author rvk
 */
public abstract class MilesDao extends ZrMilesDao {

    // private static final Log log = LogFactory.getLog(MilesDao.class);

    public MilesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation) {

        super(dbclass, dburl, dbuser, dbpassword, implementation);

    }

    /**
     * Retrieves all miles entries from the database. Sort order can be specified.
     * @param orderBy The fields to order the output by.
     * @return a List of MilesEntry objects.
     * @throws DaoException When a database error occurs.
     */
    public List findAll(String orderBy) throws DaoException {

        List result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findAll(" + orderBy + ")");
        //        }

        try {
            result = findAllMilesEntriesByMotorcycle(null, orderBy);
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
     * Retrieves one miles entry from the database.
     * @param id The record id of the entry to retrieve.
     * @return The entry found for the specified id.
     * @throws DaoFinderException If the record with the specified id cannot be found.
     * @throws DaoException When a database error occurs.
     */
    public MilesEntry findById(int id)
        throws DaoFinderException, DaoException {
        MilesEntry result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findById(" + id + ")");
        //        }

        try {
            result = findMilesEntry(id);
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
     * Retrieves the miles entries for a particular motorcycle from the database.
     * @param motorcycleId The id of the motorcycle.
     * @param orderBy The fields to order the output by.
     * @return The entries found for the specified motorcycle id.
     * @throws DaoException When a database error occurs.
     */
    public List findByMotorcycle(int motorcycleId, String orderBy)
        throws DaoException {

        List result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findAll(" + orderBy + ")");
        //        }

        try {
            result =
                findAllMilesEntriesByMotorcycle(
                    new Integer(motorcycleId),
                    orderBy);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end findAll(): " + result);
        //        }

        return result;
    } // findByMotorcycle()

    /**
     * Retrieves the miles entries for a particular rider (all his/her bikes) from the database.
     * @param riderId The id of the rider.
     * @param orderBy The fields to order the output by.
     * @return The entries found for the specified rider id.
     * @throws DaoException When a database error occurs.
     */
    public List findByRider(int riderId, String orderBy) throws DaoException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Retrieves the miles for the current year, totalled by rider.
     * @param year The year to retieve the totals for.
     * @return The miles for this year, totalled per rider.
     * @throws DaoException When a database error occurs.
     */
    public List findTotalsForYear(int year) throws DaoException {
        List result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findTotalsForYear(" + year + ")");
        //        }

        try {
            //result = findMilesForYear(year);
            result = findMilesForMonths(1, year, 12, year, true);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end findTotalsForYear(): " + result);
        //        }

        return result;
    } // findTotalsForYear()

    /**
     * Retrieves the miles for a number of months, totalled by rider.
     * @param startMonth The first month to retieve the totals for (1-12).
     * @param startYear The year the first month is in.
     * @param endMonth The last month to retieve the totals for (1-12).
     * @param endYear The year the last month is in.
     * @return The miles for this period, totalled per rider.
     * @throws DaoException When a database error occurs.
     */
    public List findTotalsForMonths(
        int startMonth,
        int startYear,
        int endMonth,
        int endYear)
        throws DaoException {
        List result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findTotalsForYear(" + year + ")");
        //        }

        try {
            result =
                findMilesForMonths(
                    startMonth,
                    startYear,
                    endMonth,
                    endYear,
                    true);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end findTotalsForYear(): " + result);
        //        }

        return result;
    } // findTotalsForMonths()

    /**
     * Makes a list of miles entries for a certain period of time and a certain
     * selection of riders.
     * @param startMonth The first month to retieve the entries for (1-12).
     * @param startYear The year the first month is in.
     * @param endMonth The last month to retieve the entries for (1-12).
     * @param endYear The year the last month is in.
     * @param riders rider id's to get data for.
     * @return The miles entries for this period.
     * @throws DaoException When a database error occurs.
     */
    public List findMilesForRanking(
        int startMonth,
        int startYear,
        int endMonth,
        int endYear,
        int[] riders)
        throws DaoException {

        List result;

        //        if (log.isInfoEnabled()) {
        //            log.info("start findTotalsForYear(" + year + ")");
        //        }

        try {
            result =
                selectMilesForRanking(
                    startMonth,
                    startYear,
                    endMonth,
                    endYear,
                    riders);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        //        if (log.isInfoEnabled()) {
        //            log.info("end findTotalsForYear(): " + result);
        //        }

        return result;
    } // findTotalsForMonths()

    /**
     * Retrieves the total miles for a bike.
     * @param motorcycleId The bike to retieve the total for.
     * @return The miles for this bike.
     * @throws DaoException When a database error occurs.
     */
    public Integer findTotalForBike(int motorcycleId) throws DaoException {
        Integer result;

        try {
            result = findMilesForBike(motorcycleId);
        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }

        return result;
    } // findTotalForBike()

    /**
     * Adds a miles entry to the database.
     * @param rider The motorcycle that has to be added to the database.
     * @return The generated key value of the new record.
     * @throws DaoException When a database error occurs.
     */
    public void add(MilesEntry milesEntry) throws DaoException {

        //        if (log.isInfoEnabled()) {
        //            log.info("start add(" + milesEntry + ")");
        //        }

        try {

            addMilesEntry(milesEntry);

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
        //            log.info("end add()");
        //        }

    } // add()

    /**
     * Updates miles information in the database.
     * @param milesEntry The entry that has to be updated.
     * @throws DaoException When a database error occurs.
     */
    public void update(MilesEntry milesEntry) throws DaoException {
        try {

            updateMiles(milesEntry);

        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }
    } // update()

    /**
     * Deletes motorcycle information from the database.
     * @param rider The motorcycle that has to be deleted. Only the id is used.
     * @throws DaoException When a database error occurs.
     */
    public void delete(MilesEntry milesEntry) throws DaoException {
        try {

            deleteMiles(milesEntry);

        } catch (ConnectionCreateException cce) {
            throw new DaoException(cce.getMessage(), cce);
        } catch (SQLException sqle) {
            throw new DaoException(sqle.getMessage(), sqle);
        }
    } // delete()

    public abstract String getCreateTableQuery();

    public abstract String[] getCreateIndexQueries();

    /* ******************************************************************* */
    /*                       private methods                               */
    /* ******************************************************************* */

    private List findAllMilesEntriesByMotorcycle(
        Integer motorcycleId,
        String orderBy)
        throws ConnectionCreateException, SQLException {

        StringBuffer query;
        ArrayList result;
        Statement statement;
        ResultSet rs;

        Integer id;
        Integer dbMotorcycleId;
        Date date;
        int odometerReading;
        int correction;
        String userComment;

        String firstName;
        String lastName;
        String make;
        String model;

        rs = null;
        statement = null;

        try {
            connection = getConnection();

            result = new ArrayList();

            query =
                new StringBuffer(
                    "SELECT"
                        + " ZR_MILES.ID, MOTORCYCLE_ID, MILES_DATE, ODOMETER_READING, CORRECTION_MILES, USER_COMMENT"
                        + ", MAKE, MODEL, FIRST_NAME, LAST_NAME"
                        + " FROM ZR_MILES, ZR_MOTORCYCLES, ZR_RIDERS"
                        + " WHERE ZR_MOTORCYCLES.ID = ZR_MILES.MOTORCYCLE_ID"
                        + " AND ZR_RIDERS.ID = ZR_MOTORCYCLES.RIDER_ID");

            // find all entries if motorcycleId is null
            if (motorcycleId != null) {
                query.append(" AND MOTORCYCLE_ID = " + motorcycleId);
            }

            if (orderBy != null) {
                query.append(" ORDER BY ");
                query.append(orderBy);
            }

            statement = connection.createStatement();
            rs = statement.executeQuery(query.toString());

            while (rs.next()) {
                id = new Integer(rs.getInt("ID"));
                dbMotorcycleId = new Integer(rs.getInt("MOTORCYCLE_ID"));
                date = rs.getDate("MILES_DATE");
                odometerReading = rs.getInt("ODOMETER_READING");
                correction = rs.getInt("CORRECTION_MILES");
                userComment = rs.getString("USER_COMMENT");
                make = rs.getString("MAKE");
                model = rs.getString("MODEL");
                firstName = rs.getString("FIRST_NAME");
                lastName = rs.getString("LAST_NAME");

                result.add(
                    new MilesEntry(
                        id,
                        dbMotorcycleId,
                        date,
                        odometerReading,
                        correction,
                        userComment,
                        (make + " " + model),
                        (firstName + " " + lastName)));
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

    private void addMilesEntry(MilesEntry milesEntry)
        throws
            ConnectionCreateException,
            DaoException,
            SQLException,
            ParentNotFoundException,
            InvalidDataException {
        String query;
        PreparedStatement statement;
        int recordsAffected;

        statement = null;

        try {

            //
            // Check if motorcycle exists in database
            //
            if (milesEntry.getMotorcycleId() == null) {
                throw new InvalidDataException("Motorcycle id not set for milesEntry");
            }

            try {

                DaoFactory
                    .getInstance()
                    .getMotorcyclesDao(dbclass, dburl, dbuser, dbpassword)
                    .findById(milesEntry.getMotorcycleId().intValue());

            } catch (DaoCreateException dce) {
                throw new DaoException(dce.getMessage(), dce);
            } catch (DaoFinderException dfe) {
                throw new ParentNotFoundException(
                    "Motorcycle with id "
                        + milesEntry.getMotorcycleId()
                        + " not found");
            }

            connection = getConnection();

            query =
                "INSERT INTO ZR_MILES"
                    + " (ID, MOTORCYCLE_ID, MILES_DATE,"
                    + " ODOMETER_READING, CORRECTION_MILES, USER_COMMENT)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(query);

            if (milesEntry.getId() == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, milesEntry.getId().intValue());
            }

            statement.setInt(2, milesEntry.getMotorcycleId().intValue());
            statement.setDate(3, DateUtil.dateToSqlDate(milesEntry.getDate()));

            statement.setInt(4, milesEntry.getOdometerReading());

            statement.setInt(5, milesEntry.getCorrection());

            if (milesEntry.getUserComment() == null) {
                statement.setNull(6, Types.VARCHAR);
            } else {
                statement.setString(6, milesEntry.getUserComment());
            }

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

    }

    private MilesEntry findMilesEntry(int id)
        throws ConnectionCreateException, DaoFinderException, SQLException {
        String query;
        MilesEntry result;
        Statement statement;
        ResultSet rs;

        Integer motorcycleId;
        Date date;
        int odometerReading;
        int correction;
        String userComment;

        rs = null;
        statement = null;

        try {
            connection = getConnection();

            result = null;

            query =
                "SELECT"
                    + " MOTORCYCLE_ID, MILES_DATE, ODOMETER_READING, CORRECTION_MILES, USER_COMMENT"
                    + " FROM ZR_MILES"
                    + " WHERE "
                    + " ID = "
                    + id;

            statement = connection.createStatement();
            rs = statement.executeQuery(query.toString());

            if (rs.next()) {
                motorcycleId = new Integer(rs.getInt("MOTORCYCLE_ID"));
                date = DateUtil.sqlDateToDate(rs.getDate("MILES_DATE"));
                odometerReading = rs.getInt("ODOMETER_READING");
                correction = rs.getInt("CORRECTION_MILES");
                userComment = rs.getString("USER_COMMENT");

                result =
                    new MilesEntry(
                        new Integer(id),
                        motorcycleId,
                        date,
                        odometerReading,
                        correction,
                        userComment);
            } else {
                throw new DaoFinderException("No milesEntry with id " + id);
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

    private Integer findMilesForBike(int id)
        throws ConnectionCreateException, SQLException {

        String query;
        String firstPartOfQuery;
        Integer result;
        Statement statement;
        ResultSet rs;

        int startMiles;
        int endMiles;

        rs = null;
        statement = null;
        startMiles = 0;
        endMiles = 0;

        try {
            connection = getConnection();

            result = null;

            firstPartOfQuery =
                "SELECT"
                    + " ODOMETER_READING"
                    + " FROM ZR_MILES"
                    + " WHERE "
                    + " MOTORCYCLE_ID = "
                    + id;

            query = firstPartOfQuery + " ORDER BY ODOMETER_READING ASC";

            statement = connection.createStatement();
            statement.setMaxRows(1);
            rs = statement.executeQuery(query.toString());

            if (rs.next()) {
                startMiles = rs.getInt("ODOMETER_READING");
                closeResultSet(rs);
                closeStatement(statement);

                query = firstPartOfQuery + " ORDER BY ODOMETER_READING DESC";
                statement = connection.createStatement();
                statement.setMaxRows(1);

                rs = statement.executeQuery(query.toString());

                if (rs.next()) {
                    endMiles = rs.getInt("ODOMETER_READING");
                }

                result = new Integer(endMiles - startMiles);
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
    } // findTotalsByBike()

    //    private List findMilesForYear(int year)
    //        throws SQLException, ConnectionCreateException {
    //        StringBuffer query;
    //        ArrayList result;
    //        PreparedStatement statement;
    //        ResultSet rs;
    //
    //        Date startDate;
    //        Date endDate;
    //        GregorianCalendar calendar;
    //
    //        int riderId;
    //        int motorcycleId;
    //        int distanceUnit;
    //        int odometerReading;
    //        int correction;
    //        int lastRiderId;
    //        int firstOdometer;
    //        int lastOdometer;
    //        int totalMiles;
    //        int totalKilometers;
    //        int lastMotocycleId;
    //        int lastDistanceUnit;
    //        int totalCorrections;
    //        List mileages;
    //
    //        String firstName;
    //        String lastName;
    //        String owner;
    //
    //        calendar = new GregorianCalendar();
    //        //
    //        // Make first day of this year
    //        calendar.set(Calendar.DAY_OF_MONTH, 1);
    //        calendar.set(Calendar.MONTH, 0);
    //        calendar.set(Calendar.YEAR, year);
    //        calendar.set(Calendar.HOUR_OF_DAY, 0);
    //        calendar.set(Calendar.MINUTE, 0);
    //        calendar.set(Calendar.MILLISECOND, 0);
    //        startDate = calendar.getTime();
    //
    //        // Make first day of next year
    //        calendar.set(Calendar.DAY_OF_MONTH, 1);
    //        calendar.set(Calendar.MONTH, 0);
    //        calendar.set(Calendar.YEAR, year + 1);
    //        calendar.set(Calendar.HOUR_OF_DAY, 0);
    //        calendar.set(Calendar.MINUTE, 0);
    //        calendar.set(Calendar.MILLISECOND, 0);
    //        endDate = calendar.getTime();
    //
    //        rs = null;
    //        statement = null;
    //
    //        try {
    //            connection = getConnection();
    //
    //            result = new ArrayList();
    //
    //            query =
    //                new StringBuffer(
    //                    "SELECT"
    //                        + " ODOMETER_READING, CORRECTION_MILES"
    //                        + ", MOTORCYCLE_ID, DISTANCE_UNIT, RIDER_ID, FIRST_NAME, LAST_NAME"
    //                        + " FROM ZR_MILES, ZR_MOTORCYCLES, ZR_RIDERS"
    //                        + " WHERE ZR_MOTORCYCLES.ID = ZR_MILES.MOTORCYCLE_ID"
    //                        + " AND ZR_RIDERS.ID = ZR_MOTORCYCLES.RIDER_ID");
    //
    //            query.append(" AND MILES_DATE >= ?");
    //            query.append(" AND MILES_DATE < ?");
    //
    //            query.append(" ORDER BY RIDER_ID, MOTORCYCLE_ID, ODOMETER_READING");
    //
    //            //                log.info(query.toString());
    //
    //            statement = connection.prepareStatement(query.toString());
    //            statement.setDate(1, DateUtil.dateToSqlDate(startDate));
    //            statement.setDate(2, DateUtil.dateToSqlDate(endDate));
    //
    //            rs = statement.executeQuery();
    //
    //            lastRiderId = -1;
    //            lastMotocycleId = -1;
    //            lastDistanceUnit = -1;
    //            firstOdometer = -1;
    //            lastOdometer = -1;
    //            owner = null;
    //            totalMiles = 0;
    //            totalKilometers = 0;
    //            totalCorrections = 0;
    //            mileages = new ArrayList();
    //
    //            while (rs.next()) {
    //                odometerReading = rs.getInt("ODOMETER_READING");
    //                correction = rs.getInt("CORRECTION_MILES");
    //                motorcycleId = rs.getInt("MOTORCYCLE_ID");
    //                distanceUnit = rs.getInt("DISTANCE_UNIT");
    //                riderId = rs.getInt("RIDER_ID");
    //                firstName = rs.getString("FIRST_NAME");
    //                lastName = rs.getString("LAST_NAME");
    //
    //                //                log.info("Rider: " + lastName + ", odometer: " + odometerReading);
    //
    //                if (riderId != lastRiderId) {
    //                    // new rider (and bike) , calculate distances for last rider, store values for new rider
    //                    if (lastRiderId != -1) {
    //                        if (lastOdometer != -1) {
    //                            if (lastDistanceUnit
    //                                == Constants.BIKE_USES_MILES) {
    //                                totalMiles += (lastOdometer - firstOdometer);
    //                                mileages.add(
    //                                    new Integer(lastOdometer - firstOdometer + totalCorrections));
    //                            } else {
    //                                totalKilometers
    //                                    += (lastOdometer - firstOdometer);
    //                                mileages.add(
    //                                    new Integer(
    //                                        (int) ((lastOdometer - firstOdometer + totalCorrections)
    //                                            / Constants.MILE)));
    //                            }
    //                        }
    //                        result.add(
    //                            new NameMileageVO(
    //                                owner,
    //                                lastRiderId,
    //                                totalMiles
    //                                    + (int) (totalKilometers / Constants.MILE),
    //                                firstOdometer,
    //                                lastOdometer,
    //                                mileages));
    //                    }
    //                    owner = firstName + " " + lastName;
    //                    totalMiles = 0;
    //                    totalKilometers = 0;
    //                    totalCorrections = 0;
    //                    firstOdometer = odometerReading;
    //                    lastOdometer = -1;
    //                    mileages = new ArrayList();
    //                } else {
    //                    // riderId still the same, check for new bike
    //                    if (motorcycleId != lastMotocycleId) {
    //                        if (lastMotocycleId != -1) {
    //                            // new bike add totals
    //                            if (lastDistanceUnit
    //                                == Constants.BIKE_USES_MILES) {
    //                                totalMiles += (lastOdometer - firstOdometer);
    //                                mileages.add(
    //                                    new Integer(lastOdometer - firstOdometer + totalCorrections));
    //                            } else {
    //                                totalKilometers
    //                                    += (lastOdometer - firstOdometer);
    //                                mileages.add(
    //                                    new Integer(
    //                                        (int) ((lastOdometer - firstOdometer + totalCorrections)
    //                                            / Constants.MILE)));
    //                            }
    //                        }
    //
    //                        firstOdometer = odometerReading;
    //                        lastOdometer = -1;
    //                    } // if mot != lastmot
    //                }
    //
    //                if (distanceUnit == Constants.BIKE_USES_MILES) {
    //                    totalMiles += correction;
    //                } else {
    //                    totalKilometers += correction;
    //                }
    //                totalCorrections += correction;
    //
    //                lastRiderId = riderId;
    //                lastOdometer = odometerReading;
    //                lastMotocycleId = motorcycleId;
    //                lastDistanceUnit = distanceUnit;
    //
    //            } // while (rs.next())
    //
    //            // process last rider
    //            if (lastRiderId != -1) {
    //                if (lastOdometer == -1) {
    //                    totalMiles = 0;
    //                    totalKilometers = 0;
    //                } else {
    //                    if (lastDistanceUnit == Constants.BIKE_USES_MILES) {
    //                        totalMiles += (lastOdometer - firstOdometer);
    //                        mileages.add(new Integer(lastOdometer - firstOdometer + totalCorrections));
    //                    } else {
    //                        totalKilometers += (lastOdometer - firstOdometer);
    //                        mileages.add(
    //                            new Integer(
    //                                (int) ((lastOdometer - firstOdometer + totalCorrections)
    //                                    / Constants.MILE)));
    //                    }
    //                    lastOdometer = firstOdometer;
    //                }
    //                result.add(
    //                    new NameMileageVO(
    //                        owner,
    //                        lastRiderId,
    //                        totalMiles + (int) (totalKilometers / Constants.MILE),
    //                        firstOdometer,
    //                        lastOdometer,
    //                        mileages));
    //            }
    //
    //        } catch (ConnectionCreateException cce) {
    //            throw cce;
    //        } catch (SQLException sqle) {
    //            throw sqle;
    //        } finally {
    //            closeResultSet(rs);
    //            closeStatement(statement);
    //            closeConnection(connection);
    //            rs = null;
    //            statement = null;
    //            connection = null;
    //        }
    //
    //        return result;
    //
    //    } // findMilesForYear()

    /**
     * Makes a mileage ranking for a certain period of time.
     * 
     * @param startMonth (1-12)
     * @param startYear (yyyy)
     * @param endMonth (1-12)
     * @param endYear (yyyy)
     * @param testPreviousEntry flag to see if we need to select the last record before the
     * start month. This is necessary to get correct monthly data. For the year totals we
     * assume everybody enters the data for january 1 and december 31. 03-01-2004: not anymore
     * @return A <code>List</code> of <code>NameMileageVO</code> objects.
     * @throws SQLException
     * @throws ConnectionCreateException
     */
    private List findMilesForMonths(
        int startMonth,
        int startYear,
        int endMonth,
        int endYear,
        boolean testPreviousEntry)
        throws SQLException, ConnectionCreateException {
        StringBuffer allQuery;
        StringBuffer bikeQuery;
        ArrayList result;
        PreparedStatement allStatement;
        PreparedStatement bikeStatement;
        ResultSet allRs;
        ResultSet bikeRs;

        Date startDate;
        Date endDate;
        GregorianCalendar calendar;

        int riderId;
        int motorcycleId;
        int distanceUnit;
        int odometerReading;
        int correction;
        int lastRiderId;
        int firstOdometer;
        int lastOdometer;
        int totalMiles;
        int totalKilometers;
        int lastMotocycleId;
        int lastDistanceUnit;
        int totalCorrectionsKm;
        int totalCorrectionsM;
        int bikeCorrections;
        List mileages;

        String firstName;
        String lastName;
        String owner;

        calendar = new GregorianCalendar();
        //
        // Make first day of selection
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, startMonth - 1);
        calendar.set(Calendar.YEAR, startYear);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startDate = calendar.getTime();

        // Make last day plus one of selection
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        if (endMonth == 12) {
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.YEAR, endYear + 1);
        } else {
            calendar.set(Calendar.MONTH, endMonth);
            calendar.set(Calendar.YEAR, endYear);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        endDate = calendar.getTime();

        allRs = null;
        allStatement = null;
        bikeRs = null;
        bikeStatement = null;
        bikeQuery = null;

        try {
            connection = getConnection();

            result = new ArrayList();

            allQuery =
                new StringBuffer(
                    "SELECT"
                        + " ODOMETER_READING, CORRECTION_MILES"
                        + ", MOTORCYCLE_ID, DISTANCE_UNIT, RIDER_ID, FIRST_NAME, LAST_NAME"
                        + " FROM ZR_MILES, ZR_MOTORCYCLES, ZR_RIDERS"
                        + " WHERE ZR_MOTORCYCLES.ID = ZR_MILES.MOTORCYCLE_ID"
                        + " AND ZR_RIDERS.ID = ZR_MOTORCYCLES.RIDER_ID");

            allQuery.append(" AND MILES_DATE >= ?");
            allQuery.append(" AND MILES_DATE < ?");
            allQuery.append(
                " ORDER BY RIDER_ID, MOTORCYCLE_ID, ODOMETER_READING");

            if (testPreviousEntry) {
                bikeQuery =
                    new StringBuffer(
                        "SELECT" + " ODOMETER_READING" + " FROM ZR_MILES");

                bikeQuery.append(" WHERE ZR_MILES.MOTORCYCLE_ID = ?");
                bikeQuery.append(" AND MILES_DATE < ?");
                bikeQuery.append(" ORDER BY ODOMETER_READING DESC");
            }

            //            log.info(query.toString());

            allStatement = connection.prepareStatement(allQuery.toString());
            allStatement.setDate(1, DateUtil.dateToSqlDate(startDate));
            allStatement.setDate(2, DateUtil.dateToSqlDate(endDate));

            allRs = allStatement.executeQuery();

            lastRiderId = -1;
            lastMotocycleId = -1;
            lastDistanceUnit = -1;
            firstOdometer = -1;
            lastOdometer = -1;
            owner = null;
            totalMiles = 0;
            totalKilometers = 0;
            totalCorrectionsKm = 0;
            totalCorrectionsM = 0;
            bikeCorrections = 0;
            mileages = new ArrayList();

            while (allRs.next()) {
                odometerReading = allRs.getInt("ODOMETER_READING");
                correction = allRs.getInt("CORRECTION_MILES");
                motorcycleId = allRs.getInt("MOTORCYCLE_ID");
                distanceUnit = allRs.getInt("DISTANCE_UNIT");
                riderId = allRs.getInt("RIDER_ID");
                firstName = allRs.getString("FIRST_NAME");
                lastName = allRs.getString("LAST_NAME");

                //                log.info("Rider: " + riderId + ", bike: " + motorcycleId + ", distanceunit: " + distanceUnit + ", odometer: " + odometerReading + ", correction: " + correction);

                if (riderId != lastRiderId) {
                    // new rider (and bike) , calculate distances for last rider, store values for new rider
                    if (lastRiderId != -1) {
                        if (lastOdometer != -1) {
                            if (testPreviousEntry) {
                                if (bikeStatement == null) {
                                    bikeStatement =
                                        connection.prepareStatement(
                                            bikeQuery.toString());
                                    bikeStatement.setMaxRows(1);
                                }
                                bikeStatement.clearParameters();
                                bikeStatement.setInt(1, lastMotocycleId);
                                bikeStatement.setDate(
                                    2,
                                    DateUtil.dateToSqlDate(startDate));
                                bikeRs = bikeStatement.executeQuery();
                                if (bikeRs.next()) {
                                    firstOdometer = bikeRs.getInt(1);
                                    // log.info("new rider, wrap up bike: " + lastMotocycleId + ", fo: " + firstOdometer);
                                }
                                closeResultSet(bikeRs);
                            }
                            if (lastDistanceUnit
                                == Constants.BIKE_USES_MILES) {
                                totalMiles += (lastOdometer - firstOdometer);
                                mileages.add(
                                    new Integer(
                                        lastOdometer
                                            - firstOdometer
                                            + bikeCorrections));
                            } else {
                                totalKilometers
                                    += (lastOdometer - firstOdometer);
                                mileages.add(
                                    new Integer(
                                        (int) ((lastOdometer
                                            - firstOdometer
                                            + bikeCorrections)
                                            / Constants.MILE)));
                            }
                        }
                        result
                            .add(new NameMileageVO(
                                owner,
                                lastRiderId,
                                totalMiles
                                    + (int) (totalKilometers / Constants.MILE),
                        /*firstOdometer,
                        lastOdometer,*/
                        mileages));
                    }
                    owner = firstName + " " + lastName;
                    totalMiles = 0;
                    totalKilometers = 0;
                    totalCorrectionsKm = 0;
                    totalCorrectionsM = 0;
                    bikeCorrections = 0;
                    firstOdometer = odometerReading;
                    lastOdometer = -1;
                    mileages = new ArrayList();
                } else {
                    // riderId still the same, check for new bike
                    if (motorcycleId != lastMotocycleId) {
                        if (lastMotocycleId != -1) {
                            // new bike add totals
                            if (lastOdometer != -1) {
                                if (testPreviousEntry) {
                                    if (bikeStatement == null) {
                                        bikeStatement =
                                            connection.prepareStatement(
                                                bikeQuery.toString());
                                    }
                                    bikeStatement.clearParameters();
                                    bikeStatement.setInt(1, lastMotocycleId);
                                    bikeStatement.setDate(
                                        2,
                                        DateUtil.dateToSqlDate(startDate));
                                    bikeRs = bikeStatement.executeQuery();
                                    if (bikeRs.next()) {
                                        firstOdometer = bikeRs.getInt(1);
                                        // log.info("new bike, wrap up bike: " + lastMotocycleId + ", fo: " + firstOdometer);
                                    }
                                    closeResultSet(bikeRs);
                                }
                                if (lastDistanceUnit
                                    == Constants.BIKE_USES_MILES) {
                                    totalMiles
                                        += (lastOdometer - firstOdometer);
                                    mileages.add(
                                        new Integer(
                                            lastOdometer
                                                - firstOdometer
                                                + bikeCorrections));
                                } else {
                                    totalKilometers
                                        += (lastOdometer - firstOdometer);
                                    mileages.add(
                                        new Integer(
                                            (int) ((lastOdometer
                                                - firstOdometer
                                                + bikeCorrections)
                                                / Constants.MILE)));
                                }
                            }
                        }

                        bikeCorrections = 0;
                        firstOdometer = odometerReading;
                        lastOdometer = -1;
                    } // if mot != lastmot
                }

                bikeCorrections += correction;
                if (distanceUnit == Constants.BIKE_USES_MILES) {
                    totalMiles += correction;
                    totalCorrectionsM += correction;
                } else {
                    totalKilometers += correction;
                    totalCorrectionsKm += correction;
                }

                lastRiderId = riderId;
                lastOdometer = odometerReading;
                lastMotocycleId = motorcycleId;
                lastDistanceUnit = distanceUnit;

            } // while (rs.next())

            // process last rider
            if (lastRiderId != -1) {
                if (lastOdometer == -1) {
                    // log.info("lastOdometer == -1");
                    totalMiles = 0;
                    totalKilometers = 0;
                } else {
                    if (testPreviousEntry) {
                        if (bikeStatement == null) {
                            bikeStatement =
                                connection.prepareStatement(
                                    bikeQuery.toString());
                        }
                        bikeStatement.clearParameters();
                        bikeStatement.setInt(1, lastMotocycleId);
                        bikeStatement.setDate(
                            2,
                            DateUtil.dateToSqlDate(startDate));
                        bikeRs = bikeStatement.executeQuery();
                        if (bikeRs.next()) {
                            firstOdometer = bikeRs.getInt(1);
                            // log.info("last rider, bike: " + lastMotocycleId + ", fo: " + firstOdometer);
                        }
                        closeResultSet(bikeRs);
                    }
                    if (lastDistanceUnit == Constants.BIKE_USES_MILES) {
                        totalMiles += (lastOdometer - firstOdometer);
                        mileages.add(
                            new Integer(
                                lastOdometer
                                    - firstOdometer
                                    + bikeCorrections));
                    } else {
                        totalKilometers += (lastOdometer - firstOdometer);
                        mileages.add(
                            new Integer(
                                (int) ((lastOdometer
                                    - firstOdometer
                                    + bikeCorrections)
                                    / Constants.MILE)));
                    }
                    // lastOdometer = firstOdometer;
                }

                // log.info("Last add: rider: " + lastRiderId);
                result
                    .add(new NameMileageVO(
                        owner,
                        lastRiderId,
                        totalMiles + (int) (totalKilometers / Constants.MILE),
                /*firstOdometer,
                lastOdometer,*/
                mileages));
            }

        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeResultSet(allRs);
            closeResultSet(bikeRs);
            closeStatement(allStatement);
            closeStatement(bikeStatement);
            closeConnection(connection);
            connection = null;
        }

        return result;

    } // findMilesForMonths()

    /**
     * Finds all miles entries for a certain period of time for certain riders.
     * 
     * @param startMonth (1-12) 0 to indicate no restriction
     * @param startYear (yyyy) 0 to indicate no restriction
     * @param endMonth (1-12) 0 to indicate no restriction
     * @param endYear (yyyy) 0 to indicate no restriction
     * @param riders a list of rider id's to select the data for. Null to indicate all riders.
     * @return A <code>List</code> of <code>MilesRankingVO</code> objects.
     * @throws SQLException
     * @throws ConnectionCreateException
     */
    private List selectMilesForRanking(
        int startMonth,
        int startYear,
        int endMonth,
        int endYear,
        int[] riders)
        throws SQLException, ConnectionCreateException {

        ArrayList result;
        StringBuffer allQuery;
        PreparedStatement allStatement;
        ResultSet allRs;
        int lastRecordId;
        int rowsFetched;

        Date startDate;
        Date endDate;
        GregorianCalendar calendar;
        boolean doSelectDate;
        boolean doSelectRider;

        int id;
        int riderId;
        int motorcycleId;
        int distanceUnit;
        int odometerReading;
        int correction;
        Date date;
        List bikesInDb;
        Iterator bikesIterator;
        int i;

        startDate = null;
        endDate = null;
        calendar = null;

        if (startMonth == 0
            || startYear == 0
            || endMonth == 0
            || endYear == 0) {
            doSelectDate = false;
        } else {
            doSelectDate = true;
        }

        if (riders == null) {
            doSelectRider = false;
        } else {
            doSelectRider = true;
        }

        if (doSelectDate) {
            calendar = new GregorianCalendar();
            //
            // Make first day of selection
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, startMonth - 1);
            calendar.set(Calendar.YEAR, startYear);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            startDate = calendar.getTime();

            // Make last day plus one of selection
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            if (endMonth == 12) {
                calendar.set(Calendar.MONTH, 0);
                calendar.set(Calendar.YEAR, endYear + 1);
            } else {
                calendar.set(Calendar.MONTH, endMonth);
                calendar.set(Calendar.YEAR, endYear);
            }
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            endDate = calendar.getTime();
        }

        allRs = null;
        allStatement = null;

        try {
            connection = getConnection();

            result = new ArrayList();

            allQuery =
                new StringBuffer(
                    "SELECT"
                        + " ZR_MILES.ID, ODOMETER_READING, CORRECTION_MILES, MILES_DATE"
                        + ", MOTORCYCLE_ID, DISTANCE_UNIT, RIDER_ID"
                        + " FROM ZR_MILES, ZR_MOTORCYCLES"
                        + " WHERE ZR_MILES.ID > ?"
                        + " AND ZR_MOTORCYCLES.ID = ZR_MILES.MOTORCYCLE_ID");

            if (doSelectRider) {
                allQuery.append(" AND ZR_MOTORCYCLES.RIDER_ID IN (");
                for (i = 0; i < riders.length; i++) {
                    if (i > 0) {
                        allQuery.append(',');
                    }
                    allQuery.append(riders[i]);
                }
                allQuery.append(')');
            }

            if (doSelectDate) {
                allQuery.append(" AND MILES_DATE >= ?");
                allQuery.append(" AND MILES_DATE < ?");
            }

            // necessary to make MAX_RECORDS_PER_SELECT work
            allQuery.append(" ORDER BY ZR_MILES.ID");

            lastRecordId = -1;
            id = 0;
            result = new ArrayList();

            allStatement = connection.prepareStatement(allQuery.toString());
            allStatement.setMaxRows(ZrMilesDao.MAX_RECORDS_PER_SELECT);

            do {
                rowsFetched = 0;
                allStatement.clearParameters();
                allStatement.setInt(1, lastRecordId);
                if (doSelectDate) {
                    allStatement.setDate(2, DateUtil.dateToSqlDate(startDate));
                    allStatement.setDate(3, DateUtil.dateToSqlDate(endDate));
                }

                allRs = allStatement.executeQuery();

                while (allRs.next()) {
                    rowsFetched += 1;
                    id = allRs.getInt("ID");
                    odometerReading = allRs.getInt("ODOMETER_READING");
                    correction = allRs.getInt("CORRECTION_MILES");
                    date = DateUtil.sqlDateToDate(allRs.getDate("MILES_DATE"));
                    motorcycleId = allRs.getInt("MOTORCYCLE_ID");
                    distanceUnit = allRs.getInt("DISTANCE_UNIT");
                    riderId = allRs.getInt("RIDER_ID");
                    result.add(
                        new MilesRankingVO(
                            riderId,
                            motorcycleId,
                            date,
                            odometerReading,
                            correction,
                            Constants.BIKE_USES_KILOMETERS == distanceUnit));
                }
                lastRecordId = id;
                closeResultSet(allRs);
            }
            while (rowsFetched >= ZrMilesDao.MAX_RECORDS_PER_SELECT);

            // find all bikes so we can find the last entry before the first month
            // for all bikes.
            doSelectRider = true;
            closeStatement(allStatement);

            allQuery.delete(0, allQuery.length());
            allQuery.append(
                "SELECT"
                    + " ID"
                    + " FROM ZR_MOTORCYCLES"
                    + " WHERE ZR_MOTORCYCLES.ID > ?");

            if (doSelectRider) {
                allQuery.append(" AND ZR_MOTORCYCLES.RIDER_ID IN (");
                for (i = 0; i < riders.length; i++) {
                    if (i > 0) {
                        allQuery.append(',');
                    }
                    allQuery.append(riders[i]);
                }
                allQuery.append(')');
            }

            allQuery.append(" ORDER BY ID");

            lastRecordId = -1;

            allStatement = connection.prepareStatement(allQuery.toString());
            allStatement.setMaxRows(ZrMilesDao.MAX_RECORDS_PER_SELECT);

            bikesInDb = new ArrayList();
            
            do {
                rowsFetched = 0;
                allStatement.clearParameters();
                allStatement.setInt(1, lastRecordId);

                allRs = allStatement.executeQuery();

                while (allRs.next()) {
                    rowsFetched += 1;
                    id = allRs.getInt("ID");

                    bikesInDb.add(new Integer(id));

                }
                lastRecordId = id;
                closeResultSet(allRs);
            }
            while (rowsFetched >= ZrMilesDao.MAX_RECORDS_PER_SELECT);

            // select the last entry before the first month for all bikes.
/*
            select MOTORCYCLE_ID, max(MILES_DATE) from ZR_MILES where MILES_DATE < 2003-08-01 group by MOTORCYCLE_ID;
*/
            closeStatement(allStatement);

            allQuery.delete(0, allQuery.length());
            allQuery.append(
                "SELECT"
                    + " ZR_MILES.ID, ODOMETER_READING, CORRECTION_MILES, MILES_DATE"
                    + ", MOTORCYCLE_ID, DISTANCE_UNIT, RIDER_ID"
                    + " FROM ZR_MILES, ZR_MOTORCYCLES"
                    + " WHERE ZR_MOTORCYCLES.ID = ?"
                    + " AND ZR_MOTORCYCLES.ID = ZR_MILES.MOTORCYCLE_ID"
                    + " AND MILES_DATE < ?"
                    + " ORDER BY ZR_MILES.ODOMETER_READING DESC");

            allStatement = connection.prepareStatement(allQuery.toString());
            allStatement.setMaxRows(1);

            bikesIterator = bikesInDb.iterator();
            while (bikesIterator.hasNext()) {

                allStatement.clearParameters();
                allStatement.setInt(1, ((Integer) bikesIterator.next()).intValue());
                allStatement.setDate(2, DateUtil.dateToSqlDate(startDate));

                allRs = allStatement.executeQuery();

                while (allRs.next()) {
                    rowsFetched += 1;
                    id = allRs.getInt("ID");
                    odometerReading = allRs.getInt("ODOMETER_READING");
                    correction = allRs.getInt("CORRECTION_MILES");
                    date = DateUtil.sqlDateToDate(allRs.getDate("MILES_DATE"));
                    motorcycleId = allRs.getInt("MOTORCYCLE_ID");
                    distanceUnit = allRs.getInt("DISTANCE_UNIT");
                    riderId = allRs.getInt("RIDER_ID");
                    result.add(
                        new MilesRankingVO(
                            riderId,
                            motorcycleId,
                            date,
                            odometerReading,
                            correction,
                            Constants.BIKE_USES_KILOMETERS == distanceUnit));
                }
                closeResultSet(allRs);
            }
        } catch (ConnectionCreateException cce) {
            throw cce;
        } catch (SQLException sqle) {
            throw sqle;
        } finally {
            closeResultSet(allRs);
            closeStatement(allStatement);
            closeConnection(connection);
            allRs = null;
            allStatement = null;
            connection = null;
        }

        return result;

    } // selectMilesForRanking()

    private void deleteMiles(MilesEntry milesEntry)
        throws ConnectionCreateException, SQLException {

        String query;
        Statement statement;

        statement = null;

        try {
            connection = getConnection();

            query = "DELETE FROM ZR_MILES WHERE ID = " + milesEntry.getId();

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
    } // deleteMiles()

    private void updateMiles(MilesEntry milesEntry)
        throws ConnectionCreateException, SQLException {

        String query;
        PreparedStatement statement;

        statement = null;

        try {
            connection = getConnection();

            query =
                "UPDATE ZR_MILES"
                    + " SET MILES_DATE = ?, ODOMETER_READING = ?, CORRECTION_MILES = ?, USER_COMMENT=?"
                    + " WHERE ID = ?";

            statement = connection.prepareStatement(query);

            statement.setDate(1, DateUtil.dateToSqlDate(milesEntry.getDate()));
            statement.setInt(2, milesEntry.getOdometerReading());
            statement.setInt(3, milesEntry.getCorrection());
            if (milesEntry.getUserComment() == null) {
                statement.setNull(4, Types.VARCHAR);
            } else {
                statement.setString(4, milesEntry.getUserComment());
            }

            statement.setInt(5, milesEntry.getId().intValue());

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
    } // updateMiles()

}
