package edu.diploma.metamodel;

import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:15 PM
 * To change this template use File | Settings | File Templates.
 */
    
@Default
public class Import implements Entity {
    private final String name;
    private final boolean isStatic;
    private final boolean isWildcard;
    
    public Import(@Element(name = "name") final String name, 
                  @Element(name = "isStatic") boolean isStatic, 
                  @Element(name = "isWildcard") boolean isWildcard) {
        this.name = name;
        this.isStatic = isStatic;
        this.isWildcard = isWildcard;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public boolean isWildcard() {
        return isWildcard;
    }

    @Override
    public void accept(Visitor visitor) {}
}
