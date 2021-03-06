/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
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
public class AnnotationDecl extends Declaration {
    private final DeclBody body;
    
    private AnnotationDecl(@Element(name = "name") final String name, 
                           @Element(name = "visibility") final Visibility visibility,
                           @ElementList(name = "modifiers") final List<String> modifiers,
                           @ElementList(name = "annotations") final List<Annotation> annotations,
                           @Element(name = "body") final DeclBody body) {
        super(name, visibility, modifiers, annotations);
        this.body = body;
    }
    
    public AnnotationDecl(final String name, final DeclBody body) {
        super(name);
        this.body = body;
    }

    public DeclBody getBody() {
        return body;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : getAnnotations()) {
            visitor.dispatch(anno);
        }
        visitor.dispatch(body);
    }
}
