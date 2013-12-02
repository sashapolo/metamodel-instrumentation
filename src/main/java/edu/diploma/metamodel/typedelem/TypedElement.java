package edu.diploma.metamodel.typedelem;

import edu.diploma.metamodel.Entity;
import edu.diploma.metamodel.types.Type;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class TypedElement extends Entity {
    protected final Type type;

    public TypedElement(final Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
