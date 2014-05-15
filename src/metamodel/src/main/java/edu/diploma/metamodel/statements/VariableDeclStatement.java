package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.declarations.VariableDecl;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class VariableDeclStatement implements Statement {
    private final VariableDecl variable;

    public VariableDeclStatement(@Element(name = "variable") final VariableDecl variable) {
        this.variable = variable;
    }

    public VariableDecl getVariable() {
        return variable;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(variable);
    }

    @Override
    public String toString() {
        return variable.toString();
    }
}
