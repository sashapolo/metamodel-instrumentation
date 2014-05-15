/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Transient;

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
        @Transient
        public final static Label DEFAULT = new Label(null);
        
        @Element(name = "expr", required = false)
        private final Expression expr;
        @Element(name = "states")
        private final StatementList states;
        
        private Label(@Element(name = "expr") final Expression expr,
                      @Element(name = "states") final StatementList states) {
            this.expr = expr;
            this.states = states;
        }
        public Label(final Expression expr) {
            this.expr = expr;
            this.states = new StatementList();
        }
        
        public void addAll(final List<Statement> states) {
            this.states.addAll(states);
        }

        @Override
        public void accept(Visitor visitor) {
            if (expr != null) {
                visitor.dispatch(expr);
            }
            visitor.dispatch(states);
        }

        public Expression getExpr() {
            return expr;
        }

        public StatementList getStates() {
            return states;
        }
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Label> getCases() {
        return Collections.unmodifiableList(cases);
    }
    
}
