package com.github.megatronking.svg.applet.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class VectorContentViewer extends JComponent implements DocumentListener {

    private OnTextWatcher mTextWatcher;

    private JTextArea mTextArea;

    VectorContentViewer(String stringData, OnTextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
        setLayout(new GridLayout(1, 1));
        mTextArea = new JTextArea();
        mTextArea.setText(stringData);
        mTextArea.getDocument().addDocumentListener(this);
        mTextArea.setMinimumSize(new Dimension(200, 200));
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
