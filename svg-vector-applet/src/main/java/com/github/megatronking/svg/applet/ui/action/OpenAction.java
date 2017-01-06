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

package com.github.megatronking.svg.applet.ui.action;

import com.github.megatronking.svg.applet.R;
import com.github.megatronking.svg.applet.ui.MainFrame;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;


public class OpenAction extends BackgroundAction {

    public static final String ACTION_NAME = R.string.menu_open;

    private MainFrame mFrame;

    public OpenAction(MainFrame frame) {
        this.mFrame = frame;
        putValue(NAME, R.string.open);
        putValue(SHORT_DESCRIPTION, R.string.open_short);
        putValue(LONG_DESCRIPTION, R.string.open_long);
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        executeBackgroundTask(mFrame.open(null));
    }
}
