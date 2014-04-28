/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

/**
 *
 * @author alexander
 */
public class LabelStatement extends Statement {
    private final String name;
    private final Statement statement;
    
    public LabelStatement(final String name, final Statement statement) {
        this.name = name;
        this.statement = statement;
    }
}
