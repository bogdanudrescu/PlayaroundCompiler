/*
 * Created on Feb 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package compiler.analizer;

import java.util.StringTokenizer;

import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class WriteTextAnalizer extends KeyValueAnalizer {

    /**
     * @param text
     * @throws AnalizerException
     */
    public WriteTextAnalizer(String text) throws AnalizerException {
        super(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.KeyValueAnalizer#initKeys()
     */
    protected void initKeys() {
        keys = new String[] { Words.WRITE_ID, Words.WRITELN_ID };
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.KeyValueAnalizer#analize(java.lang.String)
     */
    protected void analize(String text) throws AnalizerException {
        int begin = text.indexOf('\"');
        if (begin == -1) {
            throw new AnalizerException();
        }
        int end = text.indexOf('\"', begin + 1);
        if (end == -1) {
            throw new AnalizerException();
        }

        String string = text.substring(begin, end + 1);
        String[] strings = text.split(string);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < strings.length; i++) {
            buffer.append(strings[i]);
        }
        StringTokenizer tokenizer = new StringTokenizer(buffer.toString(), BLANKS);
        if (tokenizer.countTokens() != 1) {
            throw new AnalizerException();
        }

        key = searchKey(tokenizer.nextToken());
        if (key == null) {
            throw new AnalizerException("Null key");
        }

        value = string;
    }

    /**/
}