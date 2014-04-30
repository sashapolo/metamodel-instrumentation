/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import main.java.edu.diploma.metamodel.expressions.Expression;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class VariableDecl extends Declaration {
    private final Type type;
    private final Expression value;
    
    public VariableDecl(final Type type, final String name, final Expression value) {
        super(name);
        this.type = type;
        this.value = value;
    }
    public VariableDecl(final Type type, final String name) {
        super(name);
        this.type = type;
        this.value = null;
    }
}
