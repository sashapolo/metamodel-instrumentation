package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Element;

/**
 * Created by alexander on 4/26/14.
 */
public class IfStatement implements Statement {
    @Element
    private final Expression condition;
    @Element
    private final Statement ifer;
    @Element(required = false)
    private final Statement elser;

    public IfStatement(@Element(name = "condition") final Expression condition, 
                       @Element(name = "ifer") final Statement ifer, 
                       @Element(name = "elser") final Statement elser) {
        this.condition = condition;
        this.ifer = ifer;
        this.elser = elser;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(condition);
        visitor.visit(ifer);
        if (elser != null) {
            visitor.visit(elser);
        }
    }
}