/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import java.util.LinkedList;
import java.util.List;
import main.java.edu.diploma.metamodel.Entity;
import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class ArrayInitializer extends Expression {
    private final List<Entity> values = new LinkedList<>();
    
    public ArrayInitializer(final List<? extends Entity> values) {
        super(Type.UNKOWN_TYPE);
        this.values.addAll(values);
    }
}
