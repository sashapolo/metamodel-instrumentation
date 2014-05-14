/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.statements.StatementBlock;
import edu.diploma.metamodel.types.Type;
import edu.diploma.util.Stringifier;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class FunctionDecl extends Declaration {
    @Element(required = false)
    private final Type retType;
    @ElementList
    private final List<String> exceptions;
    @ElementList
    private final List<ParameterDecl> params;
    @ElementList
    private final List<TemplateDecl> templates;
    @Element
    private final StatementBlock body;

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : getAnnotations()) {
            visitor.dispatch(anno);
        }
        visitor.dispatch(retType);
        for (final ParameterDecl param : params) {
            visitor.dispatch(param);
        }
        for (final TemplateDecl templ : templates) {
            visitor.dispatch(templ);
        }
        visitor.dispatch(body);
    }

    public static class Builder {
        private final String name;
        private final Type retType;
        private StatementBlock block = StatementBlock.EMPTY_BLOCK;
        private List<ParameterDecl> params = Collections.emptyList();
        private List<String> exceptions = Collections.emptyList();
        private List<TemplateDecl> templates = Collections.emptyList();

        public Builder(final Type retType, final String name) {
            this.retType = retType;
            this.name = name;
        }

        public Builder params(final List<ParameterDecl> params) {
            this.params = params;
            return this;
        }
        public Builder body(final StatementBlock body) {
            this.block = body;
            return this;
        }
        public Builder exceptions(final List<String> exceptions) {
            this.exceptions = exceptions;
            return this;
        }
        public Builder templates(final List<TemplateDecl> templates) {
            this.templates = templates;
            return this;
        }

        public FunctionDecl build() {
            return new FunctionDecl(retType, name, params, exceptions, templates, block);
        }
    }

    private FunctionDecl(final Type retType, final String name, final List<ParameterDecl> params,
                         final List<String> exceptions, final List<TemplateDecl> templates,
                         final StatementBlock body) {
        super(name);
        this.retType = retType;
        this.params = params;
        this.exceptions = exceptions;
        this.templates = templates;
        this.body = body;
    }
    private FunctionDecl(@Element(name = "retType") final Type retType, 
                         @Element(name = "name") final String name,
                         @ElementList(name = "params") final List<ParameterDecl> params,
                         @ElementList(name = "exceptions") final List<String> exceptions,
                         @ElementList(name = "templates") final List<TemplateDecl> templates,
                         @Element(name = "body") final StatementBlock body,
                         @ElementList(name = "modifiers") final List<String> modifiers, 
                         @ElementList(name = "annotations") final List<Annotation> annotations) {
        super(name, modifiers, annotations);
        this.retType = retType;
        this.params = params;
        this.exceptions = exceptions;
        this.templates = templates;
        this.body = body;
    }


    public Type getRetType() {
        return retType;
    }
    public List<String> getExceptions() {
        return Collections.unmodifiableList(exceptions);
    }
    public List<ParameterDecl> getParams() {
        return Collections.unmodifiableList(params);
    }
    public List<TemplateDecl> getTemplates() {
        return Collections.unmodifiableList(templates);
    }
    public StatementBlock getBody() {
        return body;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        
        if (!templates.isEmpty()) {
            result.append('<').append(Stringifier.toString(templates)).append(">\n");
        }
        
        result.append(Stringifier.toString(modifiers, " ")).append(' ');
        if (retType != null) {
            result.append(retType.toString()).append(' ');
        }
        result.append(name).append('(').append(Stringifier.toString(params)).append(')');
        
        return result.toString();
    }
}
