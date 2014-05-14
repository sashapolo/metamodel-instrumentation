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
    private final Expression inner;
    private final Expression size;
    private final boolean heap;
    
    public ArrayConstructorCall(@Element(name = "type") final Type type, 
                                @Element(name = "inner") final Expression inner, 
                                @Element(name = "size") final Expression size,
                                @Element(name = "heap") boolean heap) {
        super(type);
        this.inner = inner;
        this.size = size;
        this.heap = heap;
    }
    public ArrayConstructorCall(final Expression inner, final Expression size, boolean heap) {
        super(Type.UNKOWN_TYPE);
        this.inner = inner;
        this.size = size;
        this.heap = heap;
    }

    public Expression getInner() {
        return inner;
    }

    public Expression getSize() {
        return size;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(inner);
        visitor.dispatch(size);
    }
    
    
}
