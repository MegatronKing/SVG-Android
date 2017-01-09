package com.github.megatronking.svg.applet.ui;

import com.github.megatronking.svg.applet.graphics.GraphicsUtilities;
import com.github.megatronking.svg.applet.graphics.VdPreview;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;


class VectorImageViewer extends JComponent {

    private static final int PADDING = 10;

    private JVector mJVector;

    VectorImageViewer(String data) {

        setLayout(new GridLayout());
        setOpaque(true);
        setFocusable(true);

        mJVector = new JVector();
        add(mJVector);

        addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
                removeAncestorListener(this);
                refreshImage(data);
            }
        });
    }

    void refreshImage(String data) {
        BufferedImage image = VdPreview.getPreviewFromVectorXml(new VdPreview.TargetSize(getWidth() - 2 * PADDING, getHeight()- 2 * PADDING), data);
        mJVector.refreshImage(image);
    }

    boolean isImageValid() {
        return mJVector.isVisible();
    }

    private static class JVector extends JComponent {

        private static final String PANEL_BACKGROUND = "Panel.background";
        private static final Color BACK_COLOR = UIManager.getColor(PANEL_BACKGROUND).darker();

        private BufferedImage mImage;
        private TexturePaint mTexture;

        private JVector() {
            loadSupport();
        }

        private void refreshImage(BufferedImage image) {
            mImage = image;
            if (mImage == null) {
                setVisible(false);
            } else {
                setVisible(true);
                validate();
                repaint();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (mImage == null) {
                return;
            }
            int x = (getWidth() - mImage.getWidth()) / 2 - PADDING;
            int y = (getHeight() - mImage.getHeight()) / 2 - PADDING;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(BACK_COLOR);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.translate(x, y);
            g2.setPaint(mTexture);
            g2.fillRect(PADDING, PADDING, mImage.getWidth(), mImage.getHeight());

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

            g2.drawImage(mImage, PADDING, PADDING, null);
        }

        private void loadSupport() {
            try {
                URL resource = getClass().getResource("/images/checker.png");
                BufferedImage checker = GraphicsUtilities.loadCompatibleImage(resource);
                if (checker == null) {
                    return;
                }
                mTexture = new TexturePaint(checker, new Rectangle2D.Double(0, 0, checker.getWidth(), checker.getHeight()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
