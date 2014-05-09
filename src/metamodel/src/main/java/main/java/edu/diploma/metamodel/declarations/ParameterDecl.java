/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.List;
import main.java.edu.diploma.metamodel.Annotation;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class ParameterDecl extends Declaration {
    private final VariableDecl value;
    private final boolean variadic;

    private ParameterDecl(@Element(name = "name") String name, 
                          @ElementList(name = "modifiers") final List<String> modifiers,
                          @ElementList(name = "annotations") final List<Annotation> annotations,
                          @Element(name = "value") final VariableDecl value,
                          @Element(name = "variadic") boolean variadic) {
        super(name, modifiers, annotations);
        this.value = value;
        this.variadic = variadic;
    }
    
    public ParameterDecl(final VariableDecl value, boolean variadic) {
        super(value.getName());
        this.value = value;
        this.variadic = variadic;
    }
    
    public boolean isVariadic() {
        return variadic;
    }

    public VariableDecl getValue() {
        return value;
    }
    
}
