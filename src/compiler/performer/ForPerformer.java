/*
 * Created on Mar 7, 2005
 */
package compiler.performer;

import java.util.List;

import compiler.exception.UndefineVariableException;

/**
 *  
 */
public class ForPerformer extends Performer {

    /**
     * @param entity
     */
    public ForPerformer(List entity) {
        super(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.performer.Performer#perform()
     */
    public List perform() throws UndefineVariableException {
        List iteration = (List) entity.get(1);

        String iterator = (String) iteration.get(0);
        int start = getIntValue((String) iteration.get(1));
        int end = getIntValue((String) iteration.get(2));

        for (int i = start; i <= end; i++) {
            Variables.put(iterator, String.valueOf(i));
            new Launcher((List) entity.get(2)).perform((String) entity.get(0));
        }

        return null;
    }

}