package main.java.edu.diploma.metamodel;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Annotation extends Entity {
    private final String name;
    private final Map<String, Entity> values;
    private final Entity value;
    
    public Annotation(final String name) {
        this.name = name;
        this.value = null;
        this.values = null;
    }
    
    public Annotation(final String name, final Entity value) {
        this.name = name;
        this.value = value;
        this.values = null;
    }
    
    public Annotation(final String name, final Map<String, Entity> values) {
        this.name = name;
        this.values = values;
        this.value = null;
    }
}
