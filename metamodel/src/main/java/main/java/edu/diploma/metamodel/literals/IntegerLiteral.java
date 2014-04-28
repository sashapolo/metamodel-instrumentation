/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.literals;

import java.math.BigInteger;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class IntegerLiteral extends Literal {
    private final BigInteger value;
    
    public IntegerLiteral(final Type type, final String value) {
        super(type);
        this.value = new BigInteger(value);
    }
}
