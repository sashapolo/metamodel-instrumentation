package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.types.Type;
import edu.diploma.visitors.Visitor;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class ExpressionList extends Expression {
    private final List<Expression> expressions;
    
    public ExpressionList() {
        super(Type.UNKOWN_TYPE);
        this.expressions = new LinkedList<>();
    }
    public ExpressionList(@Element(name = "type") final Type type,
                          @ElementList(name = "expressions") final List<Expression> expressions) {
        super(type);
        this.expressions = expressions;
    }
    
    public void add(final Expression expr) {
        expressions.add(expr);
    }
    
    public List<Expression> asList() {
        return expressions;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Expression expr : expressions) {
            visitor.visit(expr);
        }
    }
}
