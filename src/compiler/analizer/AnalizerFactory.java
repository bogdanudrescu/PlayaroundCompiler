/*
 * Created on Feb 15, 2005
 */
package compiler.analizer;

import java.util.List;

import compiler.exception.AnalizerException;

/**
 * Analize a string
 */
public class AnalizerFactory {

    private static final int DEFAULT = 0;

    private static final int KEY_VALUE = 1;

    private static final int WRITE_TEXT = 2;

    private static final int OPERATION = 3;

    private static final int CONDITION = 4;

    private static final int FOR = 5;

    private static final int WHILE = 6;

    private int type = DEFAULT;

    private Analizer analizer;

    /**
     * Create an <code>AnalizerFactory</code>
     * 
     * @return
     */
    public static AnalizerFactory newInstance() {
        return new AnalizerFactory();
    }

    /**
     * Analize the <code>text</code> and return the proper <code>List</code> to
     * be performed
     * 
     * @param text
     * @return @throws AnalizerException
     */
    public List analize(String text) throws AnalizerException {
        List resutlt;
        if (type == DEFAULT || type == KEY_VALUE) {
            try {
                analizer = new KeyValueAnalizer(text);
                resutlt = analizer.getAnalizeResult();
                type = resutlt == null ? KEY_VALUE : DEFAULT;
                return resutlt;
            } catch (AnalizerException e) {
                // e.printStackTrace();
            }
        }
        if (type == DEFAULT || type == WRITE_TEXT) {
            try {
                analizer = new WriteTextAnalizer(text);
                resutlt = analizer.getAnalizeResult();
                type = resutlt == null ? WRITE_TEXT : DEFAULT;
                return resutlt;
            } catch (AnalizerException e) {
                // e.printStackTrace();
            }
        }
        if (type == DEFAULT || type == OPERATION) {
            try {
                analizer = new OperationAnalizer(text);
                resutlt = analizer.getAnalizeResult();
                type = resutlt == null ? OPERATION : DEFAULT;
                return resutlt;
            } catch (AnalizerException e) {
                // e.printStackTrace();
            }
        }
        if (type == DEFAULT || type == CONDITION) {
            try {
                if (type == DEFAULT) {
                    analizer = new ConditionAnalizer(text);
                } else {
                    analizer.analize(text);
                }
                resutlt = analizer.getAnalizeResult();
                type = resutlt == null ? CONDITION : DEFAULT;
                return resutlt;
            } catch (AnalizerException e) {
                // e.printStackTrace();
            }
        }
        if (type == DEFAULT || type == FOR) {
            try {
                if (type == DEFAULT) {
                    analizer = new ForAnalizer(text);
                } else {
                    analizer.analize(text);
                }
                resutlt = analizer.getAnalizeResult();
                type = resutlt == null ? FOR : DEFAULT;
                return resutlt;
            } catch (AnalizerException e) {
                // e.printStackTrace();
            }
        }
        if (type == DEFAULT || type == WHILE) {
            try {
                if (type == DEFAULT) {
                    analizer = new WhileAnalizer(text);
                } else {
                    analizer.analize(text);
                }
                resutlt = analizer.getAnalizeResult();
                type = resutlt == null ? WHILE : DEFAULT;
                return resutlt;
            } catch (AnalizerException e) {
                // e.printStackTrace();
            }
        }
        type = DEFAULT;
        throw new AnalizerException(text);
    }
}