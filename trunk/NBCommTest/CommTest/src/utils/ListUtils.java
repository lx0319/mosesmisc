/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class ListUtils {
    public static String[] List2StringArray(List inlist){
        String[] sa = new String[inlist.size()];
        sa = (String[]) inlist.toArray(sa);
        return sa;
    }
}
