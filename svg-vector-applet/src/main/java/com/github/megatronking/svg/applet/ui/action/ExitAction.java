package com.github.megatronking.svg.applet.ui.action;

import com.github.megatronking.svg.applet.R;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class ExitAction extends AbstractAction {

    public static final String ACTION_NAME = R.string.menu_exit;

    private JFrame mFrame;

    public ExitAction(JFrame frame) {
        this.mFrame = frame;
        putValue(NAME, R.string.exit);
        putValue(SHORT_DESCRIPTION, R.string.exit_short);
        putValue(LONG_DESCRIPTION, R.string.exit_long);
        putValue(MNEMONIC_KEY, KeyEvent.VK_Q);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mFrame.dispose();
        System.exit(0);
    }

}
