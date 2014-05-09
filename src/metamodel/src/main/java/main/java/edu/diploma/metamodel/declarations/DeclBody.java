/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.Collections;
import java.util.List;
import main.java.edu.diploma.metamodel.Annotation;
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
                     @ElementList(name = "decls") final List<Declaration> decls,
                     @ElementList(name = "modifiers") final List<String> modifiers,
                     @ElementList(name = "annotations") final List<Annotation> annotations) {
        super(name);
        throw new IllegalStateException("Calling a workaround constructor!");
    }
    public DeclBody(@ElementList(name = "decls") final List<Declaration> decls) {
        super("");
        this.decls = decls;
    }
    
    public void add(final Declaration decl) {
        decls.add(decl);
    }
    public void addAll(final List<? extends Declaration> decls) {
        this.decls.addAll(decls);
    }
}
