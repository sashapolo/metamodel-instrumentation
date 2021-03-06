/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.types;

import edu.diploma.visitors.Visitor;
import java.util.Objects;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class TemplateParameter implements Type {
    private final Type type;
    private final boolean isSuper;
    private final boolean isExtends;

    private TemplateParameter(@Element(name = "type") final Type type, 
                              @Element(name = "isSuper") boolean isSuper, 
                              @Element(name = "isExtends") boolean isExtends) {
        this.type = type;
        this.isSuper = isSuper;
        this.isExtends = isExtends;
    }
    
    public TemplateParameter(final Type type) {
        this.type = type;
        this.isSuper = false;
        this.isExtends = false;
    }
    
    public static TemplateParameter createWildcardTemplate(final Type type, boolean isSuper) {
        return new TemplateParameter(type, isSuper, !isSuper);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(type);
    }

    @Override
    public String toString() {
        if (isSuper) {
            return "? super " + type.toString();
        } else if (isExtends) {
            return "? extends " + type.toString();
        } else {
            return type == null ? "?" : type.toString();
        }
    }

    
    // FIXME this is genuinely wrong and MUST be changed later
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.type);
        return hash;
    }
    // FIXME this is genuinely wrong and MUST be changed later
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TemplateParameter other = (TemplateParameter) obj;
        return Objects.equals(this.type, other.type);
    }
    
    
}
