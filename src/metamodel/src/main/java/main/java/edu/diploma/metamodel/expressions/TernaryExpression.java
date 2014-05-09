/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.expressions;

import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class TernaryExpression extends Expression {
    private final Expression condition;
    private final Expression ifBranch;
    private final Expression elseBranch;
    
    public TernaryExpression(final Expression condition, final Expression ifBranch, final Expression elseBranch) {
        super(Type.UNKOWN_TYPE);
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }
}
