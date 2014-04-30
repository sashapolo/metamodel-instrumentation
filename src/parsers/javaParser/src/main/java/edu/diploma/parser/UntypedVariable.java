/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.parser;

import main.java.edu.diploma.metamodel.declarations.VariableDecl;
import main.java.edu.diploma.metamodel.expressions.Expression;
import main.java.edu.diploma.metamodel.types.ArrayType;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class UntypedVariable {
    private final String name;
    private Expression value;
    private final UntypedVariable next;
    
    public UntypedVariable(final String name) {
        this.name = name;
        this.next = null;
        this.value = null;
    }
    public UntypedVariable(final String name, final Expression value) {
        this.name = name;
        this.value = value;
        this.next = null;
    }
    public UntypedVariable(final UntypedVariable next) {
        this.name = null;
        this.value = null;
        this.next = next;
    }
    
    public void setValue(final Expression value) {
        this.value = value;
    }
    
    public VariableDecl createVariableDecl(final Type type) {
        if (next != null) {
            return createVariableDecl(new ArrayType(type));
        }
        return new VariableDecl(type, name, value);
    }
}
