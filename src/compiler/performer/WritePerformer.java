/*
 * Created on Dec 7, 2004
 */
package compiler.performer;

import java.util.ArrayList;
import java.util.List;

import compiler.constants.Words;
import compiler.io.IO;

/**
 * Implements the write performer
 */
public class WritePerformer extends Performer {

    /**
     * The new line state
     */
    private boolean newLine;

    /**
     * Creates a write translator
     */
    public WritePerformer(List entity, boolean newLine) {
        super(entity);
        this.newLine = newLine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.translator.Translator#flush()
     */
    public List perform() {
        List result = new ArrayList();

        String string = (String) entity.get(1);
        if (string.startsWith("\"") && string.endsWith("\"") && string.length() > 1) {
            result.add(Words.RESULT_ID);
            result.add(string.substring(1, string.length() - 1));
            IO.write((String) result.get(1));
        } else {
            try {
                Double.parseDouble((String) entity.get(1));
                result.add(Words.RESULT_ID);
                result.add((String) entity.get(1));
            } catch (NumberFormatException e) {
                result.add((String) entity.get(1));
                result.add(Variables.get((String) entity.get(1)));
            }
            IO.write((String) result.get(1));
        }
        if (newLine) {
            IO.write("\n");
        }

        return result;
    }

    /**/
}