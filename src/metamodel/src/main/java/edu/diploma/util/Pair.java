/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.util;

/**
 *
 * @author alexander
 * @param <T1>
 * @param <T2>
 */
public class Pair<T1, T2> {
    public T1 first;
    public T2 second;
    
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}
