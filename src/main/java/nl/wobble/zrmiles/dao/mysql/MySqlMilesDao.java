/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MySqlMilesDao.java,v 1.1 2007/06/01 15:24:06 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao.mysql;

import nl.wobble.zrmiles.dao.MilesDao;

//import org.apache.commons.logging.LogFactory;

/**
 * @author rvk
 */
public class MySqlMilesDao extends MilesDao {

    public MySqlMilesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation) {
        super(dbclass, dburl, dbuser, dbpassword, implementation);

        //        log = LogFactory.getLog(MySqlMilesDao.class);

    }

    /**
     * @see kawazr7.nl.wobble.zrmiles.dao.MilesDao#getCreateTableQuery()
     */
    public String getCreateTableQuery() {
        return "CREATE TABLE"
            + " ZR_MILES ("
            + "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "MOTORCYCLE_ID INTEGER NOT NULL,"
            + "MILES_DATE DATE NOT NULL,"
            + "ODOMETER_READING INTEGER NOT NULL,"
            + "CORRECTION_MILES INTEGER,"
            + "USER_COMMENT VARCHAR(50)"
            + ")";
    }

    /**
     * @see kawazr7.nl.wobble.zrmiles.dao.MilesDao#getCreateIndexQueries()
     */
    public String[] getCreateIndexQueries() {
        String[] result = new String[1];

        result[0] =
            "CREATE UNIQUE INDEX ZR_MILEAGE ON ZR_MILES ("
                + "MOTORCYCLE_ID,"
                + "MILES_DATE,"
                + "ODOMETER_READING,"
                + "CORRECTION_MILES"
                + ")";

        return result;
    }

}
