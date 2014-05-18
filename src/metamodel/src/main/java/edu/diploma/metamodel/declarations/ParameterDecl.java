/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.util.Stringifier;
import edu.diploma.visitors.Visitor;
import java.util.List;
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
                          @Element(name = "visibility") final Visibility visibility,
                          @ElementList(name = "modifiers") final List<String> modifiers,
                          @ElementList(name = "annotations") final List<Annotation> annotations,
                          @Element(name = "value") final VariableDecl value,
                          @Element(name = "variadic") boolean variadic) {
        super(name, visibility, modifiers, annotations);
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

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : getAnnotations()) {
            visitor.dispatch(anno);
        }
        visitor.dispatch(value);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder(Stringifier.toString(modifiers, " "));
        if (!modifiers.isEmpty()) {
            result.append(' ');
        }
        result.append(value.toString());
        if (variadic) {
            result.append("...");
        }
        return result.toString();
    }
}
