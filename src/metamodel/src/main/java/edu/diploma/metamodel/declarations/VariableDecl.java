/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.metamodel.types.Type;
import edu.diploma.util.Stringifier;
import edu.diploma.visitors.Visitor;
import java.util.List;
import java.util.Objects;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class VariableDecl extends Declaration {
    @Element
    private final Type type;
    @Element(required = false)
    private final Expression value;
    
    private VariableDecl(@Element(name = "name") String name, 
                         @Element(name = "visibility") final Visibility visibility,
                         @ElementList(name = "modifiers") final List<String> modifiers,
                         @ElementList(name = "annotations") final List<Annotation> annotations,
                         @Element(name = "type") final Type type, 
                         @Element(name = "value") final Expression value) {
        super(name, visibility, modifiers, annotations);
        this.type = type;
        this.value = value;
    }
    
    public VariableDecl(final Type type, final String name, final Expression value) {
        super(name);
        this.type = type;
        this.value = value;
    }
    public VariableDecl(final Type type, final String name) {
        super(name);
        this.type = type;
        this.value = null;
    }

    public Type getType() {
        return type;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : annotations) {
            visitor.dispatch(anno);
        }
        visitor.dispatch(type);
        if (value != null) {
            visitor.dispatch(value);
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        
        if (!visibility.toString().isEmpty()) {
            result.append(visibility.toString()).append(" ");
        }
        if (!modifiers.isEmpty()) {
            result.append(Stringifier.toString(modifiers, " ")).append(' ');
        }
        result.append(type.toString()).append(' ').append(name);
        
        if (value != null) {
            result.append(" = ").append(value.toString());
        }
        
        return result.toString();
    }
}
