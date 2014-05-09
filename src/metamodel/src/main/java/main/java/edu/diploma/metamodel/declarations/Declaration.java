/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import main.java.edu.diploma.metamodel.Annotation;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class Declaration {
    @Element
    private final String name;
    @ElementList(required = false)
    private final List<String> modifiers = new LinkedList<>();
    @ElementList(required = false)
    private final List<Annotation> annotations = new LinkedList<>();
    
    public Declaration(final String name) {
        this.name = name;
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
