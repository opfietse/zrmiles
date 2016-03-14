/*
 * Project: zrmiles
 *
 * Copyright (c) 2003 Mark Reuvekamp
 *
 * $Id: DaoFinderException.java,v 1.1 2007/06/01 15:24:04 rvk Exp $
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
public class DaoFinderException extends ZrMilesException {

    public DaoFinderException(String message) {
        super(message);
    }

    public DaoFinderException(String message, Exception rootCause) {
        super(message, rootCause);
    }
}
