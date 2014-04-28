/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import main.java.edu.diploma.metamodel.expressions.Expression;

/**
 *
 * @author alexander
 */
public class ForEachStatement extends Statement {
    private final VariableDeclStatement init;
    private final Expression range;
    private final Statement body;
    
    public ForEachStatement(final VariableDeclStatement init, final Expression range, final Statement body) {
        this.init = init;
        this.range = range;
        this.body = body;
    }
}
