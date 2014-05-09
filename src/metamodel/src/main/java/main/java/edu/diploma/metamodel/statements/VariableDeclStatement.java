package main.java.edu.diploma.metamodel.statements;

import main.java.edu.diploma.metamodel.declarations.VariableDecl;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class VariableDeclStatement extends Statement {
    private final VariableDecl variable;

    public VariableDeclStatement(@Element(name = "variable") final VariableDecl variable) {
        this.variable = variable;
    }
}
