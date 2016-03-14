/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: DummyRidersDao.java,v 1.1 2007/06/01 15:24:07 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.dao.dummy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.wobble.zrmiles.common.Rider;
import nl.wobble.zrmiles.dao.RidersDao;
import nl.wobble.zrmiles.exception.DaoException;

/**
 * @author rvk
 */
public class DummyRidersDao extends RidersDao {

    private static final ArrayList riders = makeRiders();

    public DummyRidersDao(
        String dbclass,
        String dburl,
        String dbuser,
        String dbpassword,
        int implementation) {
        super(dbclass, dbuser, dburl, dbpassword, implementation);
    }

    /**
     * @see nl.wobble.zrmiles.dao.RidersDao#findAll(String)
     */
    public List findAll(String orderBy) {
        Rider rider;
        ArrayList riders;

        riders = new ArrayList();
        rider =
            new Rider(
                new Integer(1),
                "Mark",
                "Reuvekamp",
                "",
                "Raalte, NL",
                "kawazr7",
                "password",
                7);
        riders.add(rider);
        rider =
            new Rider(
                new Integer(2),
                "Zero",
                "Axe",
                "",
                "London, GB",
                "zeroaxe",
                "jam",
                1);
        riders.add(rider);
        rider =
            new Rider(
                new Integer(3),
                "Manuel",
                "X",
                "",
                "Berlin, D",
                "moba",
                "berlin",
                1);
        riders.add(rider);

        return riders;
    }

    /**
     * @see nl.wobble.zrmiles.dao.RidersDao#findById(int)
     */
    public Rider findById(int id) {
        Rider rider;

        switch (id) {
            case 1 :
                rider = (Rider) riders.get(0);
                break;
            case 2 :
                rider = (Rider) riders.get(1);
                break;
            case 3 :
                rider = (Rider) riders.get(2);
                break;
            default :
                rider = null;
                break;
        }

        return rider;
    }
    /**
     * @see nl.wobble.zrmiles.dao.RidersDao#add(nl.wobble.zrmiles.common.Rider)
     */
    public int add(Rider rider) throws DaoException {
        int highestId;
        int newId;
        Rider oldRider;
        Iterator iterator;

        highestId = 0;
        iterator = riders.iterator();
        while (iterator.hasNext()) {
            oldRider = (Rider) iterator.next();
            if (oldRider.getId().intValue() > highestId) {
                highestId = oldRider.getId().intValue();
            }
        }

        newId = highestId + 1;
        rider.setId(new Integer(newId));

        riders.add(rider);

        return newId;
    }

    /**
     * @see nl.wobble.zrmiles.dao.RidersDao#delete(nl.wobble.zrmiles.common.Rider)
     */
    public void delete(Rider rider) throws DaoException {
        // TODO Auto-generated method stub

    }

    /**
     * @see nl.wobble.zrmiles.dao.RidersDao#update(nl.wobble.zrmiles.common.Rider)
     */
    public void update(Rider rider) throws DaoException {
        // TODO Auto-generated method stub

    }

    private static ArrayList makeRiders() {

        ArrayList list = new ArrayList(3);
        list.add(
            new Rider(
                new Integer(1),
                "Mark",
                "Reuvekamp",
                "",
                "Raalte, NL",
                "kawazr7",
                "password",
                7));
        list.add(
            new Rider(
                new Integer(2),
                "Zero",
                "Axe",
                "",
                "London, GB",
                "zeroaxe",
                "jam",
                1));
        list.add(
            new Rider(
                new Integer(3),
                "Manuel",
                "X",
                "",
                "Berlin, D",
                "moba",
                "berlin",
                1));

        return list;
    }

    public String getCreateTableQuery() {
        return null;
    }
    /**
     * @see kawazr7.nl.wobble.zrmiles.dao.RidersDao#getCreateIndexQueries()
     */
    public String[] getCreateIndexQueries() {
        // TODO Auto-generated method stub
        return null;
    }

}
