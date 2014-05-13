/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.visitors.Visitor;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class SwitchStatement implements Statement {
    private final Expression condition;
    private final List<Label> cases;

    public SwitchStatement(@Element(name = "condition") final Expression condition, 
                           @ElementList(name = "cases") final List<Label> cases) {
        this.condition = condition;
        this.cases = cases;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(condition);
        for (final Label state : cases) {
            visitor.dispatch(state);
        }
    }
    
    public static class Label implements Statement{
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

        @Override
        public void accept(Visitor visitor) {
            visitor.dispatch(expr);
            for (final Statement state : states) {
                visitor.dispatch(state);
            }
        }
    }
    
}
