/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class VariableReference extends Expression {
    private final String name;

    public VariableReference(final String name) {
        super(Type.UNKOWN_TYPE);
        this.name = name;
    }
}
