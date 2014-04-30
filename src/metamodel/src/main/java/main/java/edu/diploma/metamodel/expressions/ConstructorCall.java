/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import java.util.Collections;
import java.util.List;
import main.java.edu.diploma.metamodel.declarations.DeclBody;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class ConstructorCall extends Expression {
    private final Type type;
    private final List<Expression> params;
    private final List<Type> templates;
    private final DeclBody body;
    
    public ConstructorCall(final Type type, final List<Expression> params) {
        this.type = type;
        this.params = params;
        this.templates = Collections.emptyList();
        this.body = null;
    }
    public ConstructorCall(final Type type, final List<Expression> params, final List<Type> templates) {
        this.type = type;
        this.params = params;
        this.templates = templates;
        this.body = null;
    }
    public ConstructorCall(final Type type, final List<Expression> params, final List<Type> templates, final DeclBody body) {
        this.type = type;
        this.params = params;
        this.templates = templates;
        this.body = body;
    }
}
