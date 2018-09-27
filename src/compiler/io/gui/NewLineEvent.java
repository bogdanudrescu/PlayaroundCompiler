/*
 * Created on Dec 19, 2004
 */
package compiler.io.gui;

/**
 * An event
 */
public class NewLineEvent {

    /**
     * The source
     */
    private ConsoleDocument document;

    /**
     * The new row index
     */
    private int row;

    /**
     * The number of extended rows
     */
    private int extend;

    /**
     * Creates a new line event
     * 
     * @param document
     * @param row
     * @param extend
     */
    public NewLineEvent(ConsoleDocument document, int row, int extend) {
        this.document = document;
        this.row = row;
        this.extend = extend;
    }

    /**
     * @return Returns the document.
     */
    public ConsoleDocument getDocument() {
        return document;
    }

    /**
     * @return Returns the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return Returns the extend.
     */
    public int getExtend() {
        return extend;
    }

    /**/
}