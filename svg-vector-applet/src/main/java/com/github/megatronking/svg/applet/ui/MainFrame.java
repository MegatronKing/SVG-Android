package com.github.megatronking.svg.applet.ui;

import com.github.megatronking.svg.applet.Application;
import com.github.megatronking.svg.applet.R;
import com.github.megatronking.svg.applet.io.StringOutputStream;
import com.github.megatronking.svg.applet.support.SupportFileFilter;
import com.github.megatronking.svg.applet.ui.action.ExitAction;
import com.github.megatronking.svg.applet.ui.action.OpenAction;
import com.github.megatronking.svg.applet.ui.action.SaveAction;
import com.github.megatronking.svg.generator.svg.Svg2Vector;
import com.github.megatronking.svg.generator.utils.FileUtils;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ActionMap;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * This is the frame.
 *
 * @author Megatron King
 * @since 2017/1/5 17:50
 */

public class MainFrame extends JFrame {

    private static final String TITLE_FORMAT = Application.NAME + ": %s";

    private ActionMap mActionsMap;

    private VectorEditorPanel mEditorPanel;

    private JMenuItem mSaveMenuItem;

    private File mCurrentFile;

    public MainFrame() throws HeadlessException {
        super(Application.NAME);
        buildActions();
        buildMenuBar();
        buildContent();
        showOpenFilePanel();
        setSize(1024, 600);
    }

    private void buildActions() {
        this.mActionsMap = new ActionMap();
        this.mActionsMap.put(OpenAction.ACTION_NAME, new OpenAction(this));
        this.mActionsMap.put(SaveAction.ACTION_NAME, new SaveAction(this));
        this.mActionsMap.put(ExitAction.ACTION_NAME, new ExitAction(this));
    }

    private void buildMenuBar() {
        JMenu fileMenu = new JMenu(R.string.file);
        fileMenu.setMnemonic('F');
        JMenuItem openMenuItem = new JMenuItem();
        mSaveMenuItem = new JMenuItem();
        JMenuItem exitMenuItem = new JMenuItem();

        openMenuItem.setAction(mActionsMap.get(OpenAction.ACTION_NAME));
        fileMenu.add(openMenuItem);

        mSaveMenuItem.setAction(mActionsMap.get(SaveAction.ACTION_NAME));
        mSaveMenuItem.setEnabled(false);
        fileMenu.add(mSaveMenuItem);

        exitMenuItem.setAction(mActionsMap.get(ExitAction.ACTION_NAME));
        fileMenu.add(exitMenuItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu(R.string.help);
        JMenuItem aboutItem = new JMenuItem(R.string.about);
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getContentPane(), R.string.about_text);
            }
        });
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }


    private void buildContent() {
        setContentPane(new GradientPanel());
    }

    private void showOpenFilePanel() {
        add(new OpenFilePanel(this));
    }

    public SwingWorker<?, ?> open(File file) {
        if (file == null) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new SupportFileFilter());
            if (mCurrentFile != null && mCurrentFile.getParentFile().exists()) {
                chooser.setCurrentDirectory(mCurrentFile.getParentFile());
            }
            int choice = chooser.showOpenDialog(this);
            if (choice == JFileChooser.APPROVE_OPTION) {
                return new OpenTask(chooser.getSelectedFile());
            } else {
                return null;
            }
        } else {
            return new OpenTask(file);
        }
    }

    private void showSvg2VectorEditor(String inputData, String name) {
        if (mEditorPanel != null) {
            mEditorPanel.dispose();
        }
        getContentPane().removeAll();
        mEditorPanel = new VectorEditorPanel(this, inputData, name);
        add(mEditorPanel);
        mSaveMenuItem.setEnabled(true);
        validate();
        repaint();
    }

    public void updateSaveMenu(boolean enable) {
        mSaveMenuItem.setEnabled(enable);
    }

    public SwingWorker<?, ?> save() {
        if (mEditorPanel == null) {
            return null;
        }
        String noExtensionName = FileUtils.noExtensionFullName(mCurrentFile);
        File file = mEditorPanel.chooseSaveFile(true, new File(noExtensionName + ".xml"));
        return file != null ? new SaveTask(file) : null;
    }

    private class SaveTask extends SwingWorker<Boolean, Void> {

        private File file;

        SaveTask(File file) {
            this.file = file;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            try {
                String vectorContent = mEditorPanel.getVector();
                if(!file.getName().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }
                FileUtils.saveFile(file.getParent(), file.getName(), vectorContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private class OpenTask extends SwingWorker<String, Void> {

        private final File file;
        private String errorMsg;

        private OpenTask(File file) {
            this.file = file;
            mCurrentFile = file;
        }

        @Override
        protected String doInBackground() throws Exception {
            StringOutputStream sos = new StringOutputStream();
            errorMsg = Svg2Vector.parseSvgToXml(file, sos, 0, 0);
            return sos.getString();
        }

        @Override
        protected void done() {
            if (errorMsg != null) {
                JOptionPane.showMessageDialog(getContentPane(), errorMsg);
            } else {
                try {
                    showSvg2VectorEditor(get(), file.getAbsolutePath());
                    setTitle(String.format(TITLE_FORMAT, file.getAbsolutePath()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
