/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.parser;

import main.java.edu.diploma.metamodel.expressions.Expression;

/**
 *
 * @author alexander
 */
public class ArrayParam {
    private final Expression size;
    
    public ArrayParam(final Expression size) {
        this.size = size;
    }
    
    public Expression getSize() {
        return size;
    }
}
