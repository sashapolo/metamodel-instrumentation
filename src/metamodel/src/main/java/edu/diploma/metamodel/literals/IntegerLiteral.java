/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.literals;

import java.math.BigInteger;
import edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class IntegerLiteral extends Literal {
    private final BigInteger value;
    
    public IntegerLiteral(@Element(name = "type") final Type type, 
                          @Element(name = "value") final BigInteger value) {
        super(type);
        this.value = value;
    }
    public IntegerLiteral(final Type type, final String value) {
        super(type);
        this.value = new BigInteger(value);
    }
}
