package com.github.megatronking.svg.applet.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.List;

/**
 * A set of utility classes for manipulating {@link BufferedImage} objects and drawing them to
 * {@link Graphics2D} canvases.
 */
public class AssetUtil {

    /**
     * Scales the given rectangle by the given scale factor.
     *
     * @param rect        The rectangle to scale.
     * @param scaleFactor The factor to scale by.
     * @return The scaled rectangle.
     */
    public static Rectangle scaleRectangle(Rectangle rect, float scaleFactor) {
        return new Rectangle(
                Math.round(rect.x * scaleFactor),
                Math.round(rect.y * scaleFactor),
                Math.round(rect.width * scaleFactor),
                Math.round(rect.height * scaleFactor));
    }

    /**
     * Creates a new ARGB {@link BufferedImage} of the given width and height.
     *
     * @param width  The width of the new image.
     * @param height The height of the new image.
     * @return The newly created image.
     */
    public static BufferedImage newArgbBufferedImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Smoothly scales the given {@link BufferedImage} to the given width and height using the
     * {@link Image#SCALE_SMOOTH} algorithm (generally bicubic resampling or bilinear filtering).
     *
     * @param source The source image.
     * @param width  The destination width to scale to.
     * @param height The destination height to scale to.
     * @return A new, scaled image.
     */
    public static BufferedImage scaledImage(BufferedImage source, int width, int height) {
        Image scaledImage = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaledBufImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = scaledBufImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return scaledBufImage;
    }

    /**
     * Applies a gaussian blur of the given radius to the given {@link BufferedImage} using a kernel
     * convolution.
     *
     * @param source The source image.
     * @param radius The blur radius, in pixels.
     * @return A new, blurred image, or the source image if no blur is performed.
     */
    public static BufferedImage blurredImage(BufferedImage source, double radius) {
        if (radius == 0) {
            return source;
        }

        final int r = (int) Math.ceil(radius);
        final int rows = r * 2 + 1;
        final float[] kernelData = new float[rows * rows];

        final double sigma = radius / 3;
        final double sigma22 = 2 * sigma * sigma;
        final double sqrtPiSigma22 = Math.sqrt(Math.PI * sigma22);
        final double radius2 = radius * radius;

        double total = 0;
        int index = 0;
        double distance2;

        int x, y;
        for (y = -r; y <= r; y++) {
            for (x = -r; x <= r; x++) {
                distance2 = 1.0 * x * x + 1.0 * y * y;
                if (distance2 > radius2) {
                    kernelData[index] = 0;
                } else {
                    kernelData[index] = (float) (Math.exp(-distance2 / sigma22) / sqrtPiSigma22);
                }
                total += kernelData[index];
                ++index;
            }
        }

        for (index = 0; index < kernelData.length; index++) {
            kernelData[index] /= total;
        }

        // We first pad the image so the kernel can operate at the edges.
        BufferedImage paddedSource = paddedImage(source, r);
        BufferedImage blurredPaddedImage = operatedImage(paddedSource, new ConvolveOp(
                new Kernel(rows, rows, kernelData), ConvolveOp.EDGE_ZERO_FILL, null));
        return blurredPaddedImage.getSubimage(r, r, source.getWidth(), source.getHeight());
    }

    /**
     * Inverts the alpha channel of the given {@link BufferedImage}. RGB data for the inverted area
     * are undefined, so it's generally best to fill the resulting image with a color.
     *
     * @param source The source image.
     * @return A new image with an alpha channel inverted from the original.
     */
    public static BufferedImage invertedAlphaImage(BufferedImage source) {
        final float[] scaleFactors = new float[]{1, 1, 1, -1};
        final float[] offsets = new float[]{0, 0, 0, 255};

        return operatedImage(source, new RescaleOp(scaleFactors, offsets, null));
    }

    /**
     * Applies a {@link BufferedImageOp} on the given {@link BufferedImage}.
     *
     * @param source The source image.
     * @param op     The operation to perform.
     * @return A new image with the operation performed.
     */
    public static BufferedImage operatedImage(BufferedImage source, BufferedImageOp op) {
        BufferedImage newImage = newArgbBufferedImage(source.getWidth(), source.getHeight());
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(source, op, 0, 0);
        return newImage;
    }

    /**
     * Fills the given {@link BufferedImage} with a {@link Paint}, preserving its alpha channel.
     *
     * @param source The source image.
     * @param paint  The paint to fill with.
     * @return A new, painted/filled image.
     */
    public static BufferedImage filledImage(BufferedImage source, Paint paint) {
        BufferedImage newImage = newArgbBufferedImage(source.getWidth(), source.getHeight());
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setPaint(paint);
        g.fillRect(0, 0, source.getWidth(), source.getHeight());
        return newImage;
    }

    /**
     * Pads the given {@link BufferedImage} on all sides by the given padding amount.
     *
     * @param source  The source image.
     * @param padding The amount to pad on all sides, in pixels.
     * @return A new, padded image, or the source image if no padding is performed.
     */
    public static BufferedImage paddedImage(BufferedImage source, int padding) {
        if (padding == 0) {
            return source;
        }

        BufferedImage newImage = newArgbBufferedImage(
                source.getWidth() + padding * 2, source.getHeight() + padding * 2);
        Graphics2D g = (Graphics2D) newImage.getGraphics();
        g.drawImage(source, padding, padding, null);
        return newImage;
    }

    /**
     * Trims the transparent pixels from the given {@link BufferedImage} (returns a sub-image).
     *
     * @param source The source image.
     * @return A new, trimmed image, or the source image if no trim is performed.
     */
    public static BufferedImage trimmedImage(BufferedImage source) {
        final int minAlpha = 1;
        final int srcWidth = source.getWidth();
        final int srcHeight = source.getHeight();
        Raster raster = source.getRaster();
        int l = srcWidth, t = srcHeight, r = 0, b = 0;

        int alpha, x, y;
        int[] pixel = new int[4];
        for (y = 0; y < srcHeight; y++) {
            for (x = 0; x < srcWidth; x++) {
                raster.getPixel(x, y, pixel);
                alpha = pixel[3];
                if (alpha >= minAlpha) {
                    l = Math.min(x, l);
                    t = Math.min(y, t);
                    r = Math.max(x, r);
                    b = Math.max(y, b);
                }
            }
        }

        if (l > r || t > b) {
            // No pixels, couldn't trim
            return source;
        }

        return source.getSubimage(l, t, r - l + 1, b - t + 1);
    }

    /**
     * Draws the given {@link BufferedImage} to the canvas, at the given coordinates, with the given
     * {@link Effect}s applied. Note that drawn effects may be outside the bounds of the source
     * image.
     *
     * @param g       The destination canvas.
     * @param source  The source image.
     * @param x       The x offset at which to draw the image.
     * @param y       The y offset at which to draw the image.
     * @param effects The list of effects to apply.
     */
    public static void drawEffects(Graphics2D g, BufferedImage source, int x, int y,
                                   Effect[] effects) {
        List<ShadowEffect> shadowEffects = new ArrayList<ShadowEffect>();
        List<FillEffect> fillEffects = new ArrayList<FillEffect>();

        for (Effect effect : effects) {
            if (effect instanceof ShadowEffect) {
                shadowEffects.add((ShadowEffect) effect);
            } else if (effect instanceof FillEffect) {
                fillEffects.add((FillEffect) effect);
            }
        }

        Composite oldComposite = g.getComposite();
        for (ShadowEffect effect : shadowEffects) {
            if (effect.inner) {
                continue;
            }

            // Outer shadow
            g.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, (float) effect.opacity));
            g.drawImage(
                    filledImage(
                            blurredImage(source, effect.radius),
                            effect.color),
                    (int) effect.xOffset, (int) effect.yOffset, null);
        }
        g.setComposite(oldComposite);

