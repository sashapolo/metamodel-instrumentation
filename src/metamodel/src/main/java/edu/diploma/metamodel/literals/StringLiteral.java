/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.literals;

import edu.diploma.metamodel.types.Type;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class StringLiteral extends Literal {
    private final String value;
    
    public StringLiteral(@Element(name = "type") final Type type, 
                         @Element(name = "value") final String value) {
        super(type);
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor) {
    }
}
