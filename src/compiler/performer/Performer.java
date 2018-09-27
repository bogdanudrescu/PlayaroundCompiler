/*
 * Created on Nov 28, 2004
 */
package compiler.performer;

import java.util.List;

import compiler.exception.UndefineVariableException;
import compiler.util.Util;

/**
 * Receiving a <code>List</code> and generate the runnable code for it
 * <p>
 * The result of the <code>perform()</code> method is a 2 elements
 * <code>List</code>: 0: the variable's name 1: the variable's value
 */
public abstract class Performer {

    /**
     * The incomeing list
     */
    protected List entity;

    /**
     * Creates a translator
     * 
     * @param entity
     * @param in
     * @param out
     */
    protected Performer(List entity) {
        // this.entity = new ArrayList();
        // for (int i = 0; i < entity.size(); i++) {
        // if (entity.get(i) instanceof List) {
        //
        // } else {
        // this.entity.add(entity.get(i));
        // }
        // }
        this.entity = Util.copy(entity);
    }

    /**
     * Flush the translation
     */
    public abstract List perform() throws UndefineVariableException;

    /**
     * Gets the operand value
     * 
     * @param name
     *            the onerand input text
     * @return the <code>String</code> value
     * @throws UndefineVariableException
     */
    protected String getValue(String name) throws UndefineVariableException {
        try {
            Double.parseDouble(name);
            return name;
        } catch (NumberFormatException e) {
            String value = Variables.get(name);
            if (value == null) {
                throw new UndefineVariableException("Variable " + name + " is undefined.");
            }
            return value;
        }
    }

    /**
     * Gets the operand value
     * 
     * @param name
     *            the onerand input text
     * @return the <code>String</code> value
     * @throws UndefineVariableException
     */
    protected double getDoubleValue(String name) throws UndefineVariableException {
        double value;
        try {
            value = Double.parseDouble(name);
            return value;
        } catch (NumberFormatException e) {
            String stringValue = Variables.get(name);
            if (stringValue == null) {
                throw new UndefineVariableException("Variable " + name + " is undefined.");
            }
            try {
                value = Double.parseDouble(stringValue);
            } catch (NumberFormatException e1) {
                throw new UndefineVariableException(e1.getMessage());
            }
            return value;
        }
    }

    /**
     * Gets the operand int value
     * 
     * @param name
     * @return @throws UndefineVariableException
     */
    protected int getIntValue(String name) throws UndefineVariableException {
        return new Double(getDoubleValue(name)).intValue();
    }

    /**/
}