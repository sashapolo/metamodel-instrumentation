/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

/**
 *
 * @author alexander
 */
public class AttributeAccess extends Expression {
    private final Expression expr;
    private final String name;
    
    public AttributeAccess(final Expression expr, final String name) {
        this.expr = expr;
        this.name = name;
    }
}