/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MySqlRidersDao.java,v 1.1 2007/06/01 15:24:06 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao.mysql;

import nl.wobble.zrmiles.dao.RidersDao;

//import org.apache.commons.logging.LogFactory;

/**
 * @author rvk
 */
public class MySqlRidersDao extends RidersDao {

    public MySqlRidersDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation) {
        super(dbclass, dburl, dbuser, dbpassword, implementation);

        //        log = LogFactory.getLog(MySqlRidersDao.class);

    }

    public String getCreateTableQuery() {
        return "CREATE TABLE"
            + " ZR_RIDERS ("
            + "ID INTEGER NOT NULL PRIMARY KEY,"
            + "FIRST_NAME CHAR(20) NOT NULL,"
            + "LAST_NAME CHAR(40) NOT NULL,"
            + "EMAIL_ADDRESS CHAR(50),"
            + "STREET_ADDRESS CHAR(50)"
            + ",USER_NAME CHAR(10) NOT NULL,"
            + "USER_PASSWORD CHAR(30),"
            + "USER_ROLE SMALLINT NOT NULL"
            + ")";

    }
    /**
     * @see kawazr7.nl.wobble.zrmiles.dao.RidersDao#getCreateIndexQueries()
     */
    public String[] getCreateIndexQueries() {
        String[] result = new String[1];

        result[0] =
            "CREATE UNIQUE INDEX ZR_NAME ON ZR_RIDERS ("
                + "FIRST_NAME,"
                + "LAST_NAME"
                + ")";

        return result;
    }

}
