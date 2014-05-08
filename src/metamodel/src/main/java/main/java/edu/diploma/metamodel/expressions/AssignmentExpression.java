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
public class AssignmentExpression extends Expression {
    private final Expression lhs;
    private final Expression rhs;
    
    public AssignmentExpression(final Expression lhs, final Expression rhs) {
        super(Type.UNKOWN_TYPE);
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
