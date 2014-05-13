/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.types.Type;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class BinaryExpression extends Expression {
    private final Expression lhs;
    private final Expression rhs;
    private final String operation;
    
    public BinaryExpression(@Element(name = "type") final Type type, 
                            @Element(name = "lhs") final Expression lhs, 
                            @Element(name = "rhs") final Expression rhs, 
                            @Element(name = "operation") final String operation) {
        super(type);
        this.lhs = lhs;
        this.rhs = rhs;
        this.operation = operation;
    }
    public BinaryExpression(final Expression lhs, final Expression rhs, final String operation) {
        super(Type.UNKOWN_TYPE);
        this.lhs = lhs;
        this.rhs = rhs;
        this.operation = operation;
    }

    public Expression getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }

    public String getOperation() {
        return operation;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(lhs);
        visitor.dispatch(rhs);
    }
    
}
