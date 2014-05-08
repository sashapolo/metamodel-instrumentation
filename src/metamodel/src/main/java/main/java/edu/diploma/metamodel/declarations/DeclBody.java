/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default(DefaultType.FIELD)
public class DeclBody extends Declaration {
    public static final DeclBody EMPTY = new DeclBody(Collections.<Declaration>emptyList());
    
    private final List<Declaration> decls;
    
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
