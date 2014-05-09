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
public class ContinueStatement extends Statement {
    private final String label;
    
    public ContinueStatement() {
        this.label = "";
    }
    public ContinueStatement(@Element(name= "label") final String label) {
        this.label = label;
    }
}
