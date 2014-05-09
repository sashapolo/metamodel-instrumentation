/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class AnnotationDecl extends Declaration {
    private final DeclBody body;
    
    public AnnotationDecl(final String name, final DeclBody body) {
        super(name);
        this.body = body;
    }
}
