package main.java.edu.diploma.metamodel.types;

import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassType extends Type {
    @Element
    private final String name;
    @Element(required = false)
    private final ClassType outer;
    @ElementList
    private final List<TemplateParameter> templates;
    
    public ClassType(final String name) {
        this.name = name;
        this.templates = Collections.emptyList();
        this.outer = null;
    }
    public ClassType(final String name, final List<TemplateParameter> templates) {
        this.name = name;
        this.templates = templates;
        this.outer = null;
    }
    public ClassType(final String name, final List<TemplateParameter> templates, final ClassType outer) {
        this.name = name;
        this.templates = templates;
        this.outer = outer;
    }

    public String getName() {
        return name;
    }
}
