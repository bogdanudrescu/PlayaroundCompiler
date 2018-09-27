/*
 * Created on Dec 6, 2004
 */
package compiler.performer;

import java.util.ArrayList;
import java.util.List;

import compiler.io.IO;

/**
 * Implements the read performer
 */
public class ReadPerformer extends Performer {

    /**
     * @param entity
     */
    protected ReadPerformer(List entity) {
        super(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.translator.Translator#flush()
     */
    public List perform() {
        List result = new ArrayList();
        result.add(entity.get(1));

        IO.write(entity.get(1) + " = ");
        String value = IO.read();

        result.add(value);

        return result;
    }

    /**/
}