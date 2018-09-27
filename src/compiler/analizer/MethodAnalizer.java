/*
 * Created on Feb 28, 2005
 */
package compiler.analizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.constants.Methods;
import compiler.constants.Words;
import compiler.exception.AnalizerException;
import compiler.performer.OperationPerformer;

/**
 *  
 */
public class MethodAnalizer extends Analizer {

    /**
     * The methods name list
     */
    private static String[] methods = { Methods.E, Methods.PI, Methods.SQRT, Methods.MIN, Methods.MAX, Methods.ABS,
            Methods.POW, Methods.EXP, Methods.LOG, Methods.ACOS, Methods.COS, Methods.ASIN, Methods.SIN, Methods.ATAN,
            Methods.TAN, Methods.RANDOM };

    /**
     * The method name
     */
    private String methodName;

    /**
     * The params text
     */
    private StringBuffer params;

    /**
     * The params list
     */
    private List paramsList;

    /**
     * @param text
     * @throws AnalizerException
     */
    public MethodAnalizer(String text) throws AnalizerException {
        super(text);
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#getAnalizeResult()
     */
    public List getAnalizeResult() {
        if (paramsList == null) {
            return null;
        }
        List list = new ArrayList();
        list.add(Words.METHOD_ID);
        list.add(methodName);
        list.add(paramsList);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.analizer.Analizer#analize(java.lang.String)
     */
    protected void analize(String text) throws AnalizerException {
        if (methodName == null) {
            for (int i = 0; i < methods.length; i++) {
                if (text.equals(methods[i])) {
                    methodName = methods[i];
                    break;
                }
            }
            if (methodName == null) {
                throw new AnalizerException();
            }
        } else if (text.equals(OperationPerformer.OPEN_BRACKET)) {
            params = new StringBuffer();
        } else if (text.equals(OperationPerformer.CLOSE_BRACKET)) {
            paramsList = createParamsList(params.toString());
            if (paramsList.size() != countParams(methodName)) {
                throw new AnalizerException();
            }
        } else {
            if (params == null) {
                throw new AnalizerException();
            }
            params.append(text);
        }

    }

    /**
     * @param text
     * @return @throws AnalizerException
     */
    private List createParamsList(String text) throws AnalizerException {
        // Test whether the text is blank (no parameters)
        StringTokenizer tokenizer = new StringTokenizer(text, BLANKS);
        if (tokenizer.countTokens() == 0) {
            return new ArrayList();
        }

        List list = new ArrayList();
        String[] strings = text.split(",");
        for (int i = 0; i < strings.length; i++) {
            tokenizer = new StringTokenizer(strings[i], BLANKS);
            if (tokenizer.countTokens() != 1) {
                throw new AnalizerException();
            }
            list.add(tokenizer.nextToken());
        }
        return list;
    }

    /**
     * @param methodName
     *            the method name
     * @return the method's params number
     */
    private int countParams(String methodName) {
        if (methodName.equals(Methods.E)) {
            return 0;
        } else if (methodName.equals(Methods.PI)) {
            return 0;
        } else if (methodName.equals(Methods.SQRT)) {
            return 1;
        } else if (methodName.equals(Methods.MIN)) {
            return 2;
        } else if (methodName.equals(Methods.MAX)) {
            return 2;
        } else if (methodName.equals(Methods.ABS)) {
            return 1;
        } else if (methodName.equals(Methods.POW)) {
            return 1;
        } else if (methodName.equals(Methods.EXP)) {
            return 1;
        } else if (methodName.equals(Methods.LOG)) {
            return 1;
        } else if (methodName.equals(Methods.ACOS)) {
            return 1;
        } else if (methodName.equals(Methods.COS)) {
            return 1;
        } else if (methodName.equals(Methods.ASIN)) {
            return 1;
        } else if (methodName.equals(Methods.SIN)) {
            return 1;
        } else if (methodName.equals(Methods.ATAN)) {
            return 1;
        } else if (methodName.equals(Methods.TAN)) {
            return 1;
        } else if (methodName.equals(Methods.RANDOM)) {
            return 0;
        }
        return 0;
    }

    /**
     * Test whether the text is the name of a method
     * 
     * @param text
     * @return
     */
    public static boolean isMethodName(String text) {
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**/
}