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
public class UnaryExpression extends Expression {
    private final Expression operand;
    private final String operation;
    private final boolean postfix;
    
    public UnaryExpression(final Expression operand, final String operation) {
        super(Type.UNKOWN_TYPE);
        this.operand = operand;
        this.operation = operation;
        this.postfix = false;
    }
    public UnaryExpression(final Expression operand, final String operation, boolean postfix) {
        super(Type.UNKOWN_TYPE);
        this.operand = operand;
        this.operation = operation;
        this.postfix = postfix;
    }
}
