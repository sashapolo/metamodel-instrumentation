/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
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
    public UnaryExpression(@Element(name = "type") final Type type, 
                           @Element(name = "operand") final Expression operand, 
                           @Element(name = "operation") final String operation, 
                           @Element(name = "postfix") boolean postfix) {
        super(type);
        this.operand = operand;
        this.operation = operation;
        this.postfix = postfix;
    }

    public Expression getOperand() {
        return operand;
    }

    public String getOperation() {
        return operation;
    }

    public boolean isPostfix() {
        return postfix;
    }
}
