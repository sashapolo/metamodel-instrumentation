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
public class TypeExpression extends Expression {
    public TypeExpression(@Element(name = "type") final Type type) {
        super(type);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(getType());
    }
}
