/*
 * Created on Dec 6, 2004
 */
package compiler.performer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import compiler.exception.UndefineVariableException;
import compiler.io.IO;

/**
 * Perform the computes for the lists
 */
public class Launcher {

    private List list;

    /**
     * Create a launcher
     * 
     * @param list
     */
    public Launcher(List list) {
        this.list = list;
    }

    /**
     * Create a launcher
     * 
     * @param list
     */
    public Launcher(String text) {
        list = getList(text);
    }

    /**
     * Perform the tasks
     * 
     * @param list
     */
    public boolean perform(String text) {
        Performer performer;
        List result = null;

        if (!((String) ((List) list.get(0)).get(1)).equals(text)) {
            return false;
        }

        for (int i = 1; i < list.size(); i++) {
            try {
                performer = PerformerFactory.createPerformer((List) list.get(i));
                if (performer != null) {
                    result = performer.perform();
                    if (result != null) {
                        Variables.put((String) result.get(0), (String) result.get(1));
                    }
                }
            } catch (UndefineVariableException e) {
                IO.write(e.getMessage() + IO.NEW_LINE);
                System.out.println(e);
                return true;
            }
        }
        return true;
    }

    /**
     * 
     * @param text
     * @return
     */
    private List getList(String text) {
        List list = new ArrayList();

        int index = 1;
        int begin;
        int end;

        StringTokenizer tokenizer;
        List item;
        while (index > 0) {
            begin = text.indexOf("[", index);
            end = text.indexOf("]", index);

            if (begin < 0 || end < 0) {
                break;
            }

            item = new ArrayList();
            tokenizer = new StringTokenizer(text.substring(begin + 1, end), ",");
            while (tokenizer.hasMoreElements()) {
                item.add(tokenizer.nextElement());
            }

            list.add(item);

            index = end + 1;
        }

        return list;
    }

    /**/
}