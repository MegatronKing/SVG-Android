
/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.svg.support.svg;

import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.google.common.base.Charsets;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Generate a Image based on the VectorDrawable's XML content.
 * This class also contains a main method, which can be used to preview a vector drawable file.
 */
public class VdPreview {
    private static final String ANDROID_ALPHA = "android:alpha";
    private static final String ANDROID_AUTO_MIRRORED = "android:autoMirrored";
    private static final String ANDROID_HEIGHT = "android:height";
    private static final String ANDROID_WIDTH = "android:width";
    public static final int MAX_PREVIEW_IMAGE_SIZE = 4096;
    public static final int MIN_PREVIEW_IMAGE_SIZE = 1;

    /**
     * This encapsulates the information used to determine the preview image size.
     * The reason we have different ways here is that both Studio UI and build process need
     * to use this common code path to generate images for vectordrawable.
     * When mUseWidth is true, use {@code mImageMaxDimension} as the maximum
     * dimension value while keeping the aspect ratio.
     * Otherwise, use {@code mImageScale} to scale the image based on the XML's size information.
     */
    public static class TargetSize {
        private boolean mUseWidth;
        private int mImageMaxDimension;
        private float mImageScale;

        private TargetSize(boolean useWidth, int imageWidth, float imageScale) {
            mUseWidth = useWidth;
            mImageMaxDimension = imageWidth;
            mImageScale = imageScale;
        }

        public static TargetSize createSizeFromWidth(int imageWidth) {
            return new TargetSize(true, imageWidth, 0.0f);
        }

        public static TargetSize createSizeFromScale(float imageScale) {
            return new TargetSize(false, 0, imageScale);
        }
    }

    /**
     * Since we allow overriding the vector drawable's size, we also need to keep
     * the original size and aspect ratio.
     */
    public static class SourceSize {
        public int getHeight() {
            return mSourceHeight;
        }

        public int getWidth() {
            return mSourceWidth;
        }

        private int mSourceWidth;
        private int mSourceHeight;
    }

    /**
     * @return a format object for XML formatting.
     */
    @NonNull
    private static OutputFormat getPrettyPrintFormat() {
        OutputFormat format = new OutputFormat();
        format.setLineWidth(120);
        format.setIndenting(true);
        format.setIndent(4);
        format.setEncoding("UTF-8");
        format.setOmitComments(true);
        return format;
    }

    /**
     * Get the vector drawable's original size.
     */
    public static SourceSize getVdOriginalSize(@NonNull Document document) {
        Element root = document.getDocumentElement();
        SourceSize srcSize = new SourceSize();
        // Update attributes, note that attributes as width and height are required,
        // while others are optional.
        NamedNodeMap attr = root.getAttributes();
        Node nodeAttr = attr.getNamedItem(ANDROID_WIDTH);
        assert nodeAttr != null;
        srcSize.mSourceWidth = parseDimension(0, nodeAttr, false);
        nodeAttr = attr.getNamedItem(ANDROID_HEIGHT);
        assert nodeAttr != null;
        srcSize.mSourceHeight = parseDimension(0, nodeAttr, false);
        return srcSize;
    }

    /**
     * The UI can override some properties of the Vector drawable.
     * In order to override in an uniform way, we re-parse the XML file
     * and pick the appropriate attributes to override.
     *
     * @param document the parsed document of original VectorDrawable's XML file.
     * @param info     incoming override information for VectorDrawable.
     * @param errorLog log for the parsing errors and warnings.
     * @return the overridden XML file in one string. If exception happens
     * or no attributes needs to be overriden, return null.
     */
    @Nullable
    public static String overrideXmlContent(@NonNull Document document,
                                            @NonNull VdOverrideInfo info,
                                            @Nullable StringBuilder errorLog) {
        boolean isXmlFileContentChanged = false;
        Element root = document.getDocumentElement();
        // Update attributes, note that attributes as width and height are required,
        // while others are optional.
        NamedNodeMap attr = root.getAttributes();
        if (info.needsOverrideWidth()) {
            Node nodeAttr = attr.getNamedItem(ANDROID_WIDTH);
            int overrideValue = info.getWidth();
            int originalValue = parseDimension(overrideValue, nodeAttr, true);
            if (originalValue != overrideValue) {
                isXmlFileContentChanged = true;
            }
        }
        if (info.needsOverrideHeight()) {
            Node nodeAttr = attr.getNamedItem(ANDROID_HEIGHT);
            int overrideValue = info.getHeight();
            int originalValue = parseDimension(overrideValue, nodeAttr, true);
            if (originalValue != overrideValue) {
                isXmlFileContentChanged = true;
            }
        }
        if (info.needsOverrideOpacity()) {
            Node nodeAttr = attr.getNamedItem(ANDROID_ALPHA);
            String opacityValue = String.format("%.2f", info.getOpacity() / 100.0f);
            if (nodeAttr != null) {
                nodeAttr.setTextContent(opacityValue);
            } else {
                root.setAttribute(ANDROID_ALPHA, opacityValue);
            }
            isXmlFileContentChanged = true;
        }
        // When auto mirror is set to true, then we always need to set it.
        // Because SVG has no such attribute at all.
        if (info.needsOverrideAutoMirrored()) {
            Node nodeAttr = attr.getNamedItem(ANDROID_AUTO_MIRRORED);
            if (nodeAttr != null) {
                nodeAttr.setTextContent("true");
            } else {
                root.setAttribute(ANDROID_AUTO_MIRRORED, "true");
            }
            isXmlFileContentChanged = true;
        }
        if (isXmlFileContentChanged) {
            // Prettify the XML string from the document.
            StringWriter stringOut = new StringWriter();
            XMLSerializer serial = new XMLSerializer(stringOut, getPrettyPrintFormat());
            try {
                serial.serialize(document);
            } catch (IOException e) {
                if (errorLog != null) {
                    errorLog.append("Exception while parsing XML file:\n").append(e.getMessage());
                }
            }
            return stringOut.toString();
        } else {
            return null;
        }
    }

