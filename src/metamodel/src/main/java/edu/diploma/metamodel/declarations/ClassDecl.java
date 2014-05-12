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
public class ClassDecl extends Declaration {
    private final List<TemplateDecl> templates;
    private final List<Type> inherits;
    private final DeclBody body;
    private boolean isAbstract;
    
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
    
    @Override
    public void addModifiers(final List<String> modifiers) {
        for (final String mod : modifiers) {
            addModifier(mod);
        }
    }
    @Override
    public void addModifier(String modifier) {
        if ("abstract".equals(modifier)) {
            isAbstract = true;
        }
        super.addModifier(modifier);
    }

    public List<TemplateDecl> getTemplates() {
        return templates;
    }
    public List<Type> getInherits() {
        return inherits;
    }
    public DeclBody getBody() {
        return body;
    }
    public boolean isAbstract() {
        return isAbstract;
    }
}
