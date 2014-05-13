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
public class AssignmentExpression extends Expression {
    private final Expression lhs;
    private final Expression rhs;
    
    public AssignmentExpression(@Element(name = "type") final Type type, 
                                @Element(name = "lhs") final Expression lhs, 
                                @Element(name = "rhs") final Expression rhs) {
        super(type);
        this.lhs = lhs;
        this.rhs = rhs;
    }
    public AssignmentExpression(final Expression lhs, final Expression rhs) {
        super(Type.UNKOWN_TYPE);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Expression getLhs() {
        return lhs;
    }

    public Expression getRhs() {
        return rhs;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(lhs);
        visitor.dispatch(rhs);
    }
    
    
}
