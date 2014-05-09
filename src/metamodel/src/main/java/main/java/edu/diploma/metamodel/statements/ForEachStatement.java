/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import main.java.edu.diploma.metamodel.expressions.Expression;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class ForEachStatement extends Statement {
    private final VariableDeclStatement init;
    private final Expression range;
    private final Statement body;
    
    public ForEachStatement(@Element(name = "init") final VariableDeclStatement init, 
                            @Element(name = "range") final Expression range, 
                            @Element(name = "body") final Statement body) {
        this.init = init;
        this.range = range;
        this.body = body;
    }
}
