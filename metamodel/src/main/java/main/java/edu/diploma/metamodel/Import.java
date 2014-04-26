package main.java.edu.diploma.metamodel;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 11:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Import extends Entity {
    private final String name;
    private final boolean isStatic;
    private final boolean isWildcard;
    
    public Import(final String name, boolean isStatic, boolean isWildcard) {
        this.name = name;
        this.isStatic = isStatic;
        this.isWildcard = isWildcard;
    }
}
