package com.github.megatronking.svg.applet.ui;

import com.github.megatronking.svg.applet.graphics.VdPreview;
import com.github.megatronking.svg.generator.utils.TextUtils;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;


class VectorImageViewer extends JComponent {

    private static final String PANEL_BACKGROUND = "Panel.background";
    private static final Color BACK_COLOR = UIManager.getColor(PANEL_BACKGROUND).darker();
    private static final int PADDING = 10;

    /** Default zoom level for the vector image. */
    private static final float DEFAULT_ZOOM = 1.0f;

    private float mZoom = DEFAULT_ZOOM;

    private BufferedImage mImage;
    private StringBuilder mErrorMsg;

    private final TexturePaint mTexture;
    private final Container mContainer;

    private final Dimension mSize;

    VectorImageViewer(Container container, String data, TexturePaint texture) {
        this.mTexture = texture;
        this.mContainer = container;

        buildImage(data);

        setLayout(new GridBagLayout());
        setOpaque(true);
        setFocusable(true);

        // Exact size will be set by setZoom() in AncestorListener#ancestorMoved.
        mSize = new Dimension(0, 0);

        addAncestorListener(new AncestorListener() {

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                removeAncestorListener(this);
                setDefaultZoom();
            }

            @Override
            public void ancestorAdded(AncestorEvent event) {
            }

        });
    }

    void refreshImage(String data) {
        buildImage(data);
        setDefaultZoom();
        validate();
        repaint();
    }

    private void buildImage(String data) {
        mErrorMsg = new StringBuilder();
        mImage = VdPreview.getPreviewFromVectorXml(VdPreview.TargetSize.createSizeFromScale(1), data, mErrorMsg);
    }

    private void setDefaultZoom() {
        if (mImage == null) {
            setZoom(DEFAULT_ZOOM);
            return;
        }
        int frameWidth = getWidth() - 2 * PADDING, frameHeight = getHeight() - 2 * PADDING;
        float z = DEFAULT_ZOOM;
        if (frameWidth > 0 && frameHeight > 0) {
            float w = (float) frameWidth / mImage.getWidth();
            float h = (float) frameHeight / mImage.getHeight();
            z = Math.min(w, h);
        }
        setZoom(z);
    }

    private void setZoom(float value) {
        mZoom = value;
        updateSize();
        if (!mSize.equals(getSize())) {
            setSize(mSize);
            mContainer.validate();
            repaint();
        }
    }

    private void updateSize() {
        int width = mImage == null ? 0 : mImage.getWidth();
        int height = mImage == null ? 0 : mImage.getHeight();
        if (mSize.height == 0 || (getHeight() - mSize.height) == 0) {
            mSize.setSize(width * mZoom + PADDING * 2, height * mZoom + PADDING * 2);
        } else {
            mSize.setSize(width * mZoom + PADDING * 2, height * mZoom + PADDING * 2);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        int x = (getWidth() - mSize.width) / 2;
        int y = (getHeight() - mSize.height) / 2;

        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg.toString())) {
            return;
        }

        int zpadding = Math.round(PADDING / mZoom);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(BACK_COLOR);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.translate(x, y);
        g2.setPaint(mTexture);
        g2.fillRect(PADDING, PADDING, mSize.width - 2 * PADDING, mSize.height - 2 * PADDING);

        g2.scale(mZoom, mZoom);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        g2.drawImage(mImage, zpadding, zpadding, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return mSize;
    }

    Image getImage() {
        return mImage;
    }
}
