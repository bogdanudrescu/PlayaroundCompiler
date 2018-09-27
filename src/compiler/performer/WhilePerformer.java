/*
 * Created on Mar 7, 2005
 */
package compiler.performer;

import java.util.List;

import compiler.constants.Condition;
import compiler.exception.UndefineVariableException;

/**
 *  
 */
public class WhilePerformer extends Performer {

    /**
     * @param entity
     */
    public WhilePerformer(List entity) {
        super(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.performer.Performer#perform()
     */
    public List perform() throws UndefineVariableException {
        List iteration = (List) entity.get(1);

        String operator = (String) iteration.get(0);
        String operand0 = (String) iteration.get(1);
        String operand1 = (String) iteration.get(2);

        while (isPerforming(operator, operand0, operand1)) {
            new Launcher((List) entity.get(2)).perform((String) entity.get(0));
        }

        return null;
    }

    /**
     * Gets the condition result
     * 
     * @param operator
     *            the condition operator
     * @param v0
     *            the condition operand
     * @param v1
     *            the condition operand
     * @return the value of the operation
     * @throws UndefineVariableException
     */
    private boolean isPerforming(String operator, String v0, String v1) throws UndefineVariableException {
        double d0 = getDoubleValue(v0);
        double d1 = getDoubleValue(v1);
        boolean result = false;

        if (operator.equals(Condition.EQUAL)) {
            result = d0 == d1;
        } else if (operator.equals(Condition.DIFFERENT)) {
            result = d0 != d1;
        } else if (operator.equals(Condition.GREATER)) {
            result = d0 > d1;
        } else if (operator.equals(Condition.GREATER_EQUAL)) {
            result = d0 >= d1;
        } else if (operator.equals(Condition.SMALLER)) {
            result = d0 < d1;
        } else if (operator.equals(Condition.SMALLER_EQUAL)) {
            result = d0 <= d1;
        }

        return result;
    }

    /**/
}