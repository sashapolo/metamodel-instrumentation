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
public class WhileStatement extends Statement {
    private final Expression condition;
    private final Statement body;
    
    public WhileStatement(final Expression condition, final Statement body) {
        this.condition = condition;
        this.body = body;
    }
}
