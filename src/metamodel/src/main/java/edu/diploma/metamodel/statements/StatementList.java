/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.util.Stringifier;
import edu.diploma.visitors.Visitor;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class StatementList implements Statement {
    private final List<Statement> statements;
    
    public StatementList(@ElementList(name = "statements") final List<Statement> statements) {
        this.statements = statements;
    }
    public StatementList() {
        this.statements = new LinkedList<>();
    }
    
    public void addAll(final List<? extends Statement> statements) {
        this.statements.addAll(statements);
    }
    
    public List<Statement> asList() {
        return statements;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Statement state : statements) {
            visitor.dispatch(state);
        }
    }

    @Override
    public String toString() {
        return Stringifier.toString(statements);
    }
    
    
}
