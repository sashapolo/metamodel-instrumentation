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
public class ArrayConstructorCall extends Expression {
    private final Expression inner;
    private final Expression size;
    
    public ArrayConstructorCall(final Expression inner, final Expression size) {
        this.inner = inner;
        this.size = size;
    }
}
