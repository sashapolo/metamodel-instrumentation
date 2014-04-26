/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import java.util.List;

/**
 *
 * @author alexander
 */
public class ArrayInitializer extends Expression {
    private final List<Expression> values;
    
    public ArrayInitializer(final List<Expression> values) {
        this.values = values;
    }
}
