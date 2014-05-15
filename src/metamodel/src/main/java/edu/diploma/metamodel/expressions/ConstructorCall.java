/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.declarations.DeclBody;
import edu.diploma.metamodel.types.Type;
import edu.diploma.util.Stringifier;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class ConstructorCall extends Expression {
    @ElementList
    private final List<Expression> params;
    @ElementList
    private final List<Type> templates;
    @Element(required = false)
    private final DeclBody body;
    @Element
    private final boolean heap;
    
    public ConstructorCall(final Type type, final List<Expression> params, boolean heap) {
        super(type);
        this.params = params;
        this.templates = Collections.emptyList();
        this.body = null;
        this.heap = heap;
    }
    public ConstructorCall(final Type type, final List<Expression> params, 
            final List<Type> templates, boolean heap) {
        super(type);
        this.params = params;
        this.templates = templates;
        this.heap = heap;
        this.body = null;
    }
    public ConstructorCall(@Element(name = "type") final Type type, 
                           @ElementList(name = "params") final List<Expression> params, 
                           @ElementList(name = "templates") final List<Type> templates, 
                           @Element(name = "body") final DeclBody body,
                           @Element(name = "heap") boolean heap) {
        super(type);
        this.params = params;
        this.templates = templates;
        this.body = body;
        this.heap = heap;
    }

    public List<Expression> getParams() {
        return Collections.unmodifiableList(params);
    }

    public List<Type> getTemplates() {
        return Collections.unmodifiableList(templates);
    }

    public DeclBody getBody() {
        return body;
    }

    public boolean isHeap() {
        return heap;
    }
    
    @Override
    public void accept(Visitor visitor) {
        for (final Expression expr : params) {
            visitor.dispatch(expr);
        }
        for (final Type t : templates) {
            visitor.dispatch(t);
        }
        if (body != null) {
            visitor.dispatch(body);
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        if (heap) {
            result.append("new ");
        }
        result.append(type.toString());
        if (!templates.isEmpty()) {
            result.append('<').append(Stringifier.toString(templates)).append('>');
        }
        result.append('(').append(Stringifier.toString(params)).append(')');
        return result.toString();
    }
    
    
}
