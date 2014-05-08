/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

/**
 *
 * @author alexander
 */
public class ParameterDecl extends Declaration {
    private final VariableDecl value;
    private final boolean variadic;

    public boolean isVariadic() {
        return variadic;
    }
    
    public ParameterDecl(final VariableDecl value, boolean variadic) {
        super(value.getName());
        this.value = value;
        this.variadic = variadic;
    }
}
