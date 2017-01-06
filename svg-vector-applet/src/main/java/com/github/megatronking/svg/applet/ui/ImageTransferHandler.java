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

package com.github.megatronking.svg.applet.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

class ImageTransferHandler extends TransferHandler {
    private final MainFrame mainFrame;

    ImageTransferHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public boolean importData(JComponent component, Transferable transferable) {
        try {
            for (DataFlavor flavor : transferable.getTransferDataFlavors()) {
                if (flavor.isFlavorJavaFileListType()) {
                    Object data = transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    //noinspection unchecked
                    final File file = ((List<File>) data).get(0);
                    mainFrame.open(file).execute();
                    return true;
                } else if (flavor.isFlavorTextType()) {
                    if (flavor.getRepresentationClass() == String.class) {
                        String mime = flavor.getMimeType();
                        DataFlavor flave = new DataFlavor(mime);
                        Object data = transferable.getTransferData(flave);
                        final String path = convertPath(data.toString());
                        mainFrame.open(new File(path)).execute();
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // Ignore
        }

        return false;
    }

    private static String convertPath(String path) {
        if (path.startsWith("file://")) path = path.substring("file://".length());
        if (path.indexOf('\n') != -1) path = path.substring(0, path.indexOf('\n'));
        if (path.indexOf('\r') != -1) path = path.substring(0, path.indexOf('\r'));
        return path;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        boolean isCopySupported = (COPY & support.getSourceDropActions()) == COPY;
        if (!isCopySupported) {
            return false;
        }
        for (DataFlavor flavor : support.getDataFlavors()) {
            if (flavor.isFlavorJavaFileListType() || flavor.isFlavorTextType()) {
                support.setDropAction(COPY);
                return true;
            }
        }
        return false;
    }
}
