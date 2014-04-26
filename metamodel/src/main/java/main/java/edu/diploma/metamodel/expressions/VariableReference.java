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
public class VariableReference extends Expression {
    private final String name;

    public VariableReference(final String name) {
        this.name = name;
    }
}
