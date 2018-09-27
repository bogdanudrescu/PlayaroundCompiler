/*
 *
 */
package compiler.io.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.UndoableEditEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import compiler.io.IO;

/**
 *  
 */
public class ConsoleDocument extends PlainDocument {

    /**
     * The begin position of the input text
     */
    private int begin;

    /**
     * The end position of the input text
     */
    private int end;

    /**
     * The current row
     */
    private int row;

    /**
     * The read stream text
     */
    private int rowLead;

    /**
     * The Enter key pressed state
     */
    private boolean isEnter;

    /**
     * Notify them when new line
     */
    protected List newLineListeners;

    /**
     * Creates a console document
     */
    public ConsoleDocument() {
        init();
        newLineListeners = new ArrayList();
    }

    /**
     * Init the console
     */
    private void init() {
        try {
            insertString(0, IO.SHOW_CONSOLE, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        begin = end = getContent().length() - 1;
        row = rowLead = 0;
        isEnter = false;
    }

    /**
     * Adds a new line listener
     * 
     * @param listener
     */
    public void addNewLineListener(NewLineListener listener) {
        newLineListeners.add(listener);
    }

    /**
     * Removes a new line listener
     * 
     * @param listener
     */
    public void removeNewLineListener(NewLineListener listener) {
        newLineListeners.remove(listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String,
     * javax.swing.text.AttributeSet)
     */
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str.equals("	")) {
            return;
        }
        if (offs >= begin) {
            if (str.equals("\n")) {
                isEnter = true;
            } else {
                super.insertString(offs, str, a);
            }
            end = getContent().length() - 1;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.AbstractDocument#remove(int, int)
     */
    public void remove(int offs, int len) throws BadLocationException {
        if (offs >= begin) {
            super.remove(offs, len);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.text.AbstractDocument#fireUndoableEditUpdate(javax.swing.
     * event.UndoableEditEvent)
     */
    protected void fireUndoableEditUpdate(UndoableEditEvent e) {
    }

    /**
     * @return Returns the begin.
     */
    public int getBegin() {
        return begin;
    }

    /**
     * @return Returns the end.
     */
    public int getEnd() {
        return end;
    }

    /**
     * @return Returns the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return Returns the rowLead.
     */
    public int getRowLead() {
        return rowLead;
    }

    /**
     * Read the input text
     * 
     * @return
     */
    public String read() {
        while (!isEnter) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e1) {
            }
        }
        isEnter = false;

        String text = null;
        try {
            text = getContent().getString(begin, end - begin);
        } catch (BadLocationException e) {
        } finally {
            writeln();
        }
        return text;
    }

    /**
     * Write the output text
     * 
     * @param text
     */
    public void write(String text) {
        try {
            if (text.equals(IO.SHOW_CONSOLE)) {
                super.insertString(getContent().length() - 1, IO.SHOW_CONSOLE, null);
            } else {
                int index = text.indexOf("\n");
                if (index >= 0) {
                    super.insertString(getContent().length() - 1, text.substring(0, index), null);
                    writeln();
                    super.insertString(getContent().length() - 1, text.substring(index + 1, text.length()), null);
                } else {
                    super.insertString(getContent().length() - 1, text, null);
                }
            }
            begin = end = getContent().length() - 1;
        } catch (BadLocationException e) {
        }
    }

    /**
     * Write a new line
     */
    protected void writeln() {
        try {
            super.insertString(getContent().length() - 1, "\n", null);
        } catch (BadLocationException e1) {
        }
        row++;
        begin = end = rowLead = getContent().length() - 1;

        notifyNewLineListeners();
    }

    /**
     * Notify listeners
     */
    protected void notifyNewLineListeners() {
        for (int i = 0; i < newLineListeners.size(); i++) {
            ((NewLineListener) newLineListeners.get(i)).newLine(new NewLineEvent(this, row, 1));
        }
    }

    /**
     * 
     * @param text
     */
    public void clear() {
        begin = end = row = rowLead = 0;
        try {
            super.remove(0, getLength());
        } catch (BadLocationException e) {
        }
        write(IO.SHOW_CONSOLE);
    }

    /**/
}