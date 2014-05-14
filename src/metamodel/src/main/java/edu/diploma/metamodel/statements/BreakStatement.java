/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class BreakStatement implements Statement {
    private final String label;
    
    public BreakStatement() {
        this.label = "";
    }
    public BreakStatement(@Element(name = "label") final String label) {
        this.label = label;
    }

    @Override
    public void accept(Visitor visitor) {}

    @Override
    public String toString() {
        return "break";
    }
}
