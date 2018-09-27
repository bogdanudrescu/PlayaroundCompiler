/*
 * Created on Feb 11, 2005
 */
package compiler.exception;

/**
 * Throw when the <code>Analizer</code> find an error in the code
 */
public class AnalizerException extends Exception {

    /**
     * Create an <code>AnalizerException</code>
     */
    public AnalizerException() {
    }

    /**
     * Create an <code>AnalizerException</code>
     * 
     * @param message
     *            the message to be displayed
     */
    public AnalizerException(String message) {
        super(message);
    }

    /**
     * Create an <code>AnalizerException</code>
     * 
     * @param message
     *            the message to be displayed
     */
    public AnalizerException(int column) {
        super("Analize exception at column " + column + ".");
    }

    /**/
}