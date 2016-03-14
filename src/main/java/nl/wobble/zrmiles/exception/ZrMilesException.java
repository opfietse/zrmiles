/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: ZrMilesException.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
 *
 * =============================================================================
 * Changelog:
 * -----------------------------------------------------------------------------
 * Date:
 * Change:
 * =============================================================================
 */
package nl.wobble.zrmiles.exception;

/**
 * @author rvk
 */
public class ZrMilesException extends Exception {
    private Exception rootCause;

    public ZrMilesException(String message) {
        super(message);
        rootCause = null;
    }

    public ZrMilesException(String message, Exception rootCause) {
        super(message);
        this.rootCause = rootCause;
    }

    public Exception getRootCause() {
        if (rootCause == null) {
            return this;
        } else {
            if (rootCause instanceof ZrMilesException) {
                return ((ZrMilesException) rootCause).getRootCause();
            } else {
              return rootCause;
            }
        }
    }
}
