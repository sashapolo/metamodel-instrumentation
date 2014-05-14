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
public class ArrayConstructorCall extends Expression {
    private final ExpressionList size;
    private final boolean heap;
    
    public ArrayConstructorCall(@Element(name = "type") final Type type, 
                                @Element(name = "size") final ExpressionList size,
                                @Element(name = "heap") boolean heap) {
        super(type);
        this.size = size;
        this.heap = heap;
    }

    public ExpressionList getSize() {
        return size;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(size);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        if (heap) {
            result.append("new ");
        }
        result.append(type.toString());
        for (final Expression expr : size.asList()) {
            result.append("[").append(expr == null ? "" : expr.toString()).append("]");
        }
        return result.toString();
    }
    
    
}
