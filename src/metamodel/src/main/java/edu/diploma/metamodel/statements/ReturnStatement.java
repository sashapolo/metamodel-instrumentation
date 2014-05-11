/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.expressions.Expression;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class ReturnStatement extends Statement {
    private final Expression expr;
    
    public ReturnStatement(@Element(name = "expr") final Expression expr) {
        this.expr = expr;
    }
}
