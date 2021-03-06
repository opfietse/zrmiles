/*
 * Project: motor
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: DescendingNameMileageSorter.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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
 * @author rvk
 */
public class DescendingNameMileageSorter implements Comparator {

    /**
     * @see java.util.Comparator#compare(Object, Object)
     */
    public int compare(Object x, Object y) {

//        Log log = LogFactory.getLog(DescendingNameMileageSorter.class);

//        if (log.isInfoEnabled()) {
//            log.info(x);
//            log.info(y);
//        }

        if (x.equals(y)) {
//            if (log.isInfoEnabled()) {
//                log.info("compare " + x + ", " + y + ": result = 0");
//            }
            return 0;
        }

        if (((NameMileageVO) x).getMileage()
            > ((NameMileageVO) y).getMileage()) {
//            if (log.isInfoEnabled()) {
//                log.info("compare " + x + ", " + y + ": result = -1");
//            }
            return -1;
        }

        if (((NameMileageVO) x).getMileage()
            < ((NameMileageVO) y).getMileage()) {
//            if (log.isInfoEnabled()) {
//                log.info("compare " + x + ", " + y + ": result = 1");
//            }
            return 1;
        } else {
//            if (log.isInfoEnabled()) {
//                log.info("compare " + x + ", " + y + ": result = 0");
//            }
            return 0;
        }
    }
} // class DescendingNameMileageSorter
