package com.github.megatronking.svg.applet.graphics;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Generate a Image based on the VectorDrawable's XML content.
 *
 * <p>This class also contains a main method, which can be used to preview a vector drawable file.
 */
public class VdPreview {

    private static final int MAX_PREVIEW_IMAGE_SIZE = 4096;
    private static final int MIN_PREVIEW_IMAGE_SIZE = 1;


    /**
     * This encapsulates the information used to determine the preview image size.
     * The reason we have different ways here is that both Studio UI and build process need
     * to use this common code path to generate images for vectordrawable.
     * When {@code mUseWidth} is true, use {@code mImageMaxDimension} as the maximum
     * dimension value while keeping the aspect ratio.
     * Otherwise, use {@code mImageScale} to scale the image based on the XML's size information.
     */
    public static class TargetSize {

        private int mImageMaxDimension;
        private float mImageScale;

        private TargetSize(int imageWidth, float imageScale) {
            mImageMaxDimension = imageWidth;
            mImageScale = imageScale;
        }

        public static TargetSize createSizeFromScale(float imageScale) {
            return new TargetSize( 0, imageScale);
        }
    }


    /**
     * This generates an image according to the VectorDrawable's content {@code xmlFileContent}.
     * At the same time, {@code vdErrorLog} captures all the errors found during parsing.
     * The size of image is determined by the {@code size}.
     *
     * @param targetSize the size of result image.
     * @param xmlFileContent  VectorDrawable's XML file's content.
     * @param vdErrorLog      log for the parsing errors and warnings.
     * @return an preview image according to the VectorDrawable's XML
     */
    public static BufferedImage getPreviewFromVectorXml(TargetSize targetSize, String xmlFileContent, StringBuilder vdErrorLog) {
        if (xmlFileContent == null || xmlFileContent.length() == 0) {
            return null;
        }
        VdParser p = new VdParser();
        VdTree vdTree;

        InputStream inputStream = new ByteArrayInputStream(xmlFileContent.getBytes());
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
                vdErrorLog.append("Invalid image size, can't fit in a square whose size is").append(forceImageSize);
            }
        } else {
            imageWidth = vdWidth * imageScale;
            imageHeight = vdHeight * imageScale;
        }

        // Create the image according to the vectorDrawable's aspect ratio.
        BufferedImage image = AssetUtil.newArgbBufferedImage((int)imageWidth, (int)imageHeight);
        vdTree.drawIntoImage(image);
        return image;
    }
}