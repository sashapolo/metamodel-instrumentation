/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.metamodel.declarations.VariableDecl;
import edu.diploma.visitors.Visitor;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class TryWithResourcesStatement implements Statement {
    @ElementList
    private final List<VariableDecl> resources;
    @Element
    private final Statement body;
    @Element(required = false)
    private final Statement finallyBlock;
    @ElementList
    private final List<CatchStatement> catches;
    
    public TryWithResourcesStatement(final List<VariableDecl> resources, final Statement body, 
            final List<CatchStatement> catches) {
        this.resources = resources;
        this.body = body;
        this.catches = catches;
        this.finallyBlock = null;
    }
    public TryWithResourcesStatement(@ElementList(name = "resources") final List<VariableDecl> resources, 
                                     @Element(name = "body") final Statement body, 
                                     @ElementList(name = "catches") final List<CatchStatement> catches, 
                                     @Element(name = "finallyBlock") final Statement finallyBlock) {
        this.resources = resources;
        this.body = body;
        this.catches = catches;
        this.finallyBlock = finallyBlock;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final VariableDecl var : resources) {
            visitor.dispatch(var);
        }
        visitor.dispatch(body);
        for (final CatchStatement c : catches) {
            visitor.dispatch(c);
        }
        if (finallyBlock != null) {
            visitor.dispatch(finallyBlock);
        }
    }
}
