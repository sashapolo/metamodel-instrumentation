/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class StatementList extends Statement {
    private final List<Statement> statements = new LinkedList<>();
    
    public StatementList(final List<? extends Statement> statements) {
        this.statements.addAll(statements);
    }
}
