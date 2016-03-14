/*
 * Project: motoradmin
 *
 * Copyright (c) 2004 Mark Reuvekamp
 *
 * $Id: SortUtil.java,v 1.1 2007/06/01 15:24:05 rvk Exp $
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
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 * @author rvk
 */
public class SortUtil {

//    private Log log = LogFactory.getLog(SortUtil.class);

    public SortedSet sortMilesTotals(List miles, boolean ascending) {
        TreeSet result;
//        boolean setChanged;

//        if (log.isInfoEnabled()) {
//            log.info("start sortMilesTotals(" + miles + ", " + ascending + ")");
//        }

        if (ascending) {
            result = new TreeSet(new AscendingMileageNamesSorter());
        } else {
            result = new TreeSet(new DescendingMileageNamesSorter());
        }

        /* setChanged = */

//        if (log.isInfoEnabled()) {
//            log.info("end sortMilesTotals(), setChanged: " + setChanged);
//        }

        return result;
    }

    public class AscendingMileageNamesSorter implements Comparator {

       public int compare(Object x, Object y) {

//           Log log = LogFactory.getLog(AscendingMileageNamesSorter.class);

//           if (log.isInfoEnabled()) {
//               log.info(x);
//               log.info(y);
//           }

            if (x.equals(y)) {
//                if (log.isInfoEnabled()) {
//                    log.info("compare " + x + ", " + y + ": result = 0");
//                }
                return 0;
            }

            if (((NameMileageVO) x).getMileage() > ((NameMileageVO) y).getMileage()) {
//                if (log.isInfoEnabled()) {
//                    log.info("compare " + x + ", " + y + ": result = 1");
//                }
                return 1;
            }

           if (((NameMileageVO) x).getMileage() < ((NameMileageVO) y).getMileage()) {
//               if (log.isInfoEnabled()) {
//                   log.info("compare " + x + ", " + y + ": result = -1");
//               }
               return -1;
           } else {
//               if (log.isInfoEnabled()) {
//                   log.info("compare " + x + ", " + y + ": result = 0");
//               }
               return 0;
           }
        }
    } // class AscendingMileageNamesSorter

    public class DescendingMileageNamesSorter implements Comparator {
       public int compare(Object x, Object y) {

//            Log log = LogFactory.getLog(DescendingMileageNamesSorter.class);

//            if (log.isInfoEnabled()) {
//                log.info(x);
//                log.info(y);
//            }

            if (x.equals(y)) {
//                if (log.isInfoEnabled()) {
//                    log.info("compare " + x + ", " + y + ": result = 0");
//                }
                return 0;
            }

            if (((NameMileageVO) x).getMileage() > ((NameMileageVO) y).getMileage()) {
//                if (log.isInfoEnabled()) {
//                    log.info("compare " + x + ", " + y + ": result = -1");
//                }
                return -1;
            }

           if (((NameMileageVO) x).getMileage() < ((NameMileageVO) y).getMileage()) {
//               if (log.isInfoEnabled()) {
//                   log.info("compare " + x + ", " + y + ": result = 1");
//               }
               return 1;
           } else {
//               if (log.isInfoEnabled()) {
//                   log.info("compare " + x + ", " + y + ": result = 0");
//               }
               return 0;
           }
        }
    } // class DescendingMileageNamesSorter
}
