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
public class CastExpression extends Expression {
    private final Expression param;
    
    public CastExpression(final Type type, final Expression param) {
        super(type);
        this.param = param;
    }
}
