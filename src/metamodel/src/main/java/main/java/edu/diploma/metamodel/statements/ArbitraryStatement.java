package main.java.edu.diploma.metamodel.statements;

import java.util.List;
import main.java.edu.diploma.metamodel.Entity;

/**
 * Created by alexander on 4/26/14.
 */
public class ArbitraryStatement extends Statement {
    private final String name;
    private final List<Entity> stuff;

    public ArbitraryStatement(final String name, final List<Entity> stuff) {
        this.name = name;
        this.stuff = stuff;
    }
}
