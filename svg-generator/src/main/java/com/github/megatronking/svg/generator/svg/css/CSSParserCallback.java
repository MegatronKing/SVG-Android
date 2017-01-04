package com.github.megatronking.svg.generator.svg.css;

public interface CSSParserCallback {

    void handleImport(String importString);

    void handleSelector(String selector);

    void startRule();

    void handleProperty(String property);

    void handleValue(String value);

    void endRule();

}
