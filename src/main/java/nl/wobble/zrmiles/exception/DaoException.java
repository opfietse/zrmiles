/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: DaoException.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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
public class DaoException extends ZrMilesException {

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
