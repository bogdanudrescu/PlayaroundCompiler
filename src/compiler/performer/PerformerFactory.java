/*
 * Created on Nov 28, 2004
 */
package compiler.performer;

import java.util.List;

import compiler.constants.Words;
import compiler.exception.UndefineVariableException;

/**
 * Implements a factory for the performers
 */
public class PerformerFactory {

    /**
     * Creates a <code>Performer</code> from the param list
     * 
     * @param entity
     * @return
     * @throws UndefineVariableException
     */
    public static Performer createPerformer(List entity) throws UndefineVariableException {
        if (entity.size() <= 0) {
            return null;
        }

        if (entity.get(0).equals(Words.IF_ID)) {
            return new ConditionPerformer(entity);
        }

        if (entity.get(0).equals(Words.FOR_ID)) {
            return new ForPerformer(entity);
        }

        if (entity.get(0).equals(Words.WHILE_ID)) {
            return new WhilePerformer(entity);
        }

        if (entity.get(0).equals(Words.OPERATION_ID)) {
            return new OperationPerformer(entity);
        }

        if (entity.get(0).equals(Words.READ_ID)) {
            return new ReadPerformer(entity);
        }

        if (entity.get(0).equals(Words.WRITE_ID)) {
            return new WritePerformer(entity, false);
        }

        if (entity.get(0).equals(Words.WRITELN_ID)) {
            return new WritePerformer(entity, true);
        }

        return null;
    }

    /**/
}