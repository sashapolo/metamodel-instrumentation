/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import java.util.List;
import edu.diploma.metamodel.Annotation;
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
    
    private AnnotationDecl(@Element(name = "name") String name, 
                           @ElementList(name = "modifiers") final List<String> modifiers,
                           @ElementList(name = "annotations") final List<Annotation> annotations,
                           @Element(name = "body") final DeclBody body) {
        super(name, modifiers, annotations);
        this.body = body;
    }
    
    public AnnotationDecl(final String name, final DeclBody body) {
        super(name);
        this.body = body;
    }

    public DeclBody getBody() {
        return body;
    }
}
