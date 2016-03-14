/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: MilesRankingAscendingSorter.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.common;

import java.util.Comparator;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * Sorts a list of MilesRankingVO objects by rider id, bike id, mileage, date.
 * @author rvk
 */
public class MilesRankingAscendingSorter implements Comparator {

    /**
     * @see java.util.Comparator#compare(Object, Object)
     */
    public final int compare(final Object x, final Object y) {

        MilesRankingVO xx;
        MilesRankingVO yy;

        if (x.equals(y)) {
//            if (log.isInfoEnabled()) {
//                log.info("compare " + x + ", " + y + ": result = 0");
//            }
            return 0;
        }

        xx = (MilesRankingVO) x;
        yy = (MilesRankingVO) y;

        // compare rider id's
        if (xx.getRiderId()
            > yy.getRiderId()) {
            return 1;
        }

        if (xx.getRiderId()
            < yy.getRiderId()) {
            return -1;
        }

        // riders equal, compare bike id's
        if (xx.getMotorcycleId()
            > yy.getMotorcycleId()) {
            return 1;
        }

        if (xx.getMotorcycleId()
            < yy.getMotorcycleId()) {
            return -1;
        }

        // riders and bike equal, compare miles
        if (xx.getMileage()
            > yy.getMileage()) {
            return 1;
        }

        if (xx.getMileage()
            < yy.getMileage()) {
            return -1;
        } else {
            // miles equal, compare dates
            return xx.getDate().compareTo(yy.getDate());
        }
    }
} // class MilesRankingAscendingSorter
