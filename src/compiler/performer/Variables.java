/*
 * Created on Dec 6, 2004
 */
package compiler.performer;

import java.util.HashMap;

/**
 * A wraper for a <code>HashMap</code> who keeps the variables
 */
public class Variables {

    /**
     * The variable's map
     */
    private static HashMap map = new HashMap();

    /**
     * Puts the value
     * 
     * @param name
     * @param value
     */
    public static void put(String name, String value) {
        map.put(name, value);
    }

    /**
     * Gets the value of the variable
     * 
     * @param name
     * @return
     */
    public static String get(String name) {
        return (String) map.get(name);
    }

    /**
     *  
     */
    public static void clear() {
        map.clear();
    }

    /**
     *  
     */
    public static void out() {
        System.out.println(map);
    }

    /**/
}