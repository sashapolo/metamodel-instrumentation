/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import java.util.LinkedList;
import java.util.List;
import main.java.edu.diploma.metamodel.expressions.Expression;

/**
 *
 * @author alexander
 */
public class SwitchStatement extends Statement {
    private final Expression condition;
    private final List<Label> cases;

    public SwitchStatement(final Expression condition, final List<Label> cases) {
        this.condition = condition;
        this.cases = cases;
    }
    
    public static class Label {
        public final static Label DEFAULT = new Label(null);
        
        private final Expression expr;
        private final List<Statement> states = new LinkedList<>();
        
        public Label(final Expression expr) {
            this.expr = expr;
        }
        
        public void addAll(final List<Statement> states) {
            if (expr == null) {
                throw new IllegalArgumentException("Can't add statements to DEFAULT label");
            }
            this.states.addAll(states);
        }
    }
    
    
}
