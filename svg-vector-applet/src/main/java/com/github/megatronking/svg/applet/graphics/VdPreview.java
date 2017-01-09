package com.github.megatronking.svg.applet.graphics;

import com.github.megatronking.svg.generator.utils.TextUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Generate a Image based on the VectorDrawable's XML content.
 *
 * <p>This class also contains a main method, which can be used to preview a vector drawable file.
 */
public class VdPreview {

    public static class TargetSize {

        private int mImageWidth;
        private int mImageHeight;

        public TargetSize(int imageWidth, int imageHeight) {
            mImageWidth = imageWidth;
            mImageHeight = imageHeight;
        }
    }

    /**
     * This generates an image according to the VectorDrawable's content {@code xmlFileContent}.
     * At the same time, {@code vdErrorLog} captures all the errors found during parsing.
     * The size of image is determined by the {@code size}.
     *
     * @param targetSize the size of result image.
     * @param xmlFileContent  VectorDrawable's XML file's content.
     * @return an preview image according to the VectorDrawable's XML
     */
    public static BufferedImage getPreviewFromVectorXml(TargetSize targetSize, String xmlFileContent) {
        if (xmlFileContent == null || xmlFileContent.length() == 0) {
            return null;
        }
        VdParser p = new VdParser();
        VdTree vdTree;

        StringBuilder vdErrorLog = new StringBuilder();
        InputStream inputStream = new ByteArrayInputStream(xmlFileContent.getBytes());
        vdTree = p.parse(inputStream, vdErrorLog);
        if (vdTree == null || !TextUtils.isEmpty(vdErrorLog)) {
            return null;
        }

        // If the forceImageSize is set (>0), then we honor that.
        // Otherwise, we will ask the vectorDrawable for the prefer size, then apply the imageScale.
        float vdWidth = vdTree.getBaseWidth();
        float vdHeight = vdTree.getBaseHeight();

        if (vdWidth <= 0 || vdHeight <= 0) {
            return null;
        }

        float imageWidth;
        float imageHeight;

        float ratioWidth = targetSize.mImageWidth / vdWidth;
        float ratioHeight = targetSize.mImageHeight / vdHeight;

        float scale = Math.min(ratioWidth, ratioHeight);

        imageWidth = vdWidth * scale;
        imageHeight = vdHeight * scale;

        if ((int)imageWidth <= 0 || (int)imageHeight <= 0) {
            return null;
        }

        // Create the image according to the vectorDrawable's aspect ratio.
        BufferedImage image = AssetUtil.newArgbBufferedImage((int)imageWidth, (int)imageHeight);
        vdTree.drawIntoImage(image);
        return image;
    }
}