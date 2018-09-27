/*
 * Created on Dec 19, 2004
 */
package compiler.io.gui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;

/**
 *  
 */
public class ConsoleCaret implements Caret {

    protected class UpdateHandler implements PropertyChangeListener, DocumentListener, ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
         * PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent evt) {
            // System.out.println("propertyChange");
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.
         * DocumentEvent)
         */
        public void changedUpdate(DocumentEvent e) {
            // System.out.println("changedUpdate");
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.
         * DocumentEvent)
         */
        public void insertUpdate(DocumentEvent e) {
            // System.out.println("insertUpdate");
            setDotValue(dot + e.getLength());
            // try {
            // System.out.println(component.getUI().modelToView(component, dot,
            // Position.Bias.Forward));
            // component.scrollRectToVisible(component.getUI().modelToView(component,
            // dot, Position.Bias.Forward));
            // throw new BadLocationException(e.getDocument().getText(0,
            // e.getDocument().getLength()), 0);
            // } catch (BadLocationException e1) {
            // e1.printStackTrace();
            // }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.
         * DocumentEvent)
         */
        public void removeUpdate(DocumentEvent e) {
            // System.out.println("removeUpdate");
            if (dot > e.getOffset()) {
                setDotValue(dot - e.getLength());
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
         * ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // System.out.println("actionPerformed");
        }

    }

    /**
     * The dot
     */
    protected int dot;

    /**
     * The caret's component
     */
    protected JTextComponent component;

    /**
     * The update handler
     */
    protected UpdateHandler updateHandler;

    /**
     * The visible state
     */
    protected boolean visible;

    /**
     * The document
     */
    protected ConsoleDocument doc;

    /**
     * The <code>ChangeListner</code> list
     */
    protected List changeListeners;

    /**
     * Creates a <code>ConsoleCaret</code>
     */
    public ConsoleCaret() {
        dot = 0;
        visible = false;

        updateHandler = new UpdateHandler();
        changeListeners = new ArrayList();
    }

    /**
     * Sets the dot's value
     * 
     * @param dot
     */
    protected void setDotValue(int dot) {
        // System.out.println("begin: " + doc.getBegin());
        if (dot >= doc.getBegin()) {
            this.dot = dot;
        } else {
            this.dot = doc.getBegin();
        }

        notifyListeners();
        new Thread() {

            public void run() {
                try {
                    component.scrollRectToVisible(
                            component.getUI().modelToView(component, ConsoleCaret.this.dot, Position.Bias.Forward));
                } catch (BadLocationException e) {
                }
            }
        }.start();
    }

    /**
     *  
     */
    private void notifyListeners() {
        for (int i = 0; i < changeListeners.size(); i++) {
            ((ChangeListener) changeListeners.get(i)).stateChanged(new ChangeEvent(this));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.DefaultCaret#setDot(int)
     */
    public void setDot(int dot) {
        setDotValue(dot);
        // System.out.println("setDot: " + this.dot);
        component.repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#getBlinkRate()
     */
    public int getBlinkRate() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#getDot()
     */
    public int getDot() {
        return dot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#getMark()
     */
    public int getMark() {
        return dot;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#isSelectionVisible()
     */
    public boolean isSelectionVisible() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#isVisible()
     */
    public boolean isVisible() {
        return visible;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#moveDot(int)
     */
    public void moveDot(int dot) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#setBlinkRate(int)
     */
    public void setBlinkRate(int rate) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#setSelectionVisible(boolean)
     */
    public void setSelectionVisible(boolean v) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#setVisible(boolean)
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
        if (dot < doc.getBegin()) {
            dot = doc.getBegin();
            // } else if (dot > doc.getEnd()) {
            // System.out.println("paint: " + dot + " " + doc.getBegin() + " " +
            // doc.getEnd());
            // dot = doc.getEnd();
        }

        // try {
        FontMetrics fm = g.getFontMetrics(component.getFont());
        String text = component.getText().substring(doc.getRowLead(), dot);
        int x = fm.stringWidth(text);
        int height = fm.getHeight();
        int y = doc.getRow() * height;
        g.drawLine(x, y, x, y + height - 1);
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // System.out.println(dot + " " + doc.getBegin() + " " + doc.getEnd());
        // System.out.println("\"" + component.getText() + "\"");
        // }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#getMagicCaretPosition()
     */
    public Point getMagicCaretPosition() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#setMagicCaretPosition(java.awt.Point)
     */
    public void setMagicCaretPosition(Point p) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#addChangeListener(javax.swing.event.
     * ChangeListener)
     */
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#removeChangeListener(javax.swing.event.
     * ChangeListener)
     */
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#deinstall(javax.swing.text.JTextComponent)
     */
    public void deinstall(JTextComponent c) {
        c.removePropertyChangeListener(updateHandler);
        Document doc = c.getDocument();
        if (doc != null) {
            doc.removeDocumentListener(updateHandler);
        }
        synchronized (this) {
            component = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.text.Caret#install(javax.swing.text.JTextComponent)
     */
    public void install(JTextComponent c) {
        component = c;
        doc = (ConsoleDocument) c.getDocument();
        doc.addDocumentListener(updateHandler);
        dot = doc.getBegin();

        c.addPropertyChangeListener(updateHandler);
    }

    /**/
}