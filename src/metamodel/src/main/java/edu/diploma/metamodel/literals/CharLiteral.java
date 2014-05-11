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
public class CharLiteral extends Literal {
    private final String value;
        
    public CharLiteral(@Element(name = "type") final Type type,
                       @Element(name = "value") final String value) {
        super(type);
        this.value = value;
    }
    
}
