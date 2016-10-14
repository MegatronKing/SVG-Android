/*
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 */

package com.android.svg.support.vector;

/**
 * Signals that an vector exception of some sort has occurred. This
 * class is the general class of exceptions produced by failed or
 * invalid operations.
 *
 * @author Megatron King
 * @since 2016/9/5 13:55
 */
public class VectorException extends Exception {

    /**
     * Constructs an {@code VectorException} with a default message
     * as its error detail message.
     */
    public VectorException() {
        super("Error occurred in read vector xml.");
    }

    /**
     * Constructs an {@code VectorException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public VectorException(String message) {
        super(message);
    }
}