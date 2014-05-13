/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class EnumValue extends Declaration {
    private final List<Expression> value;
    private final DeclBody body;
    
    private EnumValue(@Element(name = "name") String name, 
                      @Element(name = "body") final DeclBody body,
                      @ElementList(name = "modifiers") final List<String> modifiers,
                      @ElementList(name = "value") final List<Expression> value,
                      @ElementList(name = "annotations") final List<Annotation> annotations) {
        super(name, modifiers, annotations);
        this.value = value;
        this.body = body;
    }
    
    public EnumValue(final String name, final List<Expression> value, final DeclBody body) {
        super(name);
        this.value = value;
        this.body = body;
    }
    public EnumValue(final String name, final List<Expression> value) {
        super(name);
        this.value = value;
        this.body = DeclBody.EMPTY;
    }

    public List<Expression> getValue() {
        return Collections.unmodifiableList(value);
    }

    public DeclBody getBody() {
        return body;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : getAnnotations()) {
            visitor.dispatch(anno);
        }
        for (final Expression expr : value) {
            visitor.dispatch(expr);
        }
        visitor.dispatch(body);
    }
}
