/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.types;

import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class ArrayType extends Type {
    private final Type type;
    
    public ArrayType(final Type type) {
        this.type = type;
    }
}
