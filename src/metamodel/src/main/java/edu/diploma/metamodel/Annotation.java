package edu.diploma.metamodel;

import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.Map;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementMap;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class Annotation implements Entity {
    @Element
    private final String name;
    @ElementMap
    private final Map<String, Entity> values;
    @Element(required = false)
    private final Entity value;
    
    private Annotation(@Element(name = "name") final String name, 
                       @ElementMap(name = "values") final Map<String, Entity> values,
                       @Element(name = "value") final Entity value) {
        this.name = name;
        this.values = values;
        this.value = value;
    }
    public Annotation(final String name) {
        this.name = name;
        this.value = null;
        this.values = Collections.emptyMap();
    }
    public Annotation(final String name, final Entity value) {
        this.name = name;
        this.value = value;
        this.values = Collections.emptyMap();
    }
    public Annotation(final String name, final Map<String, Entity> values) {
        this.name = name;
        this.values = values;
        this.value = null;
    }

    public String getName() {
        return name;
    }

    public Map<String, Entity> getValues() {
        return Collections.unmodifiableMap(values);
    }

    public Entity getValue() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        if (value == null) {
            for (final Entity val : values.values()) {
                visitor.dispatch(val);
            }
        } else {
            visitor.dispatch(value);
        }
    }
}
