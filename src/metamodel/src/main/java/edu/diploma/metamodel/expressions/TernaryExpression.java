/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.expressions;

import edu.diploma.metamodel.types.Type;
import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;

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
    public TernaryExpression(@Element(name = "type") final Type type, 
                             @Element(name = "condition") final Expression condition, 
                             @Element(name = "ifBranch") final Expression ifBranch, 
                             @Element(name = "elseBranch") final Expression elseBranch) {
        super(type);
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getIfBranch() {
        return ifBranch;
    }

    public Expression getElseBranch() {
        return elseBranch;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.dispatch(condition);
        visitor.dispatch(ifBranch);
        visitor.dispatch(elseBranch);
    }
    
    
}
