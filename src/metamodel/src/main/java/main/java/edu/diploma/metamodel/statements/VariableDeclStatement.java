package main.java.edu.diploma.metamodel.statements;

import main.java.edu.diploma.metamodel.declarations.VariableDecl;
import org.simpleframework.xml.Default;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class VariableDeclStatement extends Statement {
    private final VariableDecl variable;

    public VariableDeclStatement(final VariableDecl variable) {
        this.variable = variable;
    }
}
