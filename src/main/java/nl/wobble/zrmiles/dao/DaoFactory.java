/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: DaoFactory.java,v 1.1 2007/06/01 15:24:07 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao;

import nl.wobble.zrmiles.dao.dummy.DummyRidersDao;
import nl.wobble.zrmiles.dao.hsqldb.HSqlDbMilesDao;
import nl.wobble.zrmiles.dao.hsqldb.HSqlDbMotorcyclesDao;
import nl.wobble.zrmiles.dao.hsqldb.HSqlDbRidersDao;
import nl.wobble.zrmiles.dao.mysql.MySqlMilesDao;
import nl.wobble.zrmiles.dao.mysql.MySqlMotorcyclesDao;
import nl.wobble.zrmiles.dao.mysql.MySqlRidersDao;
import nl.wobble.zrmiles.exception.DaoCreateException;

/**
 * Creates Data Access Objects for the zrmiles application.
 * Alter the implementation to use by editing and recompile.
 * @author rvk
 */
public class DaoFactory {

    private static DaoFactory instance = new DaoFactory();
    public static final int USE_DUMMY = 0;
    public static final int USE_MYSQL = 1;
    public static final int USE_HSQLDB = 2;

    //    private static final int IMPLEMENTATION_TO_USE = USE_DUMMY;
    private static final int IMPLEMENTATION_TO_USE = USE_MYSQL;

    private DaoFactory() {
    }

    /**
     * Returns an instance of the factory.
     * @return A factory
     */
    public static DaoFactory getInstance() {
        return instance;
    }

    /**
     * Returns a RidersDao.
     * @return A DAO for the riders data.
     * @throws DaoCreateException
     */
    public RidersDao getRidersDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword)
        throws DaoCreateException {

        return getRidersDao(dbclass, dburl, dbuser, dbpassword, IMPLEMENTATION_TO_USE);
    }

    /**
     * Returns a RidersDao.
     * @return A DAO for the riders data.
     * @throws DaoCreateException
     */
    public RidersDao getRidersDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation)
        throws DaoCreateException {

        RidersDao dao;

        dao = null;
        switch (implementation) {
            case USE_DUMMY :
                dao = new DummyRidersDao(null, null, null, null, implementation);
                break;
            case USE_MYSQL :
                dao = new MySqlRidersDao(dbclass, dburl, dbuser, dbpassword, implementation);
                break;
            case USE_HSQLDB :
                dao = new HSqlDbRidersDao(dbclass, dburl, dbuser, dbpassword, implementation);
                break;
            default :
                dao = null;
                break;
        }

        if (dao == null) {
            throw new DaoCreateException("Don't know what implementation to use");
        }

        return dao;
    }

    public RidersDao getRidersDao(DbInfo dbInfo) throws DaoCreateException {
        return getRidersDao(
            dbInfo.driver,
            dbInfo.url,
            dbInfo.user,
            dbInfo.password, IMPLEMENTATION_TO_USE);
    }

    public RidersDao getRidersDao(DbInfo dbInfo, int implementation) throws DaoCreateException {
        return getRidersDao(
            dbInfo.driver,
            dbInfo.url,
            dbInfo.user,
            dbInfo.password, implementation);
    }

    /**
     * Returns a MotorcycleDao.
     * @return A DAO for the motorcycles data.
     * @throws DaoCreateException
     */
    public MotorcyclesDao getMotorcyclesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword)
        throws DaoCreateException {

        return getMotorcyclesDao(dbclass, dburl, dbuser, dbpassword, IMPLEMENTATION_TO_USE);
    }

    /**
     * Returns a MotorcycleDao.
     * @return A DAO for the motorcycles data.
     * @throws DaoCreateException
     */
    public MotorcyclesDao getMotorcyclesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation)
        throws DaoCreateException {

        MotorcyclesDao dao;

        dao = null;
        switch (implementation) {
            //            case USE_DUMMY: dao = new DummyMotorcyclesDao(null, null, null, null);
            //                            break;
            case USE_MYSQL :
                dao =
                    new MySqlMotorcyclesDao(dbclass, dburl, dbuser, dbpassword, implementation);
                break;
            case USE_HSQLDB :
                dao =
                    new HSqlDbMotorcyclesDao(dbclass, dburl, dbuser, dbpassword, implementation);
                break;
            default :
                dao = null;
                break;
        }

        if (dao == null) {
            throw new DaoCreateException("Don't know what implementation to use");
        }

        return dao;
    }

    public MotorcyclesDao getMotorcyclesDao(DbInfo dbInfo) throws DaoCreateException {
        return getMotorcyclesDao(
            dbInfo.driver,
            dbInfo.url,
            dbInfo.user,
            dbInfo.password);
    }

    public MotorcyclesDao getMotorcyclesDao(DbInfo dbInfo, int implementation) throws DaoCreateException {
        return getMotorcyclesDao(
            dbInfo.driver,
            dbInfo.url,
            dbInfo.user,
            dbInfo.password, implementation);
    }

    /**
     * Returns a MilesDao.
     * @return A DAO for the motorcycles data.
     * @throws DaoCreateException
     */
    public MilesDao getMilesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword)
        throws DaoCreateException {

        return getMilesDao(dbclass, dburl, dbuser, dbpassword, IMPLEMENTATION_TO_USE);
    }

    /**
     * Returns a MilesDao.
     * @return A DAO for the motorcycles data.
     * @throws DaoCreateException
     */
    public MilesDao getMilesDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation)
        throws DaoCreateException {

        MilesDao dao;

        dao = null;
        switch (implementation) {
            //            case USE_DUMMY: dao = new DummyMilesDao(null, null, null, null);
            //                            break;
            case USE_MYSQL :
                dao = new MySqlMilesDao(dbclass, dburl, dbuser, dbpassword, implementation);
                break;
            case USE_HSQLDB :
                dao = new HSqlDbMilesDao(dbclass, dburl, dbuser, dbpassword, implementation);
                break;
            default :
                dao = null;
                break;
        }

        if (dao == null) {
            throw new DaoCreateException("Don't know what implementation to use");
        }

        return dao;
    }

    public MilesDao getMilesDao(DbInfo dbInfo) throws DaoCreateException {
        return getMilesDao(
            dbInfo.driver,
            dbInfo.url,
            dbInfo.user,
            dbInfo.password, IMPLEMENTATION_TO_USE);
    }

    public MilesDao getMilesDao(DbInfo dbInfo, int implementation) throws DaoCreateException {
        return getMilesDao(
            dbInfo.driver,
            dbInfo.url,
            dbInfo.user,
            dbInfo.password, implementation);
    }
}
