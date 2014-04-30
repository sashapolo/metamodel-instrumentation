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
public class ArrayAccessExpression extends Expression {
    private final Expression caller;
    private final Expression param;
    
    public ArrayAccessExpression(final Expression caller, final Expression param) {
        this.caller = caller;
        this.param = param;
    }
}
