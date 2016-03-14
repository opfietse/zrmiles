/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MySqlMotorcyclesDao.java,v 1.1 2007/06/01 15:24:06 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao.mysql;

import nl.wobble.zrmiles.dao.MotorcyclesDao;

//import org.apache.commons.logging.LogFactory;

/**
 * @author rvk
 */
public class MySqlMotorcyclesDao extends MotorcyclesDao {

    public MySqlMotorcyclesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation) {
        super(dbclass, dburl, dbuser, dbpassword, implementation);

        //        log = LogFactory.getLog(MySqlMotorcyclesDao.class);

    }

    /**
     * @see kawazr7.nl.wobble.zrmiles.dao.MotorcyclesDao#getCreateTableQuery()
     */
    public String getCreateTableQuery() {
        return "CREATE TABLE"
            + " ZR_MOTORCYCLES ("
            + "ID INTEGER NOT NULL PRIMARY KEY,"
            + "RIDER_ID INTEGER NOT NULL,"
            + "MAKE CHAR(40) NOT NULL,"
            + "MODEL CHAR(50) NOT NULL,"
            + "YEAR INTEGER,"
            + "DISTANCE_UNIT SMALLINT NOT NULL"
            + ")";
    }

    /**
     * @see kawazr7.nl.wobble.zrmiles.dao.MotorcyclesDao#getCreateIndexQueries()
     */
    public String[] getCreateIndexQueries() {
        // no indexes
        return null;
    }

}
