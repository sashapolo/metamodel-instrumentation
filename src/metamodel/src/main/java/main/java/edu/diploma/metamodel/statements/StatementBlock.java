package main.java.edu.diploma.metamodel.statements;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Transient;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class StatementBlock extends Statement {
    @Transient
    public final static StatementBlock EMPTY_BLOCK = new StatementBlock(Collections.<Statement>emptyList());

    private final List<Statement> statements;

    public StatementBlock(@ElementList(name = "statements") final List<Statement> statements) {
        this.statements = statements;
    }

    public static StatementBlock create() {
        return new StatementBlock(new LinkedList<Statement>());
    }
    
    public void add(final Statement statement) {
        statements.add(statement);
    }
    public void addAll(final List<Statement> statements) {
        this.statements.addAll(statements);
    }
}
