/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class ForStatement implements Statement {
    private final Statement init;
    private final Expression condition;
    private final Statement action;
    private final Statement body;
    
    public ForStatement(@Element(name = "init") final Statement init, 
                        @Element(name = "condition") final Expression condition, 
                        @Element(name = "action") final Statement action, 
                        @Element(name = "body") final Statement body) {
        this.init = init;
        this.condition = condition;
        this.action = action;
        this.body = body;
    }

    public Statement getInit() {
        return init;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getAction() {
        return action;
    }

    public Statement getBody() {
        return body;
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(init);
        visitor.dispatch(condition);
        visitor.dispatch(action);
        visitor.dispatch(body);
    }
}
