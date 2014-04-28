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
public class CharLiteral extends Literal {
    private final String value;
    
    public CharLiteral(final String value) {
        super(PrimitiveType.CHAR);
        this.value = value;
    }
    
}
