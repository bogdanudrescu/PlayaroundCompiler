/*
 * Created on Mar 7, 2005
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 *  
 */
public class ForAnalizer extends BlockAnalizer {

    /**
     * The equal string
     */
    private static final String EQUAL_STRING = "=";

    /**
     *  
     */
    public ForAnalizer() {
        super();
    }

    /**
     * @param text
     * @throws AnalizerException
     */
    public ForAnalizer(String text) throws AnalizerException {
        super(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#getAnalizeResult()
     */
    public List getAnalizeResult() {
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.BlockInstructionsAnalizer#getInstructionId()
     */
    protected String getInstructionId() {
        return Words.FOR_ID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.BlockInstructionsAnalizer#createTopInfo(java.lang.
     * String)
     */
    protected List analizeTopInfo(String text) throws AnalizerException {
        StringTokenizer tokenizer = new StringTokenizer(text, BLANKS);

        if (tokenizer.countTokens() != 5) {
            throw new AnalizerException();
        }

        String iterator = tokenizer.nextToken();
        String is = tokenizer.nextToken();
        String start = tokenizer.nextToken();
        String to = tokenizer.nextToken();
        String end = tokenizer.nextToken();

        if (validateVariableName(iterator) && is.equals(EQUAL_STRING) && validateOperand(start)
                && to.equals(Words.TO_ID) && validateOperand(end)) {
            List list = new ArrayList();
            list.add(iterator);
            list.add(start);
            list.add(end);
            return list;
        } else {
            throw new AnalizerException();
        }
    }

    /**/
}