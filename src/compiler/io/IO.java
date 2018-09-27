/*
 * Created on Dec 6, 2004
 */
package compiler.io;

import compiler.io.gui.ConsoleDocument;

/**
 * The IO manager
 */
public class IO {

    /**
     * The console text
     */
    public static final String SHOW_CONSOLE = "? - ";

    /**
     * The console text
     */
    public static final String NEW_LINE = "\n";

    /**
     * The console text
     */
    public static final String ERROR = "Undefine command or program name.";

    /**
     * The console's document
     */
    private static ConsoleDocument document;

    /**
     * @param document
     *            The document to set.
     */
    public static void setDocument(ConsoleDocument document) {
        IO.document = document;
    }

    /**
     * Read from the reader
     * 
     * @return
     */
    public static String read() {
        return document.read();
    }

    /**
     * Write the string
     * 
     * @param string
     */
    public static void write(String string) {
        document.write(string);
    }

    /**
     * Show the console
     */
    public static void showConsole() {
        write(NEW_LINE + SHOW_CONSOLE);
    }

    /**
     * Show the error string
     */
    public static void error() {
        write(ERROR);
    }

    /**
     * Clear the console content
     */
    public static void clear() {
        document.clear();
    }

    /**/
}