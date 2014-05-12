/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.declarations.DeclBody;
import edu.diploma.metamodel.types.Type;
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
    
    public ConstructorCall(final Type type, final List<Expression> params) {
        super(type);
        this.params = params;
        this.templates = Collections.emptyList();
        this.body = null;
    }
    public ConstructorCall(final Type type, final List<Expression> params, final List<Type> templates) {
        super(type);
        this.params = params;
        this.templates = templates;
        this.body = null;
    }
    public ConstructorCall(@Element(name = "type") final Type type, 
                           @ElementList(name = "params") final List<Expression> params, 
                           @ElementList(name = "templates") final List<Type> templates, 
                           @Element(name = "body") final DeclBody body) {
        super(type);
        this.params = params;
        this.templates = templates;
        this.body = body;
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

    @Override
    public void accept(Visitor visitor) {
        for (final Expression expr : params) {
            visitor.visit(expr);
        }
        for (final Type type : templates) {
            visitor.visit(type);
        }
        if (body != null) {
            visitor.visit(body);
        }
    }
    
}
