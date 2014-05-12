/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class Declaration {
    @Element(required = false)
    private final String name;
    @ElementList(required = false)
    private final List<String> modifiers;
    @ElementList(required = false)
    private final List<Annotation> annotations;
    
    public Declaration(final String name) {
        this.name = name;
        this.modifiers = new LinkedList<>();
        this.annotations = new LinkedList<>();
    }
    public Declaration(@Element(name = "name") final String name, 
                       @ElementList(name = "modifiers") final List<String> modifiers, 
                       @ElementList(name = "annotations") final List<Annotation> annotations) {
        this.name = name;
        this.modifiers = modifiers;
        this.annotations = annotations;
    }
    
    public void addAnnotations(final List<Annotation> annotations) {
        this.annotations.addAll(annotations);
    }
    public void addAnnotation(final Annotation annotation) {
        this.annotations.add(annotation);
    }
    public void addModifiers(final List<String> modifiers) {
        this.modifiers.addAll(modifiers);
    }
    public void addModifier(final String modifier) {
        this.modifiers.add(modifier);
    }

    public String getName() {
        return name;
    }
    public List<String> getModifiers() {
        return Collections.unmodifiableList(modifiers);
    }
    public List<Annotation> getAnnotations() {
        return Collections.unmodifiableList(annotations);
    }
    
}
