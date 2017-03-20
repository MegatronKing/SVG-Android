/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.github.megatronking.svg.generator.svg.css;

public interface CSSParserCallback {

    void handleImport(String importString);

    void handleSelector(String selector);

    void startRule();

    void handleProperty(String property);

    void handleValue(String value);

    void endRule();

}
