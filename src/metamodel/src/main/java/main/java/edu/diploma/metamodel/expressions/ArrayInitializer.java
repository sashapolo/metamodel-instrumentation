/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import java.util.LinkedList;
import java.util.List;
import main.java.edu.diploma.metamodel.Entity;
import main.java.edu.diploma.metamodel.types.Type;
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
        return values;
    }
    
}
