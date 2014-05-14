/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.types;

import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class ArrayType implements Type {
    private final Type type;
    
    public ArrayType(@Element(name = "type") final Type type) {
        this.type = type;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(type);
    }

    @Override
    public String toString() {
        return type.toString() + "[]";
    }
}
