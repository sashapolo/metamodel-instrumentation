/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import java.util.LinkedList;
import java.util.List;
import main.java.edu.diploma.metamodel.Entity;

/**
 *
 * @author alexander
 */
public class ArrayInitializer extends Expression {
    private final List<Entity> values = new LinkedList<>();
    
    public ArrayInitializer(final List<? extends Entity> values) {
        this.values.addAll(values);
    }
}
