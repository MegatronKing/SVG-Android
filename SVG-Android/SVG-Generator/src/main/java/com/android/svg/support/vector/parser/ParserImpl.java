package com.android.svg.support.vector.parser;

/**
 * The impl parsers.
 *
 * @author Megatron King
 * @since 2016/9/1 14:07
 */

public interface ParserImpl {

    // attributes
    VectorAttributeParser VECTOR_ATTRIBUTE_PARSER = new VectorAttributeParser();
    GroupAttributeParser GROUP_ATTRIBUTE_PARSER = new GroupAttributeParser();
    PathAttributeParser PATH_ATTRIBUTE_PARSER = new PathAttributeParser();
    ClipPathAttributeParser CLIP_PATH_ATTRIBUTE_PARSER = new ClipPathAttributeParser();

    // elements
    VectorElementParser VECTOR_ELEMENT_PARSER = new VectorElementParser();
    GroupElementParser GROUP_ELEMENT_PARSER = new GroupElementParser();

}
