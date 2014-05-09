/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class BreakStatement extends Statement {
    private final String label;
    
    public BreakStatement() {
        this.label = "";
    }
    public BreakStatement(final String label) {
        this.label = label;
    }
}
