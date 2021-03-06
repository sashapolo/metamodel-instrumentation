/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class DeclBody extends Declaration {
    public static final DeclBody EMPTY = new DeclBody(Collections.<Declaration>emptyList());
    
    @ElementList
    private final List<Declaration> decls;
    
    private DeclBody(@Element(name = "name") String name, 
                     @Element(name = "visibility") final Visibility visibility,
                     @ElementList(name = "decls") final List<Declaration> decls,
                     @ElementList(name = "modifiers") final List<String> modifiers,
                     @ElementList(name = "annotations") final List<Annotation> annotations) {
        super(name, visibility, modifiers, annotations);
        this.decls = decls;
    }
    public DeclBody() {
        super("");
        this.decls = new LinkedList<>();
    }
    public DeclBody(final List<Declaration> decls) {
        super("");
        this.decls = decls;
    }
    
    public void add(final Declaration decl) {
        decls.add(decl);
    }
    public void addAll(final List<? extends Declaration> decls) {
        this.decls.addAll(decls);
    }

    @Override
    public void addModifier(String modifier) {
        for (final Declaration decl : decls) {
            decl.addModifier(modifier);
        }
    }

    @Override
    public void addAnnotation(Annotation annotation) {
        for (final Declaration decl : decls) {
            decl.addAnnotation(annotation);
        }
    }

    public List<Declaration> getDecls() {
        return Collections.unmodifiableList(decls);
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : getAnnotations()) {
            visitor.dispatch(anno);
        }
        for (final Declaration decl : decls) {
            visitor.dispatch(decl);
        }
    }
}
