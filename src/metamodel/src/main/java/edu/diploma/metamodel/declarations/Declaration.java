/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.Entity;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public abstract class Declaration implements Entity {
    @Element(required = false)
    protected final String name;
    @Element
    protected Visibility visibility;
    @ElementList(required = false)
    protected final List<String> modifiers;
    @ElementList(required = false)
    protected final List<Annotation> annotations;
    
    public Declaration(final String name) {
        this.name = name;
        this.visibility = Visibility.DEFAULT;
        this.modifiers = new LinkedList<>();
        this.annotations = new LinkedList<>();
    }
    public Declaration(@Element(name = "name") final String name, 
                       @Element(name = "visibility") final Visibility visibility,
                       @ElementList(name = "modifiers") final List<String> modifiers, 
                       @ElementList(name = "annotations") final List<Annotation> annotations) {
        this.name = name;
        this.visibility = visibility;
        this.modifiers = modifiers;
        this.annotations = annotations;
    }
    
    public void addAnnotations(final List<Annotation> annotations) {
        for (final Annotation anno : annotations) {
            addAnnotation(anno);
        }
    }
    public void addAnnotation(final Annotation annotation) {
        this.annotations.add(annotation);
    }
    public void addModifiers(final List<String> modifiers) {
        for (final String mod : modifiers) {
            addModifier(mod);
        }
    }
    public void addModifier(final String modifier) {
        this.modifiers.add(modifier);
    }
    public void setVisibility(final Visibility visibility) {
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }
    public Visibility getVisibility() {
        return visibility;
    }
    public List<String> getModifiers() {
        return Collections.unmodifiableList(modifiers);
    }
    public List<Annotation> getAnnotations() {
        return Collections.unmodifiableList(annotations);
    }
    
    public static enum Visibility {
        PUBLIC, PRIVATE, PROTECTED, DEFAULT;

        @Override
        public String toString() {
            switch (this) {
                case DEFAULT: return "";
                case PRIVATE: return "private";
                case PROTECTED: return "protected";
                case PUBLIC: return "public";
            }
            throw new AssertionError("unreachable");
        }
    }
}
