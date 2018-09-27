/*
 * Created on Feb 15, 2005
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 *  
 */
public class KeyValueAnalizer extends Analizer {

    /**
     * The recognized keys
     */
    protected String[] keys;

    /**
     * The word of the program's header
     */
    protected String key;

    /**
     * The program's name
     */
    protected String value;

    /**
     * @param text
     * @throws AnalizerException
     */
    public KeyValueAnalizer(String text) throws AnalizerException {
        initKeys();
        analize(text);
    }

    /**
     *  
     */
    protected void initKeys() {
        keys = new String[] { Words.PROGRAM_HEADER_ID, Words.READ_ID, Words.WRITE_ID, Words.WRITELN_ID };
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#getAnalizeResult()
     */
    public List getAnalizeResult() {
        List list = new ArrayList();
        list.add(key);
        list.add(value);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#analize(java.lang.String)
     */
    protected void analize(String text) throws AnalizerException {
        /*
         * StringBuffer keyBuffer = new StringBuffer(); StringBuffer valueBuffer
         * = new StringBuffer(); char character; int elements = 0; boolean
         * resultParsed = false; for (int i = 0; i < text.length(); i++) {
         * character = text.charAt(i); if (character == BLANK || character ==
         * TAB) { if (resultParsed) { elements++; resultParsed = false; } } else
         * { if (elements == 2) { throw new AnalizerException(i + " : " +
         * character); } if (elements == 0) { keyBuffer.append(character); } if
         * (elements == 1) { valueBuffer.append(character); } resultParsed =
         * true; } }
         */

        StringTokenizer tokenizer = new StringTokenizer(text, BLANKS);
        if (tokenizer.countTokens() != 2) {
            throw new AnalizerException();
        }
        key = searchKey(tokenizer.nextToken());
        if (key == null) {
            throw new AnalizerException("Null key");
        }

        String valueString = (String) tokenizer.nextToken();
        if (!validateValue(key, valueString)) {
            throw new AnalizerException(valueString);
        }

        value = valueString;
    }

    /**
     * @param keyString
     * @return
     */
    protected String searchKey(String keyString) {
        for (int i = 0; i < keys.length; i++) {
            if (keyString.equals(keys[i])) {
                return keys[i];
            }
        }
        return null;
    }

    /**
     * @param value
     */
    protected boolean validateValue(String key, String value) {
        boolean valid = validateOperand(value);
        if (key.equals(keys[2]) || key.equals(keys[3])) {
            if (!valid) {
                if (value.charAt(0) == '\"' && value.charAt(value.length() - 1) == '\"') {
                    valid = true;
                }
            }
        }
        return valid;
    }

}