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
public class DoWhileStatement extends Statement {
    private final Expression condition;
    private final Statement body;
    
    public DoWhileStatement(@Element(name = "condition") final Expression condition, 
                            @Element(name = "body") final Statement body) {
        this.condition = condition;
        this.body = body;
    }
}
