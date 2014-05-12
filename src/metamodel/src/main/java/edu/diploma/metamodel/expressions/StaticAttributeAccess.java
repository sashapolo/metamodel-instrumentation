/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.types.Type;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class StaticAttributeAccess extends Expression {
    private final Type qualifier;
    private final VariableReference attribute;
    
    public StaticAttributeAccess(@Element(name = "type") final Type type, 
                                 @Element(name = "qualifier") final Type qualifier, 
                                 @Element(name = "attribute") final VariableReference attribute) {
        super(type);
        this.qualifier = qualifier;
        this.attribute = attribute;
    }
    public StaticAttributeAccess(final Type qualifier, final VariableReference attribute) {
        super(Type.UNKOWN_TYPE);
        this.qualifier = qualifier;
        this.attribute = attribute;
    }

    public Type getQualifier() {
        return qualifier;
    }

    public VariableReference getAttribute() {
        return attribute;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(qualifier);
        visitor.visit(attribute);
    }
    
    
}
