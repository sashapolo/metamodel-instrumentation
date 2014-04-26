/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class StaticAttributeAccess extends Expression {
    private final Type qualifier;
    private final VariableReference attribute;
    
    public StaticAttributeAccess(final Type qualifier, final VariableReference attribute) {
        this.qualifier = qualifier;
        this.attribute = attribute;
    }
}