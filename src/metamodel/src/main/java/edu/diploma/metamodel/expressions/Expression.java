/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.statements.Statement;
import edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public abstract class Expression implements Statement {
    private final Type type;
    
    public Expression(@Element(name = "type") final Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
