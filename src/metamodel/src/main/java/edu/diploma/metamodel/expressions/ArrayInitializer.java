/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.Entity;
import edu.diploma.metamodel.types.Type;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class ArrayInitializer extends Expression {
    private final List<Entity> values = new LinkedList<>();
    
    public ArrayInitializer(@Element(name = "type") final Type type, 
                            @ElementList(name = "values") final List<? extends Entity> values) {
        super(type);
        this.values.addAll(values);
    }
    public ArrayInitializer(final List<? extends Entity> values) {
        super(Type.UNKOWN_TYPE);
        this.values.addAll(values);
    }

    public List<Entity> getValues() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Entity val : values) {
            visitor.dispatch(val);
        }
    }
    
}
