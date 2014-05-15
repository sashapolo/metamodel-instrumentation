package edu.diploma.metamodel.statements;

import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Transient;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class StatementBlock implements Statement {
    @Transient
    public final static StatementBlock EMPTY_BLOCK = new StatementBlock(Collections.<Statement>emptyList());

    private final List<Statement> statements;

    public StatementBlock() {
        this.statements = new LinkedList<Statement>();
    }
    public StatementBlock(@ElementList(name = "statements") final List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return Collections.unmodifiableList(statements);
    }
    
    public void add(final Statement statement) {
        statements.add(statement);
    }
    public void addAll(final List<Statement> statements) {
        this.statements.addAll(statements);
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Statement state : statements) {
            visitor.dispatch(state);
        }
    }
}
