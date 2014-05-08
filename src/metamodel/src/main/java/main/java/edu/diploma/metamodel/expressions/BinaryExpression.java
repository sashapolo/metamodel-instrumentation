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
public class BinaryExpression extends Expression {
    private final Expression lhs;
    private final Expression rhs;
    private final String operation;
    
    public BinaryExpression(final Expression lhs, final Expression rhs, final String operation) {
        super(Type.UNKOWN_TYPE);
        this.lhs = lhs;
        this.rhs = rhs;
        this.operation = operation;
    }
}
