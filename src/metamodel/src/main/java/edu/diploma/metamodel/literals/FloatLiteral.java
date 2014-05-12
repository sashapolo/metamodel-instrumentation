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
public class FloatLiteral extends Literal {
    private final double value;
    
    public FloatLiteral(@Element(name = "type") final Type type, 
                        @Element(name = "value") final double value) {
        super(type);
        this.value = value;
    }
    public FloatLiteral(final Type type, final String value) {
        super(type);
        this.value = Double.valueOf(value);
    }

    @Override
    public void accept(Visitor visitor) {
    }
}
