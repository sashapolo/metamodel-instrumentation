/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.List;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class ClassDecl extends Declaration {
    private final List<TemplateDecl> templates;
    private final List<Type> inherits;
    private final DeclBody body;
    private boolean isAbstract;
    
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
            if ("abstract".equals(mod)) {
                isAbstract = true;
            }
        }
        super.addModifiers(modifiers);
    }
}
