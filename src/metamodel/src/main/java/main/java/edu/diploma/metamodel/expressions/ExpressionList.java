package main.java.edu.diploma.metamodel.expressions;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class ExpressionList extends Expression {
    private final List<Expression> expressions = new LinkedList<>();
    
    public ExpressionList() {
        super(Type.UNKOWN_TYPE);
    }
    
    public void add(final Expression expr) {
        expressions.add(expr);
    }
    
    public List<Expression> asList() {
        return Collections.unmodifiableList(expressions);
    }
}
