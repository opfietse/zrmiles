/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: LastIntegerElementDescendingSorter.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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
import java.util.Iterator;
import java.util.List;

/**
 * Sorts a list of objects by the last element. It expects the last element to be
 * an Integer. Result is in descending order.
 * @author rvk
 */
public class LastIntegerElementDescendingSorter implements Comparator {

    /**
     * @return 0 if list(s) is/are empty or last object is not an <CODE>Integer</CODE>
     * @see java.util.Comparator#compare(Object, Object)
     */
    public int compare(Object x, Object y) {

        List xx;
        List yy;
        Object xxx;
        Object yyy;
        Iterator iterator;

        if (x.equals(y)) {
            return 0;
        }

        xx = (List) x;
        yy = (List) y;

        if (xx.size() == 0 || yy.size() == 0) {
            return 0;
        }
        
        xxx = null;
        yyy = null;

        iterator = xx.iterator();
        while (iterator.hasNext()) {
            xxx = iterator.next();
        }

        iterator = yy.iterator();
        while (iterator.hasNext()) {
            yyy = iterator.next();
        }

        // compare Integer component
        if (xxx instanceof Integer && yyy instanceof Integer) {
            return ((Integer) yyy).compareTo((Integer) xxx);
        } else {
            return 0;
        }

        /*
        if (xx.getRiderId()
            > yy.getRiderId()) {
            return 1;
        }
        
        if (xx.getRiderId()
            < yy.getRiderId()) {
            return -1;
        }
        */

    }
} // class MilesRankingAscendingSorter
