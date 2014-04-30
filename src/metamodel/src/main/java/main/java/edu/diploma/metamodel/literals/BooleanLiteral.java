/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.literals;

import main.java.edu.diploma.metamodel.types.PrimitiveType;

/**
 *
 * @author alexander
 */
public class BooleanLiteral extends Literal {
    private final boolean value;
    
    public BooleanLiteral(final boolean value) {
        super(PrimitiveType.BOOL);
        this.value = value;
    }
    public BooleanLiteral(final String value) {
        super(PrimitiveType.BOOL);
        this.value = Boolean.valueOf(value);
    }
}
