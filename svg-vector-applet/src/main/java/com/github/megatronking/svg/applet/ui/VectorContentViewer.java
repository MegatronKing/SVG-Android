package com.github.megatronking.svg.applet.ui;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class VectorContentViewer extends JComponent implements DocumentListener {

    private OnTextWatcher mTextWatcher;

    private JTextArea mTextArea;

    VectorContentViewer(String stringData, OnTextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
        this.setLayout(new FlowLayout());
        mTextArea = new JTextArea();
        mTextArea.setText(stringData);
        mTextArea.getDocument().addDocumentListener(this);
        add(mTextArea);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (mTextWatcher != null) {
            mTextWatcher.onChange(mTextArea.getText());
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (mTextWatcher != null) {
            mTextWatcher.onChange(mTextArea.getText());
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (mTextWatcher != null) {
            mTextWatcher.onChange(mTextArea.getText());
        }
    }

    public interface OnTextWatcher {

        void onChange(String value);

    }

}
