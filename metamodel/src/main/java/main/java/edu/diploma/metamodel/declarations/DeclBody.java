/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class DeclBody extends Declaration {
    public static final DeclBody EMPTY = new DeclBody(Collections.<Declaration>emptyList());
    
    private final List<Declaration> decls;
    
    public DeclBody() {
        super("");
        this.decls = new LinkedList<>();
    }
    public DeclBody(final List<Declaration> decls) {
        super("");
        this.decls = decls;
    }
    
    public void addDeclaration(final Declaration decl) {
        decls.add(decl);
    }
    public void addDeclarations(final List<Declaration> decls) {
        this.decls.addAll(decls);
    }
}
