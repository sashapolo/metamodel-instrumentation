package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.Entity;
import edu.diploma.visitors.Visitor;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class ArbitraryStatement implements Statement {
    private final String name;
    private final List<Entity> stuff;

    public ArbitraryStatement(@Element(name = "name") final String name, 
                              @ElementList(name = "stuff") final List<Entity> stuff) {
        this.name = name;
        this.stuff = stuff;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Entity entity : stuff) {
            visitor.visit(entity);
        }
    }
}