    /**
     * Query the dimension info and override it if needed.
     *
     * @param overrideValue the dimension value to override with.
     * @param nodeAttr      the node who contains dimension info.
     * @param override      if true then override the dimension.
     * @return the original dimension value.
     */
    private static int parseDimension(int overrideValue, Node nodeAttr, boolean override) {
        assert nodeAttr != null;
        String content = nodeAttr.getTextContent();
        assert content.endsWith("dp");
        int originalValue = Integer.parseInt(content.substring(0, content.length() - 2));
        if (override) {
            nodeAttr.setTextContent(overrideValue + "dp");
        }
        return originalValue;
    }

    /**
     * This generates an image according to the VectorDrawable's content {@code xmlFileContent}.
     * At the same time, vdErrorLog captures all the errors found during parsing.
     * The size of image is determined by the {@code size}.
     *
     * @param targetSize     the size of result image.
     * @param xmlFileContent VectorDrawable's XML file's content.
     * @param vdErrorLog     log for the parsing errors and warnings.
     * @return an preview image according to the VectorDrawable's XML
     */
    @Nullable
    public static BufferedImage getPreviewFromVectorXml(@NonNull TargetSize targetSize,
                                                        @Nullable String xmlFileContent,
                                                        @Nullable StringBuilder vdErrorLog) {
        if (xmlFileContent == null || xmlFileContent.isEmpty()) {
            return null;
        }
        VdParser p = new VdParser();
        VdTree vdTree;
        InputStream inputStream = new ByteArrayInputStream(
                xmlFileContent.getBytes(Charsets.UTF_8));
        vdTree = p.parse(inputStream, vdErrorLog);
        if (vdTree == null) {
            return null;
        }
        // If the forceImageSize is set (>0), then we honor that.
        // Otherwise, we will ask the vectorDrawable for the prefer size, then apply the imageScale.
        float vdWidth = vdTree.getBaseWidth();
        float vdHeight = vdTree.getBaseHeight();
        float imageWidth;
        float imageHeight;
        int forceImageSize = targetSize.mImageMaxDimension;
        float imageScale = targetSize.mImageScale;
        if (forceImageSize > 0) {
            // The goal here is to generate an image within certain size, while keeping the
            // aspect ration as much as we can.
            // If it is scaling too much to fit in, we log an error.
            float maxVdSize = Math.max(vdWidth, vdHeight);
            float ratioToForceImageSize = forceImageSize / maxVdSize;
            float scaledWidth = ratioToForceImageSize * vdWidth;
            float scaledHeight = ratioToForceImageSize * vdHeight;
            imageWidth = Math.max(MIN_PREVIEW_IMAGE_SIZE, Math.min(MAX_PREVIEW_IMAGE_SIZE, scaledWidth));
            imageHeight = Math.max(MIN_PREVIEW_IMAGE_SIZE, Math.min(MAX_PREVIEW_IMAGE_SIZE, scaledHeight));
            if (scaledWidth != imageWidth || scaledHeight != imageHeight) {
                vdErrorLog.append("Invalid image size, can't fit in a square whose size is" + forceImageSize);
            }
        } else {
            imageWidth = vdWidth * imageScale;
            imageHeight = vdHeight * imageScale;
        }
        // Create the image according to the vectorDrawable's aspect ratio.
        BufferedImage image = AssetUtil.newArgbBufferedImage((int) imageWidth, (int) imageHeight);
        vdTree.drawIntoImage(image);
        return image;
    }

    public static void main(String[] args) {
        System.out.println("Hello from sdk-common-lib.");
    }
}