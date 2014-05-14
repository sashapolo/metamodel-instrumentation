/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
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
@Default
public class ClassDecl extends Declaration {
    private final List<TemplateDecl> templates;
    private final List<Type> inherits;
    private final DeclBody body;
    
    private ClassDecl(@Element(name = "name") String name, 
                      @ElementList(name = "modifiers") final List<String> modifiers,
                      @ElementList(name = "annotations") final List<Annotation> annotations,
                      @ElementList(name = "templates") final List<TemplateDecl> templates, 
                      @ElementList(name = "inherits") final List<Type> inherits,
                      @Element(name = "body") final DeclBody body) {
        super(name, modifiers, annotations);
        this.templates = templates;
        this.inherits = inherits;
        this.body = body;
    }
    
    public ClassDecl(final String name, final List<TemplateDecl> templates, 
            final List<Type> inherits, final DeclBody body) {
        super(name);
        this.templates = templates;
        this.inherits = inherits;
        this.body = body;
    }

    public List<TemplateDecl> getTemplates() {
        return Collections.unmodifiableList(templates);
    }
    public List<Type> getInherits() {
        return Collections.unmodifiableList(inherits);
    }
    public DeclBody getBody() {
        return body;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : getAnnotations()) {
            visitor.dispatch(anno);
        }
        for (final TemplateDecl templ : templates) {
            visitor.dispatch(templ);
        }
        for (final Type inh : inherits) {
            visitor.dispatch(inh);
        }
        visitor.dispatch(body);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        
        result.append(Stringifier.toString(modifiers, " ")).append(' ');
        result.append("class ").append(name);
        
        if (!templates.isEmpty()) {
            result.append('<').append(Stringifier.toString(templates)).append('>');
        }
        if (!inherits.isEmpty()) {
            result.append(" extends ").append(Stringifier.toString(inherits));
        }
        return result.toString();
    }
    
    
}
