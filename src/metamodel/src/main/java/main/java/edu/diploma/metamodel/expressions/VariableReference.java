/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class VariableReference extends Expression {
    private final String name;

    public VariableReference(final String name) {
        super(Type.UNKOWN_TYPE);
        this.name = name;
    }
    public VariableReference(@Element(name = "type") final Type type, 
                             @Element(name = "name") final String name) {
        super(type);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
}
