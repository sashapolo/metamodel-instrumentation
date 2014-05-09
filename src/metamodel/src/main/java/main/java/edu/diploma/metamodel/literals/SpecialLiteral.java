/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.literals;

import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class SpecialLiteral extends Literal {
    private final String value;
    
    public SpecialLiteral(final String value) {
        super(Type.UNKOWN_TYPE);
        this.value = value;
    }
}
