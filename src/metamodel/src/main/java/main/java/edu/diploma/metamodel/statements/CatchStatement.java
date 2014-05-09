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
public class CatchStatement extends Statement {
    private final VariableDeclStatement exception;
    private final Statement body;
    
    public CatchStatement(final VariableDeclStatement exception, final Statement body) {
        this.exception = exception;
        this.body = body;
    }
    
    
}
