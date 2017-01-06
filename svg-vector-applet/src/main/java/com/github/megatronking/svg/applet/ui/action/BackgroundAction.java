package com.github.megatronking.svg.applet.ui.action;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

abstract class BackgroundAction extends AbstractAction {

    void executeBackgroundTask(SwingWorker<?, ?> worker) {
        if (worker != null) {
            worker.execute();
        }
    }

}