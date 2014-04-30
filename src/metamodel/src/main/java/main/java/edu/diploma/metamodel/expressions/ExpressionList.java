package main.java.edu.diploma.metamodel.expressions;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexander on 4/26/14.
 */
public class ExpressionList extends Expression {
    private final List<Expression> expressions = new LinkedList<>();

    public void add(final Expression expr) {
        expressions.add(expr);
    }
    
    public List<Expression> asList() {
        return Collections.unmodifiableList(expressions);
    }
}
