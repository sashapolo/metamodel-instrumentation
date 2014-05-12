/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

/**
 *
 * @author alexander
 */
@Default
public class ReturnStatement implements Statement {
    private final Expression expr;
    
    public ReturnStatement(@Element(name = "expr") final Expression expr) {
        this.expr = expr;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(expr);
    }
}
