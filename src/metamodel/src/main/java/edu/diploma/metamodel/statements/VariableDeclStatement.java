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

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(variable);
    }
}
