package edu.diploma.metamodel.behaviour;

import edu.diploma.metamodel.Operations.Operation;
import edu.diploma.metamodel.types.Type;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Method {
    private final List<Type> exceptions = new LinkedList<>();
    private final List<Operation> operations = new LinkedList<>();
}
