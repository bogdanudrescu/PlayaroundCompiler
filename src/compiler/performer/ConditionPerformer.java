/*
 * Created on Feb 24, 2005
 */
package compiler.performer;

import java.util.List;

import compiler.constants.Condition;
import compiler.exception.UndefineVariableException;

/**
 * Perform the if instruction The received <code>List</code> has the structure:
 * 0 - NAME 1 - the condition list 2 - the <code>if</code> branch program to
 * execute 3 - the <code>else</code> branch program to execute
 */
public class ConditionPerformer extends Performer {

    /**
     * @param entity
     */
    public ConditionPerformer(List entity) {
        super(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.performer.Performer#perform()
     */
    public List perform() throws UndefineVariableException {

        List condition = (List) entity.get(1);
        if (isPerforming((String) condition.get(0), (String) condition.get(1), (String) condition.get(2))) {
            new Launcher((List) entity.get(2)).perform((String) entity.get(0));
        } else if (entity.get(3) != null) {
            new Launcher((List) entity.get(3)).perform((String) entity.get(0));
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