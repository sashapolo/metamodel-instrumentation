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
public class PrimitiveType implements Type {   
    private final String name;
    
    public PrimitiveType(@Element(name = "name") final String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {}
}