        // Inner shadow & fill effects.
        final Rectangle imageRect = new Rectangle(0, 0, source.getWidth(), source.getHeight());
        BufferedImage out = newArgbBufferedImage(imageRect.width, imageRect.height);
        Graphics2D g2 = (Graphics2D) out.getGraphics();
        double fillOpacity = 1.0;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.drawImage(source, 0, 0, null);
        g2.setComposite(AlphaComposite.SrcAtop);

        // Gradient fill
        for (FillEffect effect : fillEffects) {
            g2.setPaint(effect.paint);
            g2.fillRect(0, 0, imageRect.width, imageRect.height);
            fillOpacity = Math.max(0, Math.min(1, effect.opacity));
        }

        // Inner shadows
        for (ShadowEffect effect : shadowEffects) {
            if (!effect.inner) {
                continue;
            }

            BufferedImage innerShadowImage = newArgbBufferedImage(
                    imageRect.width, imageRect.height);
            Graphics2D g3 = (Graphics2D) innerShadowImage.getGraphics();
            g3.drawImage(source, (int) effect.xOffset, (int) effect.yOffset, null);
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_ATOP, (float) effect.opacity));
            g2.drawImage(
                    filledImage(
                            blurredImage(invertedAlphaImage(innerShadowImage), effect.radius),
                            effect.color),
                    0, 0, null);
        }

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) fillOpacity));
        g.drawImage(out, x, y, null);
        g.setComposite(oldComposite);
    }

    /**
     * Draws the given {@link BufferedImage} to the canvas, centered, wholly contained within the
     * bounds defined by the destination rectangle, and with preserved aspect ratio.
     *
     * @param g       The destination canvas.
     * @param source  The source image.
     * @param dstRect The destination rectangle in the destination canvas into which to draw the
     *                image.
     */
    public static void drawCenterInside(Graphics2D g, BufferedImage source, Rectangle dstRect) {
        final int srcWidth = source.getWidth();
        final int srcHeight = source.getHeight();
        if (srcWidth * 1.0 / srcHeight > dstRect.width * 1.0 / dstRect.height) {
            final int scaledWidth = Math.max(1, dstRect.width);
            final int scaledHeight = Math.max(1, dstRect.width * srcHeight / srcWidth);
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x,
                    dstRect.y + (dstRect.height - scaledHeight) / 2,
                    dstRect.x + dstRect.width,
                    dstRect.y + (dstRect.height - scaledHeight) / 2 + scaledHeight,
                    0,
                    0,
                    0 + scaledWidth,
                    0 + scaledHeight,
                    null);
        } else {
            final int scaledWidth = Math.max(1, dstRect.height * srcWidth / srcHeight);
            final int scaledHeight = Math.max(1, dstRect.height);
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x + (dstRect.width - scaledWidth) / 2,
                    dstRect.y,
                    dstRect.x + (dstRect.width - scaledWidth) / 2 + scaledWidth,
                    dstRect.y + dstRect.height,
                    0,
                    0,
                    0 + scaledWidth,
                    0 + scaledHeight,
                    null);
        }
    }

    /**
     * Draws the given {@link BufferedImage} to the canvas, centered and cropped to fill the
     * bounds defined by the destination rectangle, and with preserved aspect ratio.
     *
     * @param g       The destination canvas.
     * @param source  The source image.
     * @param dstRect The destination rectangle in the destination canvas into which to draw the
     *                image.
     */
    public static void drawCenterCrop(Graphics2D g, BufferedImage source, Rectangle dstRect) {
        final int srcWidth = source.getWidth();
        final int srcHeight = source.getHeight();
        if (srcWidth * 1.0 / srcHeight > dstRect.width * 1.0 / dstRect.height) {
            final int scaledWidth = dstRect.height * srcWidth / srcHeight;
            final int scaledHeight = dstRect.height;
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x,
                    dstRect.y,
                    dstRect.x + dstRect.width,
                    dstRect.y + dstRect.height,
                    0 + (scaledWidth - dstRect.width) / 2,
                    0,
                    0 + (scaledWidth - dstRect.width) / 2 + dstRect.width,
                    0 + dstRect.height,
                    null);
        } else {
            final int scaledWidth = dstRect.width;
            final int scaledHeight = dstRect.width * srcHeight / srcWidth;
            Image scaledImage = scaledImage(source, scaledWidth, scaledHeight);
            g.drawImage(scaledImage,
                    dstRect.x,
                    dstRect.y,
                    dstRect.x + dstRect.width,
                    dstRect.y + dstRect.height,
                    0,
                    0 + (scaledHeight - dstRect.height) / 2,
                    0 + dstRect.width,
                    0 + (scaledHeight - dstRect.height) / 2 + dstRect.height,
                    null);
        }
    }

    /**
     * An effect to apply in
     * {@link AssetUtil#drawEffects(java.awt.Graphics2D, java.awt.image.BufferedImage, int, int, AssetUtil.Effect[])}
     */
    public abstract static class Effect {
    }

    /**
     * An inner or outer shadow.
     */
    public static class ShadowEffect extends Effect {
        public double xOffset;
        public double yOffset;
        public double radius;
        public Color color;
        public double opacity;
        public boolean inner;

        public ShadowEffect(double xOffset, double yOffset, double radius, Color color,
                            double opacity, boolean inner) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.radius = radius;
            this.color = color;
            this.opacity = opacity;
            this.inner = inner;
        }
    }

    /**
     * A fill, defined by a paint.
     */
    public static class FillEffect extends Effect {

        public Paint paint;
        public double opacity;

        public FillEffect(Paint paint, double opacity) {
            this.paint = paint;
            this.opacity = opacity;
        }

        public FillEffect(Paint paint) {
            this.paint = paint;
            this.opacity = 1.0;
        }
    }
}