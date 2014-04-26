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
    private final boolean variadic;

    public boolean isVariadic() {
        return variadic;
    }
    
    public static class Builder {
        private final Type type;
        private final String name;
        
        private Expression value = null;
        private boolean variadic = false;
        
        public Builder(final Type type, final String name) {
            this.type = type;
            this.name = name;
        }
        
        public Builder value(final Expression value) {
            this.value = value;
            return this;
        }
        public Builder variadic() {
            this.variadic = true;
            return this;
        }
        
        public VariableDecl build() {
            return new VariableDecl(type, name, value, variadic);
        }
    }
    
    private VariableDecl(final Type type, final String name, final Expression value, final boolean isVariadic) {
        super(name);
        this.type = type;
        this.value = value;
        this.variadic = isVariadic;
    }
}
