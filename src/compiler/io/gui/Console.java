/*
 * Created on Dec 12, 2004
 */
package compiler.io.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import compiler.io.IO;

/**
 *  
 */
public class Console extends JPanel /* implements NewLineListener */ {

    /**
     * The scroll pane
     */
    private JScrollPane scrollPane;

    /**
     * The text area
     */
    private JTextArea textArea;

    /**
     * The document
     */
    private ConsoleDocument document;

    /**
     *  
     */
    public Console() {
        document = new ConsoleDocument();
        // document.addNewLineListener(this);

        textArea = new JTextArea(document);
        textArea.setCaret(new ConsoleCaret());

        IO.setDocument(document);

        scrollPane = new JScrollPane(textArea);// ,
        // JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        // JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    /*****************************************************************************
     * (non-Javadoc)
     * 
     * @see compiler.gui.NewLineListener#newLine(compiler.gui.NewLineEvent)
     * 
     *      public void newLine(NewLineEvent e) { FontMetrics fm =
     *      getFontMetrics(textArea.getFont()); int maximum =
     *      scrollPane.getVerticalScrollBar().getMaximum();
     *      scrollPane.getVerticalScrollBar().setMaximum(maximum +
     *      fm.getHeight());
     *      scrollPane.getVerticalScrollBar().setValue(scrollPane.
     *      getVerticalScrollBar().getMaximum() -
     *      scrollPane.getVerticalScrollBar().getVisibleAmount());
     * 
     *      doLayout();
     * 
     *      scrollPane.repaint(); scrollPane.getVerticalScrollBar().repaint();
     *      textArea.repaint(); } /
     ****************************************************************************/
}