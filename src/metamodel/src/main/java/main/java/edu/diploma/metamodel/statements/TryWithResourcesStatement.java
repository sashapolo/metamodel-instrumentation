/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import java.util.List;
import main.java.edu.diploma.metamodel.declarations.VariableDecl;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class TryWithResourcesStatement extends Statement {
    private final List<VariableDecl> resources;
    private final Statement body;
    private final Statement finallyBlock;
    private final List<CatchStatement> catches;
    
    public TryWithResourcesStatement(final List<VariableDecl> resources, final Statement body, 
            final List<CatchStatement> catches) {
        this.resources = resources;
        this.body = body;
        this.catches = catches;
        this.finallyBlock = null;
    }
    public TryWithResourcesStatement(final List<VariableDecl> resources, final Statement body, 
            final List<CatchStatement> catches, final Statement finallyBlock) {
        this.resources = resources;
        this.body = body;
        this.catches = catches;
        this.finallyBlock = finallyBlock;
    }
}
