/*
 * Created on Feb 23, 2005
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Condition;
import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 * Implements the <code>if</code> condition analizer
 */
public class ConditionAnalizer extends Analizer {

    /**
     * The operators list
     */
    private static final String[] operators = { Condition.EQUAL, Condition.DIFFERENT, Condition.GREATER,
            Condition.GREATER_EQUAL, Condition.SMALLER, Condition.SMALLER_EQUAL };

    /**
     * The header list of the instructions
     */
    private static final List ifList = new ArrayList();

    static {
        ifList.add(Words.PROGRAM_HEADER_ID);
        ifList.add(Words.IF_ID);
    }

    /**
     * The analizer result
     */
    private List result;

    /**
     * The condition list
     */
    private List condition;

    /**
     * The instructions list
     */
    private List ifInstructions;

    /**
     * The instructions list
     */
    private List elseInstructions;

    /**
     * Create an <code>AnalizerFactory</code>
     */
    private AnalizerFactory factory;

    /**
     * Temporary list contains an analized instruction
     */
    private List analizeList;

    /**
     * @param text
     * @throws AnalizerException
     */
    public ConditionAnalizer(String text) throws AnalizerException {
        factory = AnalizerFactory.newInstance();
        analize(text);
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
     * @see compiler.analizer.Analizer#analize(java.lang.String)
     */
    protected void analize(String text) throws AnalizerException {
        if (condition == null) {
            String[] strings = text.split(Words.BEGIN_ID);
            if (strings.length < 2) {
                throw new AnalizerException();
            }
            for (int i = 2; i < strings.length; i++) {
                strings[1] = strings[1] + BLANK + Words.BEGIN_ID + BLANK + strings[i];
                strings[i] = null;
            }
            String[] strings2 = strings[0].split(Words.IF_ID);
            if (strings2.length != 2) {
                throw new AnalizerException();
            }
            condition = analizeCondition(strings2[1]);
            ifInstructions = new ArrayList();
            ifInstructions.add(ifList);
            analizeList = factory.analize(strings[1]);
            if (analizeList != null) {
                ifInstructions.add(analizeList);
            }
        } else if (text.indexOf(Words.ELSE_ID) != -1 && analizeList != null) {
            // test if after ELSE is BEGIN
            StringTokenizer tokenizer = new StringTokenizer(text, BLANKS);
            if (tokenizer.countTokens() < 2) {
                throw new AnalizerException();
            }
            if (!(tokenizer.nextToken().equals(Words.ELSE_ID) && tokenizer.nextToken().equals(Words.BEGIN_ID))) {
                throw new AnalizerException();
            }
            String[] strings = text.split(Words.BEGIN_ID);
            if (strings.length < 2) {
                throw new AnalizerException();
            }
            for (int i = 2; i < strings.length; i++) {
                strings[1] = strings[1] + BLANK + Words.BEGIN_ID + BLANK + strings[i];
                strings[i] = null;
            }
            String[] strings2 = strings[0].split(Words.ELSE_ID);
            if (strings2.length != 2) {
                throw new AnalizerException();
            }
            elseInstructions = new ArrayList();
            elseInstructions.add(ifList);
            analizeList = factory.analize(strings[1]);
            if (analizeList != null) {
                elseInstructions.add(analizeList);
            }
        } else {
            try {
                analizeList = factory.analize(text);
                if (analizeList != null) {
                    if (elseInstructions == null) {
                        ifInstructions.add(analizeList);
                    } else {
                        elseInstructions.add(analizeList);
                    }
                }
            } catch (AnalizerException e) {
                StringTokenizer tokenizer = new StringTokenizer(text, BLANKS);
                if (tokenizer.countTokens() != 1) {
                    throw new AnalizerException();
                }
                if (Words.END_ID.equals((String) tokenizer.nextElement())) {
                    result = new ArrayList();
                    result.add(Words.IF_ID);
                    result.add(condition);
                    result.add(ifInstructions);
                    result.add(elseInstructions);
                } else {
                    throw new AnalizerException();
                }
            }
        }
    }

    /**
     *  
     */
    protected List analizeCondition(String text) throws AnalizerException {
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

    /**/
}