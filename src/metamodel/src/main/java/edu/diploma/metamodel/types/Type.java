package edu.diploma.metamodel.types;

import edu.diploma.metamodel.Entity;
import org.simpleframework.xml.Default;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Default
public abstract class Type extends Entity {
    public final static Type UNKOWN_TYPE = new PrimitiveType("unknown");
}
