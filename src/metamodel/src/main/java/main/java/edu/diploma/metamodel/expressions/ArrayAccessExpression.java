/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class ArrayAccessExpression extends Expression {
    private final Expression caller;
    private final Expression param;
    
    public ArrayAccessExpression(final Expression caller, final Expression param) {
        super(Type.UNKOWN_TYPE);
        this.caller = caller;
        this.param = param;
    }
}
