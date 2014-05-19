package edu.diploma.metamodel.types;

import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassType implements Type {
    @Element
    private final String name;
    @Element(required = false)
    private final ClassType outer;
    @ElementList
    private final List<TemplateParameter> templates;
    
    public ClassType(@Element(name = "name") final String name) {
        this.name = name;
        this.templates = Collections.emptyList();
        this.outer = null;
    }
    public ClassType(@Element(name = "name") final String name, 
                     @ElementList(name = "templates") final List<TemplateParameter> templates) {
        this.name = name;
        this.templates = templates;
        this.outer = null;
    }
    public ClassType(@Element(name = "name") final String name, 
                     @ElementList(name = "templates") final List<TemplateParameter> templates, 
                     @Element(name = "outer") final ClassType outer) {
        this.name = name;
        this.templates = templates;
        this.outer = outer;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        if (outer != null) {
            visitor.dispatch(outer);
        }
        for (final TemplateParameter param : templates) {
            visitor.dispatch(param);
        }
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        if (outer != null) {
            result.append(outer.toString());
        }
        result.append(name);
        if (!templates.isEmpty()) {
            result.append('<');
            final Iterator<TemplateParameter> it = templates.iterator();
            while (it.hasNext()) {
                result.append(it.next());
                if (it.hasNext()) {
                    result.append(", ");
                }
            }
            result.append('>');
        }
        return result.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.outer);
        hash = 71 * hash + Objects.hashCode(this.templates);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassType other = (ClassType) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.outer, other.outer)) {
            return false;
        }
        if (!Objects.equals(this.templates, other.templates)) {
            return false;
        }
        return true;
    }
    
    
}
