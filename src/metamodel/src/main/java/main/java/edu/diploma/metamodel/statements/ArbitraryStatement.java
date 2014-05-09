package main.java.edu.diploma.metamodel.statements;

import java.util.List;
import main.java.edu.diploma.metamodel.Entity;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Created by alexander on 4/26/14.
 */
@Default
public class ArbitraryStatement extends Statement {
    private final String name;
    private final List<Entity> stuff;

    public ArbitraryStatement(@Element(name = "name") final String name, 
                              @ElementList(name = "stuff") final List<Entity> stuff) {
        this.name = name;
        this.stuff = stuff;
    }
}
