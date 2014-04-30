package main.java.edu.diploma.metamodel.statements;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexander on 4/26/14.
 */
public class StatementBlock extends Statement {
    public final static StatementBlock EMPTY_BLOCK = new StatementBlock(Collections.<Statement>emptyList());

    private final List<Statement> statements;

    public StatementBlock() {
        this.statements = new LinkedList<>();
    }
    public StatementBlock(final List<Statement> statements) {
        this.statements = statements;
    }

    public void add(final Statement statement) {
        statements.add(statement);
    }
    public void addAll(final List<Statement> statements) {
        this.statements.addAll(statements);
    }
}
