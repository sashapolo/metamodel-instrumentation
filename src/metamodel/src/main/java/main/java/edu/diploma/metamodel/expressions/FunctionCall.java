/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import java.util.Collections;
import java.util.List;
import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class FunctionCall extends Expression {
    private final String name;
    private final Expression caller;
    private final List<Expression> params;
    private final List<Type> templateParams;
    
    public FunctionCall(final String name, final Expression caller, final List<Expression> params) {
        super(Type.UNKOWN_TYPE);
        this.name = name;
        this.caller = caller;
        this.params = params;
        this.templateParams = Collections.emptyList();
    }
    public FunctionCall(final String name, final Expression caller, final List<Expression> params, 
            final List<Type> templateParams) {
        super(Type.UNKOWN_TYPE);
        this.name = name;
        this.caller = caller;
        this.params = params;
        this.templateParams = templateParams;
    }
    public FunctionCall(@Element(name = "type") final Type type, 
                        @Element(name = "name") final String name, 
                        @Element(name = "caller") final Expression caller, 
                        @ElementList(name = "params") final List<Expression> params, 
                        @ElementList(name = "templateParams") final List<Type> templateParams) {
        super(type);
        this.name = name;
        this.caller = caller;
        this.params = params;
        this.templateParams = templateParams;
    }

    public String getName() {
        return name;
    }

    public Expression getCaller() {
        return caller;
    }

    public List<Expression> getParams() {
        return params;
    }

    public List<Type> getTemplateParams() {
        return templateParams;
    }
    
    
}
