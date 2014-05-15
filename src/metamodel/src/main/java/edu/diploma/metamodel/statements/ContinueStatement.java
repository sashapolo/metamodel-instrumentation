/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
public class ContinueStatement implements Statement {
    @Element(required = false)
    private final String label;
    
    public ContinueStatement() {
        this.label = "";
    }
    public ContinueStatement(@Element(name= "label") final String label) {
        this.label = label;
    }

    @Override
    public void accept(Visitor visitor) {}

    @Override
    public String toString() {
        return "continue";
    }
}
