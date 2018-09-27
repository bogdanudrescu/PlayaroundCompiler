/*
 * Created on Mar 7, 2005
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Condition;
import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 *  
 */
public class WhileAnalizer extends BlockAnalizer {

    /**
     * The operators list
     */
    private static final String[] operators = { Condition.EQUAL, Condition.DIFFERENT, Condition.GREATER,
            Condition.GREATER_EQUAL, Condition.SMALLER, Condition.SMALLER_EQUAL };

    /**
     *  
     */
    public WhileAnalizer() {
        super();
    }

    /**
     * @param text
     * @throws AnalizerException
     */
    public WhileAnalizer(String text) throws AnalizerException {
        super(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.BlockAnalizer#getInstructionId()
     */
    protected String getInstructionId() {
        return Words.WHILE_ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.BlockAnalizer#analizeTopInfo(java.lang.String)
     */
    protected List analizeTopInfo(String text) throws AnalizerException {
        List result = new ArrayList();

        String operator = null;
        for (int i = 0; i < operators.length; i++) {
            if (text.indexOf(operators[i]) != -1) {
                operator = operators[i];
            }
        }

        if (operator == null) {
            throw new AnalizerException();
        }

        String[] strings = text.split(operator);

        if (strings.length != 2) {
            throw new AnalizerException();
        }

        StringTokenizer tokenizer = new StringTokenizer(strings[0]);
        StringTokenizer tokenizer2 = new StringTokenizer(strings[1]);

        if (tokenizer.countTokens() != 1 || tokenizer2.countTokens() != 1) {
            throw new AnalizerException();
        }

        String v0 = tokenizer.nextToken();
        String v1 = tokenizer2.nextToken();

        if (!validateOperand(v0) || !validateOperand(v1)) {
            throw new AnalizerException();
        }

        result.add(operator);
        result.add(v0);
        result.add(v1);

        return result;
    }

}