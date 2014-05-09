package main.java.edu.diploma.metamodel.statements;

import main.java.edu.diploma.metamodel.expressions.Expression;
import org.simpleframework.xml.Default;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class IfStatement extends Statement {
    private final Expression condition;
    private final Statement ifer;
    private final Statement elser;

    public IfStatement(final Expression condition, final Statement ifer, final Statement elser) {
        this.condition = condition;
        this.ifer = ifer;
        this.elser = elser;
    }
}
