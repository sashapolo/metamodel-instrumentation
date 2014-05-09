/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import main.java.edu.diploma.metamodel.expressions.Expression;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class ThrowStatement extends Statement {
    private final Expression expr;
    
    public ThrowStatement(final Expression expr) {
        this.expr = expr;
    }
}
