/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.literals;

import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class Literal extends Expression {
    public Literal(final Type type) {
        super(type);
    }
}
