/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class ArrayAccessExpression extends Expression {
    private final Expression caller;
    private final Expression param;
    
    public ArrayAccessExpression(@Element(name = "type") final Type type, 
                                 @Element(name = "caller") final Expression caller, 
                                 @Element(name = "param") final Expression param) {
        super(type);
        this.caller = caller;
        this.param = param;
    }
    public ArrayAccessExpression(final Expression caller, final Expression param) {
        super(Type.UNKOWN_TYPE);
        this.caller = caller;
        this.param = param;
    }

    public Expression getCaller() {
        return caller;
    }

    public Expression getParam() {
        return param;
    }
    
    
}
