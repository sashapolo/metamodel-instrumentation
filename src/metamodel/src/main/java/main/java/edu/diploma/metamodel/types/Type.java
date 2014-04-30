package main.java.edu.diploma.metamodel.types;

import main.java.edu.diploma.metamodel.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Type extends Entity {
    public final static Type UNKOWN_TYPE = new UnknownType();
}
