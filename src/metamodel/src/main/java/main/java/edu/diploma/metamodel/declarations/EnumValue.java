/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.List;
import main.java.edu.diploma.metamodel.expressions.Expression;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class EnumValue extends Declaration {
    private final List<Expression> value;
    private final DeclBody body;
    
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
}
