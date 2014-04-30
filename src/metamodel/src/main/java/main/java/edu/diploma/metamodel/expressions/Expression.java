/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import main.java.edu.diploma.metamodel.statements.Statement;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class Expression extends Statement {
    private final Type type;
    
    public Expression() {
        this.type = Type.UNKOWN_TYPE;   
    }
    
    public Expression(final Type type) {
        this.type = type;
    }
}
