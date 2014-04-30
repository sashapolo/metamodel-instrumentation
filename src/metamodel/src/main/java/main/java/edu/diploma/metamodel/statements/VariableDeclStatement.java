package main.java.edu.diploma.metamodel.statements;

import main.java.edu.diploma.metamodel.declarations.VariableDecl;

/**
 * Created by alexander on 4/26/14.
 */
public class VariableDeclStatement extends Statement {
    private final VariableDecl variable;

    public VariableDeclStatement(final VariableDecl variable) {
        this.variable = variable;
    }
}
