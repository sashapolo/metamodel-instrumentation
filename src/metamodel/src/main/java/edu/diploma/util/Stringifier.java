/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.util;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author alexander
 */
public class Stringifier {
    public static <T> String toString(final List<T> list, final String delimeter) {
        final StringBuilder result = new StringBuilder();
        final Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            result.append(it.next().toString());
            if (it.hasNext()) {
                result.append(delimeter);
            }
        }
        return result.toString();
    }
    public static <T> String toString(final List<T> list) {
        return toString(list, ", ");
    }

    private Stringifier() {
    }
}
