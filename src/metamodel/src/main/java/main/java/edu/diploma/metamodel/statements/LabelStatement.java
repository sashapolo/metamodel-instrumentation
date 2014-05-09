/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class LabelStatement extends Statement {
    private final String name;
    private final Statement statement;
    
    public LabelStatement(@Element(name = "name") final String name, 
                          @Element(name = "statement") final Statement statement) {
        this.name = name;
        this.statement = statement;
    }
}
