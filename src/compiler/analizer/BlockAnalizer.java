/*
 * Created on Mar 7, 2005
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Words;
import compiler.exception.AnalizerException;

/**
 * Analize a text sequence described as bellow: [instructionId] [topInfo] begin
 * [instruction] [instruction] ... end;
 */
public abstract class BlockAnalizer extends Analizer {

    /**
     * The instruction id
     */
    protected String instructionId;

    /**
     * The header list of the instructions
     */
    protected List headerList;

    /**
     * The iteration list contains the iteration variable name, the first value
     * and the last value of the variable
     */
    protected List topInfo;

    /**
     * The instructions list
     */
    protected List instructions;

    /**
     * The analizer result
     */
    protected List result;

    /**
     * Create an <code>AnalizerFactory</code>
     */
    protected AnalizerFactory factory;

    /**
     *  
     */
    public BlockAnalizer() {
        super();
    }

    /**
     * @param text
     * @throws AnalizerException
     */
    public BlockAnalizer(String text) throws AnalizerException {
        instructionId = getInstructionId();
        headerList = new ArrayList();
        headerList.add(Words.PROGRAM_HEADER_ID);
        headerList.add(instructionId);

        factory = AnalizerFactory.newInstance();

        analize(text);
    }

    /**
     * Gets the instruction id
     * 
     * @return
     */
    protected abstract String getInstructionId();

    /**
     * Create the result of the info in the top of the text, imediattly after
     * the instruction base word
     * 
     * @param text
     * @return
     */
    protected abstract List analizeTopInfo(String text) throws AnalizerException;

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#getAnalizeResult()
     */
    public List getAnalizeResult() {
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#analize(java.lang.String)
     */
    protected void analize(String text) throws AnalizerException {
        if (topInfo == null) {
            String[] strings = text.split(Words.BEGIN_ID);
            if (strings.length < 2) {
                throw new AnalizerException();
            }
            for (int i = 2; i < strings.length; i++) {
                strings[1] = strings[1] + BLANK + Words.BEGIN_ID + BLANK + strings[i];
                strings[i] = null;
            }
            String[] strings2 = strings[0].split(instructionId);
            if (strings2.length != 2) {
                throw new AnalizerException();
            }
            topInfo = analizeTopInfo(strings2[1]);
            instructions = new ArrayList();
            instructions.add(headerList);
            List analizeList = factory.analize(strings[1]);
            if (analizeList != null) {
                instructions.add(analizeList);
            }
        } else {
            try {
                List analizeList = factory.analize(text);
                if (analizeList != null) {
                    instructions.add(analizeList);
                }
            } catch (AnalizerException e) {
                StringTokenizer tokenizer = new StringTokenizer(text, BLANKS);
                if (tokenizer.countTokens() != 1) {
                    throw new AnalizerException();
                }
                if (Words.END_ID.equals((String) tokenizer.nextElement())) {
                    result = new ArrayList();
                    result.add(instructionId);
                    result.add(topInfo);
                    result.add(instructions);
                } else {
                    throw new AnalizerException();
                }
            }
        }
    }

    /**/
}