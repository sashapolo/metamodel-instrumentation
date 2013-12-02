package edu.diploma.metamodel.types;

import edu.diploma.metamodel.behaviour.Method;
import edu.diploma.metamodel.typedelem.Attribute;
import edu.diploma.metamodel.typedelem.Parameter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Class extends Type {
    private final String name;
    private final List<Parameter> generics = new LinkedList<>();
    private final List<Attribute> attributes = new LinkedList<>();
    private final List<Method> methods = new LinkedList<>();

    public Class(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
