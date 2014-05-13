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
public class AttributeAccess extends Expression {
    private final Expression expr;
    private final String name;
    
    private AttributeAccess(@Element(name = "type") final Type type,
                            @Element(name = "expr") final Expression expr, 
                            @Element(name = "name") final String name) {
        super(type);
        this.expr = expr;
        this.name = name;
    }
    
    public AttributeAccess(final Expression expr, final String name) {
        super(Type.UNKOWN_TYPE);
        assert expr != null;
        this.expr = expr;
        this.name = name;
    }

    public Expression getExpr() {
        return expr;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(expr);
    }
}
