/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.types.Type;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class TemplateDecl extends Declaration {
    private final List<Type> bounds;
    
    private TemplateDecl(@Element(name = "name") String name, 
                         @ElementList(name = "modifiers") final List<String> modifiers,
                         @ElementList(name = "annotations") final List<Annotation> annotations,
                         @ElementList(name = "bounds") final List<Type> bounds) {
        super(name, modifiers, annotations);
        this.bounds = bounds;
    }
    
    public TemplateDecl(final String name, final List<Type> bounds) {
        super(name);
        this.bounds = bounds;
    }

    public List<Type> getBounds() {
        return bounds;
    }
    
}
