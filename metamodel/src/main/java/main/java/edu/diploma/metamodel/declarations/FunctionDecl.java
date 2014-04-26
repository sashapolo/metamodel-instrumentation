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
public class FunctionDecl extends Declaration {
    private final Type retType;
    private final List<Type> exceptions;
    private final List<VariableDecl> params;
    
    public FunctionDecl(final Type retType, final String name, final List<VariableDecl> params) {
        super(name);
        this.retType = retType;
        this.params = params;
    }
}
