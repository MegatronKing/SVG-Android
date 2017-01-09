package com.github.megatronking.svg.applet.ui;

import com.github.megatronking.svg.applet.graphics.GraphicsUtilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JComponent;

class OpenFilePanel extends JComponent {

    private static final String DROP_TEXT = "Drop Here";

    private BufferedImage dropHere;

    OpenFilePanel(MainFrame mainFrame) {
        setOpaque(false);
        loadSupportImage();
        setTransferHandler(new ImageTransferHandler(mainFrame));
    }

    private void loadSupportImage() {
        try {
            URL resource = getClass().getResource("/images/drop.png");
            dropHere = GraphicsUtilities.loadCompatibleImage(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int x = (getWidth() - dropHere.getWidth()) / 2;
        int y = (getHeight() - dropHere.getHeight()) / 2;
        g.setFont(new Font("宋体", Font.BOLD, 32));
        g.setColor(Color.WHITE);
        int textWidth = g.getFontMetrics().stringWidth(DROP_TEXT);
        g.drawString(DROP_TEXT, (getWidth() - textWidth) / 2, y - 30);
        g.drawImage(dropHere, x, y, null);
    }

}
