/*
 * Created on Feb 13, 2005
 */
package compiler.performer;

import java.util.ArrayList;
import java.util.List;

import compiler.constants.Words;
import compiler.exception.UndefineVariableException;

/**
 * Implements the computations performer. The received <code>List</code> has the
 * structure: 0 - NAME 1 - a variable (R) 2 - the operators and operands list
 */
public class OperationPerformer extends Performer {

    /**
     * The plus functionality string
     */
    public static final String NULL = "$";

    /**
     * The plus functionality string
     */
    public static final String PLUS = "+";

    /**
     * The minus functionality string
     */
    public static final String MINUS = "-";

    /**
     * The multiply functionality string
     */
    public static final String MULTIPLY = "*";

    /**
     * The divide functionality string
     */
    public static final String DIVIDE = "/";

    /**
     * The divide functionality string
     */
    public static final String OPEN_BRACKET = "(";

    /**
     * The divide functionality string
     */
    public static final String CLOSE_BRACKET = ")";

    /**
     * The invalid number exception
     */
    public static final String ERROR_MESSAGE_COMPUTE = "invalid number format";

    /**
     * Create an <code>OperationPerformer</code>
     * 
     * @param entity
     * @throws UndefineVariableException
     */
    public OperationPerformer(List entity) throws UndefineVariableException {
        super(entity);
        String s;
        List list = (List) this.entity.get(2);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof String) {
                s = (String) list.get(i);
                try {
                    Double.parseDouble(s);
                } catch (NumberFormatException e) {
                    Object object = Variables.get(s);
                    if (object != null) {
                        list.set(i, Variables.get(s));
                    }
                }
            } else if (list.get(i) instanceof List) {
                List list2 = (List) list.get(i);
                if (Words.METHOD_ID.equals(list2.get(0))) {
                    MethodPerformer performer = new MethodPerformer(list2);
                    List result;
                    result = performer.perform();
                    list.set(i, result.get(1));
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.performer.Performer#perform()
     */
    public List perform() throws UndefineVariableException {
        List temp = new ArrayList();
        temp.add(NULL);

        String variableName = (String) entity.get(1);
        List operation = (List) entity.get(2);

        // The Poland Form Expresion algorithm
        List pfe = new ArrayList();
        String item;
        String tempItem;
        for (int i = 0; i < operation.size(); i++) {
            item = (String) operation.get(i);
            tempItem = (String) temp.get(0);

            if (isOperand(item)) {
                pfe.add(item);
            } else if ((isOperator(item) && priority(item) > priority(tempItem)) || item.equals(OPEN_BRACKET)) {
                temp.add(0, item);
            } else if (isOperator(item) && priority(item) <= priority(tempItem)) {
                pfe.add(temp.get(0));
                temp.remove(0);
                i--;
            } else if (item.equals(CLOSE_BRACKET)) {
                if (tempItem.equals(OPEN_BRACKET)) {
                    temp.remove(0);
                } else {
                    pfe.add(temp.get(0));
                    temp.remove(0);
                    i--;
                }
            }
        }
        for (int i = 0; i < temp.size() - 1; i++) {
            pfe.add(temp.get(i));
        }

        // The Poland Form Expresion result
        List stack = new ArrayList();
        for (int i = 0; i < pfe.size(); i++) {
            item = (String) pfe.get(i);
            if (isOperand(item)) {
                stack.add(item);
            } else if (isOperator(item)) {
                String r = compute(getValue((String) stack.get(stack.size() - 2)),
                        getValue((String) stack.get(stack.size() - 1)), item);

                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add(r);
            } else {

            }
        }

        List result = new ArrayList();
        result.add(variableName);
        result.add(stack.get(0));

        return result;
    }

    /**
     *  
     */
    private int priority(String operator) {
        if (operator.equals(NULL) || operator.equals(OPEN_BRACKET)) {
            return 0;
        } else if (operator.equals(PLUS) || operator.equals(MINUS)) {
            return 1;
        } else if (operator.equals(MULTIPLY) || operator.equals(DIVIDE)) {
            return 2;
        }
        return -1;
    }

    /**
     *  
     */
    private boolean isOperator(String string) {
        if (string.equals(NULL) || string.equals(PLUS) || string.equals(MINUS) || string.equals(MULTIPLY)
                || string.equals(DIVIDE)) {
            return true;
        }
        return false;
    }

    /**
     *  
     */
    private boolean isOperand(String string) {
        if (string.equals(NULL) || string.equals(PLUS) || string.equals(MINUS) || string.equals(MULTIPLY)
                || string.equals(DIVIDE) || string.equals(OPEN_BRACKET) || string.equals(CLOSE_BRACKET)) {
            return false;
        }
        return true;
    }

    /**
     * Compute the values with the computation type
     * 
     * @param elem0
     * @param elem1
     * @param type
     * @return
     */
    protected String compute(String elem0, String elem1, String type) {
        Double d0;
        Double d1;

        try {
            d0 = new Double(elem0);
        } catch (NumberFormatException e) {
            return ERROR_MESSAGE_COMPUTE;
        }
        try {
            d1 = new Double(elem1);
        } catch (NumberFormatException e) {
            return ERROR_MESSAGE_COMPUTE;
        }

        double r = 0;

        if (type.equals(OperationPerformer.PLUS)) {
            r = d0.doubleValue() + d1.doubleValue();
        } else if (type.equals(OperationPerformer.MINUS)) {
            r = d0.doubleValue() - d1.doubleValue();
        } else if (type.equals(OperationPerformer.MULTIPLY)) {
            r = d0.doubleValue() * d1.doubleValue();
        } else if (type.equals(OperationPerformer.DIVIDE)) {
            r = d0.doubleValue() / d1.doubleValue();
        }

        return Double.toString(r);
    }

    /**/
}