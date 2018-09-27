/*
 * Created on Feb 11, 2005
 */
package compiler.analizer;

import java.util.List;

import compiler.exception.AnalizerException;

/**
 * Analize the input <code>String</code> and create a <code>List</code> which
 * contains the instructions that will be performed
 */
public abstract class Analizer {

    /**
     * The blank character
     */
    public static final char BLANK = ' ';

    /**
     * The blank character
     */
    public static final char TAB = '	';

    /**
     * The blank character
     */
    public static final String BLANKS = " 	";

    /**
     * The text
     */
    protected String text;

    protected Analizer() {
    }

    /**
     * Create an <code>Analizer</code>
     * 
     * @param text
     *            the text to be analize
     * @throws AnalizerException
     */
    protected Analizer(String text) throws AnalizerException {
        analize(text);
    }

    /**
     * Analize the <code>text</code>
     * 
     * @return a list to be performed
     */
    public abstract List getAnalizeResult();

    /**
     * Used to format the text so it can be easily analize
     */
    protected abstract void analize(String text) throws AnalizerException;

    /**
     * Tests if the param is an operand
     */
    protected boolean validateOperand(String operand) {
        if (operand.length() == 0) {
            return false;
        }
        if (isLetter(operand.charAt(0))) {
            for (int i = 1; i < operand.length(); i++) {
                if (!isLetter(operand.charAt(i)) && !isNumber(operand.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else if (isNumber(operand.charAt(0))) {
            for (int i = 1; i < operand.length(); i++) {
                if (isLetter(operand.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Tests if the param is a name of variable
     */
    protected boolean validateVariableName(String name) {
        if (name.length() == 0) {
            return false;
        }
        if (isLetter(name.charAt(0))) {
            for (int i = 1; i < name.length(); i++) {
                if (!isLetter(name.charAt(i)) && !isNumber(name.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     *  
     */
    protected boolean isOperandChar(char c) {
        return (48 <= c && c <= 57) || (65 <= c && c <= 90) || (97 <= c && c <= 122);
    }

    /**
     *  
     */
    protected boolean isNumber(char c) {
        return (48 <= c && c <= 57);
    }

    /**
     *  
     */
    protected boolean isLetter(char c) {
        return (65 <= c && c <= 90) || (97 <= c && c <= 122);
    }

    /**/
}