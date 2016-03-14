/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MyCgiServerDbInfo.java,v 1.1 2007/06/01 15:24:07 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao;

/**
 * @author rvk
 */
public class MyCgiServerDbInfo extends DbInfo {

    public MyCgiServerDbInfo() {
        driver = "org.hsqldb.jdbcDriver";
        url =
            "jdbc:hsqldb:/members/baJFKdqq7jttHf4p8mn6hPMCC8p19XQ8/db/zrmiles";
        user = "sa";
        password = null;
    }

}
