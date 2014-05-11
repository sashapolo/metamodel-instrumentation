/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.literals;

import edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class BooleanLiteral extends Literal {
    private final boolean value;
    
    public BooleanLiteral(@Element(name = "type") final Type type, 
                          @Element(name = "value") final boolean value) {
        super(type);
        this.value = value;
    }
    public BooleanLiteral(final Type type, final String value) {
        super(type);
        this.value = Boolean.valueOf(value);
    }
}
