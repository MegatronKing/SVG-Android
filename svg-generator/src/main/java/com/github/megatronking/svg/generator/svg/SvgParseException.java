/*
 * Copyright (C) 2017, Megatron King
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
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