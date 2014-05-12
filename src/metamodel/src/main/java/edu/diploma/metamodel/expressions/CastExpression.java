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
public class CastExpression extends Expression {
    private final Expression param;
    
    public CastExpression(@Element(name = "type") final Type type, 
                          @Element(name = "param") final Expression param) {
        super(type);
        this.param = param;
    }

    public Expression getParam() {
        return param;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(param);
    }
}
