/*
 * Copyright (C) 2008 The Android Open Source Project
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

package com.github.megatronking.svg.applet.graphics;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class GraphicsUtilities {

    public static BufferedImage loadCompatibleImage(URL resource) throws IOException {
        BufferedImage image = ImageIO.read(resource);
        if (image == null) {
            return null;
        }
        return toCompatibleImage(image);
    }

    private static BufferedImage toCompatibleImage(BufferedImage image) {
        if (isHeadless()) {
            return image;
        }

        ColorModel colorModel = image.getColorModel();
        if (colorModel != null && colorModel.equals(getGraphicsConfiguration().getColorModel())) {
            return image;
        }

        BufferedImage compatibleImage = getGraphicsConfiguration().createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());
        Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return compatibleImage;
    }

    private static GraphicsConfiguration getGraphicsConfiguration() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return environment.getDefaultScreenDevice().getDefaultConfiguration();
    }

    private static boolean isHeadless() {
        return GraphicsEnvironment.isHeadless();
    }
}
