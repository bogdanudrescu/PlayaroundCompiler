/*
 * Created on Nov 28, 2004
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 * Test class for the translators
 */
public class Compiler {

    /**
     * Tokens
     */
    public static final String DELIMITATOR = ";";

    public static final String NEW_LINE = "\n";

    /**
     * Compile the text received
     * 
     * @param text
     * @return
     */
    public List compile(String text) throws AnalizerException {
        AnalizerFactory factory = AnalizerFactory.newInstance();
        StringTokenizer tokenizer = new StringTokenizer(text, NEW_LINE);
        StringBuffer buffer = new StringBuffer();
        String blank = String.valueOf(Analizer.BLANK);
        while (tokenizer.hasMoreElements()) {
            buffer.append(blank + tokenizer.nextElement());
        }

        StringTokenizer tokenizer2 = new StringTokenizer(buffer.toString(), DELIMITATOR);
        String element;
        List list = new ArrayList();
        List program = null;
        while (tokenizer2.hasMoreElements()) {
            element = (String) tokenizer2.nextElement();
            List programLine = factory.analize(element);

            if (programLine != null) {
                System.out.println(programLine);
                if (Words.PROGRAM_HEADER_ID.equals((String) programLine.get(0))) {
                    if (program != null) {
                        list.add(program);
                    }
                    program = new ArrayList();
                }
                program.add(programLine);
            }
        }
        if (program != null) {
            list.add(program);
        }

        return list;
    }

    /**/
}