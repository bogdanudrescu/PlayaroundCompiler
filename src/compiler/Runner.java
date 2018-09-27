/*
 * Created on Feb 15, 2005
 */
package compiler;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import compiler.io.IO;
import compiler.io.gui.Console;
import compiler.performer.Launcher;
import compiler.performer.Variables;

/**
 * Run the program
 */
public class Runner implements Runnable {

    /**
     * The exit console text
     */
    public static final String CLOSE = "close";

    /**
     * The exit console text
     */
    public static final String CLEAR = "clear";

    /**
     * The compiled list
     */
    private List list;

    /**
     * The <code>Runner</code>'s frame
     */
    private JFrame frame;

    /**
     * Create a <code>Runner</code>
     */
    public Runner() {
        Console console = new Console();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add(console);

        frame.setBounds(150, 150, 800, 600);

        new Thread(this, "Runner").start();
    }

    /**
     * Starts the performing of <code>list</code>
     * 
     * @param list
     */
    public void startPerform(List list) {
        this.list = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            this.list.add(new Launcher((List) list.get(i)));
        }

        if (!frame.isVisible()) {
            frame.setVisible(true);
        }

        frame.toFront();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        String result;
        boolean performed;
        do {
            performed = false;
            result = IO.read();
            if (result.equals(CLOSE)) {
                frame.setVisible(false);
                IO.clear();
                continue;
            } else if (result.equals(CLEAR)) {
                IO.clear();
                continue;
            }
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (((Launcher) list.get(i)).perform(result)) {
                        performed = true;
                        break;
                    }
                }
            }
            if (!performed) {
                IO.error();
            }
            IO.showConsole();
            Variables.clear();
        } while (true);
    }

    /**/
}