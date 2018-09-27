/*
 * Created on Feb 11, 2005
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 * Analize the operation text
 */
public class OperationAnalizer extends Analizer {

    /**
     * The assignement functionality string
     */
    public static final char ASSIGNMENT = '=';

    /**
     * The plus functionality string
     */
    public static final char PLUS = '+';

    /**
     * The minus functionality string
     */
    public static final char MINUS = '-';

    /**
     * The multiply functionality string
     */
    public static final char MULTIPLY = '*';

    /**
     * The divide functionality string
     */
    public static final char DIVIDE = '/';

    /**
     * The left bracket string
     */
    public static final char OPEN_BRACKET = '(';

    /**
     * The right bracket string
     */
    public static final char CLOSE_BRACKET = ')';

    /**
     * The begin of line flag
     */
    public static final char BOL = (char) -1;

    /**
     * The end of line flag
     */
    public static final char EOL = (char) -2;

    /**
     * The leftOperator which akes the value of the operation
     */
    protected String leftOperator;

    /**
     * The text from the right of the <code>=</code>
     */
    protected List operation;

    /**
     * Create an <code>OperationAnalizer</code>
     * 
     * @param text
     *            the text to be alaize
     * @throws AnalizerException
     */
    public OperationAnalizer(String text) throws AnalizerException {
        super(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#analize()
     */
    public List getAnalizeResult() {
        List list = new ArrayList();
        list.add(Words.OPERATION_ID);
        list.add(leftOperator);
        list.add(operation);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#format(java.lang.String)
     */
    protected void analize(String text) throws AnalizerException {
        StringTokenizer equalTokenizer = new StringTokenizer(text, String.valueOf(ASSIGNMENT));
        if (equalTokenizer.countTokens() != 2) {
            throw new AnalizerException();
        }

        leftOperator = analizeLeftOperand(equalTokenizer.nextToken());
        operation = analizeRightExpresion(equalTokenizer.nextToken());
    }

    /**
     * Analize the left operator
     */
    private String analizeLeftOperand(String text) throws AnalizerException {
        StringTokenizer tokenizer = new StringTokenizer(text, BLANKS);
        if (tokenizer.countTokens() != 1) {
            throw new AnalizerException();
        }
        String left = tokenizer.nextToken();
        /*
         * char character; boolean done = false; boolean resultParsed = false;
         * for (int i = 0; i < text.length(); i++) { character = text.charAt(i);
         * if (character == BLANK || character == TAB) { if (resultParsed) {
         * done = true; } } else { if (done) { throw new AnalizerException(i +
         * " : " + character); } left.append(character); resultParsed = true; }
         * }
         */
        if (!validateOperand(left)) {
            throw new AnalizerException();
        }
        return left;
    }

    /**
     * Analize the right operation
     */
    private List analizeRightExpresion(String text) throws AnalizerException {
        List right = new ArrayList();
        StringBuffer operand = null;
        char character;
        char lastCharacter = BOL;
        int brackets = 0;
        // States whether the previous char was not BLANK
        boolean isNext = true;
        MethodAnalizer analizer = null;
        for (int i = 0; i <= text.length(); i++) {
            if (i < text.length()) {
                character = text.charAt(i);
            } else {
                character = EOL;
            }
            if (character != BLANK && character != TAB) {
                if (analizer != null) {
                    analizer.analize(String.valueOf(character));
                    List list = analizer.getAnalizeResult();
                    if (list != null) {
                        right.add(list);
                        analizer = null;
                        operand = null;
                        isNext = true;
                    }
                    lastCharacter = character;
                    continue;
                }
                if (!validate(lastCharacter, character, isNext)) {
                    analizer = new MethodAnalizer(operand.toString());
                    analizer.analize(String.valueOf(character));
                    lastCharacter = character;
                    continue;
                } else {
                    if (isOperandChar(character)) {
                        if (!isOperandChar(lastCharacter)) {
                            operand = new StringBuffer();
                        }
                        operand.append(character);
                    } else {
                        if (isOperandChar(lastCharacter)) {
                            if (!validateOperand(operand.toString())) {
                                throw new AnalizerException(i);
                            } else {
                                right.add(operand.toString());
                            }
                        }
                        if (character != EOL) {
                            right.add(String.valueOf(character));
                        }
                    }
                }
            }
            switch (character) {
                case BLANK:
                case TAB:
                    isNext = false;
                    continue;
                case OPEN_BRACKET:
                    brackets++;
                    isNext = true;
                    break;
                case CLOSE_BRACKET:
                    brackets--;
                    isNext = true;
                    break;
                default:
                    isNext = true;
                    break;
            }
            if (brackets < 0) {
                throw new AnalizerException(i);
            }
            lastCharacter = character;
        }
        if (brackets != 0) {
            throw new AnalizerException(text.length());
        }
        return right;
    }

    /**
     * Validate the operators & aperands order
     * 
     * @param lastAdded
     *            the last added char
     * @param current
     *            the current char
     * @param isNext
     *            states whether the <code>current</code> char is next from the
     *            <code>lastAdded</code>
     */
    private boolean validate(char lastAdded, char current, boolean isNext) {
        if (isOperator(current) || isOperandChar(current) || isOpenBracket(current) || isCloseBracket(current)
                || current == EOL) {
            if (!isNext) {
                if (isOperandChar(lastAdded) && isOperandChar(current)) {
                    return false;
                }
            }
            if ((lastAdded == BOL && isOperator(current)) || (isOperator(lastAdded) && isOperator(current))
                    || (isCloseBracket(lastAdded) && isOperandChar(current))
                    || (isOperandChar(lastAdded) && isOpenBracket(current))
                    || (isOpenBracket(lastAdded) && isCloseBracket(current))
                    || (isCloseBracket(lastAdded) && isOpenBracket(current))
                    || (isOperator(lastAdded) && isCloseBracket(current))
                    || (isOpenBracket(lastAdded) && isOperator(current)) || (isOperator(lastAdded) && current == EOL)) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     *  
     */
    private boolean isOperator(char c) {
        return c == PLUS || c == MINUS || c == MULTIPLY || c == DIVIDE;
    }

    /**
     *  
     */
    private boolean isOpenBracket(char c) {
        return c == OPEN_BRACKET;
    }

    /**
     *  
     */
    private boolean isCloseBracket(char c) {
        return c == CLOSE_BRACKET;
    }

    /**/
}