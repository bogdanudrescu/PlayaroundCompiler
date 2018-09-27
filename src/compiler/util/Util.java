/*
 * Created on Feb 15, 2005
 */
package compiler.util;

import java.util.ArrayList;
import java.util.List;

/**
 *  
 */
public class Util {

    /**
     * Make a copy of the <code>list</code>
     * 
     * @param list
     * @return
     */
    public static List copy(List list) {
        List list2 = new ArrayList();
        Object[] objects = list.toArray();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof List) {
                list2.add(copy((List) objects[i]));
            } else {
                list2.add(objects[i]);
            }
        }
        return list2;
    }
}