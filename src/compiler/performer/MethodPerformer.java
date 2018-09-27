/*
 * Created on Feb 28, 2005
 */
package compiler.performer;

import java.util.ArrayList;
import java.util.List;

import compiler.constants.Methods;
import compiler.constants.Words;
import compiler.exception.UndefineVariableException;

/**
 *  
 */
public class MethodPerformer extends Performer {

    /**
     * @param entity
     */
    public MethodPerformer(List entity) {
        super(entity);
        String s;
        List list = (List) this.entity.get(2);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof String) {
                s = (String) list.get(i);
                try {
                    Double.parseDouble(s);
                } catch (NumberFormatException e) {
                    Object object = Variables.get(s);
                    if (object != null) {
                        list.set(i, Variables.get(s));
                    }
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see compiler.performer.Performer#perform()
     */
    public List perform() throws UndefineVariableException {
        String method = (String) entity.get(1);
        List params = (List) entity.get(2);
        String r = "null";
        try {
            if (method.equals(Methods.E)) {
                r = e();
            } else if (method.equals(Methods.PI)) {
                r = pi();
            } else if (method.equals(Methods.SQRT)) {
                r = sqrt((String) params.get(0));
            } else if (method.equals(Methods.MIN)) {
                r = min((String) params.get(0), (String) params.get(1));
            } else if (method.equals(Methods.MAX)) {
                r = max((String) params.get(0), (String) params.get(1));
            } else if (method.equals(Methods.ABS)) {
                r = abs((String) params.get(0));
            } else if (method.equals(Methods.POW)) {
                r = pow((String) params.get(0), (String) params.get(1));
            } else if (method.equals(Methods.EXP)) {
                r = exp((String) params.get(0));
            } else if (method.equals(Methods.LOG)) {
                r = log((String) params.get(0));
            } else if (method.equals(Methods.ACOS)) {
                r = acos((String) params.get(0));
            } else if (method.equals(Methods.COS)) {
                r = cos((String) params.get(0));
            } else if (method.equals(Methods.ASIN)) {
                r = asin((String) params.get(0));
            } else if (method.equals(Methods.SIN)) {
                r = sin((String) params.get(0));
            } else if (method.equals(Methods.ATAN)) {
                r = atan((String) params.get(0));
            } else if (method.equals(Methods.TAN)) {
                r = tan((String) params.get(0));
            } else if (method.equals(Methods.RANDOM)) {
                r = random();
            }
        } catch (NumberFormatException e) {
            throw new UndefineVariableException();
        }

        List result = new ArrayList();
        result.add(Words.RESULT_ID);
        result.add(r);
        return result;
    }

    /**
     * @return
     */
    private String e() {
        return String.valueOf(Math.E);
    }

    /**
     * @return
     */
    private String pi() {
        return String.valueOf(Math.PI);
    }

    /**
     * @param string
     * @return
     */
    private String sqrt(String string) {
        return String.valueOf(Math.sqrt(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @param string2
     * @return
     */
    private String min(String string, String string2) {
        return String.valueOf(Math.min(Double.parseDouble(string), Double.parseDouble(string2)));
    }

    /**
     * @param string
     * @param string2
     * @return
     */
    private String max(String string, String string2) {
        return String.valueOf(Math.max(Double.parseDouble(string), Double.parseDouble(string2)));
    }

    /**
     * @param string
     * @return
     */
    private String abs(String string) {
        return String.valueOf(Math.abs(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @param string2
     * @return
     */
    private String pow(String string, String string2) {
        return String.valueOf(Math.pow(Double.parseDouble(string), Double.parseDouble(string2)));
    }

    /**
     * @param string
     * @return
     */
    private String exp(String string) {
        return String.valueOf(Math.exp(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @return
     */
    private String log(String string) {
        return String.valueOf(Math.log(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @return
     */
    private String acos(String string) {
        return String.valueOf(Math.acos(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @return
     */
    private String cos(String string) {
        return String.valueOf(Math.cos(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @return
     */
    private String asin(String string) {
        return String.valueOf(Math.asin(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @return
     */
    private String sin(String string) {
        return String.valueOf(Math.sin(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @return
     */
    private String atan(String string) {
        return String.valueOf(Math.atan(Double.parseDouble(string)));
    }

    /**
     * @param string
     * @return
     */
    private String tan(String string) {
        return String.valueOf(Math.tan(Double.parseDouble(string)));
    }

    /**
     * @return
     */
    private String random() {
        return String.valueOf(Math.random());
    }

    /**/
}