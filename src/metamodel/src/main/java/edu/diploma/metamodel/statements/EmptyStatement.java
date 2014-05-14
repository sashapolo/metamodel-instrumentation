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
public class EmptyStatement implements Statement {
    //FIXME find how to get rid of this shit
    @Element(required = false)
    private final String placeholder;
    
    public EmptyStatement() {
        this.placeholder = null;
    }
    private EmptyStatement(@Element(name = "placeholder") final String placeholder) {
        this.placeholder = placeholder;
    }
    
    @Override
    public void accept(Visitor visitor) {}
}
