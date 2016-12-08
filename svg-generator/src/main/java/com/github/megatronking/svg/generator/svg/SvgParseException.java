/*
 * Copyright 2001-2005 (C) MetaStuff, Ltd. All Rights Reserved.
 *
 * This software is open source.
 * See the bottom of this file for the licence.
 */

package com.github.megatronking.svg.generator.svg;

import org.dom4j.DocumentException;

/**
 * Signals that an vector exception of some sort has occurred. This
 * class is the general class of exceptions produced by failed or
 * invalid operations.
 *
 * @author Megatron King
 * @since 2016/11/22 16:01
 */
public class SvgParseException extends DocumentException {

    /**
     * Constructs an {@code VectorException} with a default message
     * as its error detail message.
     */
    public SvgParseException() {
        super("Error occurred in read svg xml.");
    }

    /**
     * Constructs an {@code VectorException} with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public SvgParseException(String message) {
        super(message);
    }
}