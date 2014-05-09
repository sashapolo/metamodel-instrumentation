/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.statements;

import java.util.List;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class TryStatement extends Statement {
    private final Statement body;
    private final Statement finallyBlock;
    private final List<CatchStatement> catches;
    
    public TryStatement(final Statement body, final List<CatchStatement> catches) {
        this.body = body;
        this.catches = catches;
        this.finallyBlock = null;
    }
    public TryStatement(final Statement body, final List<CatchStatement> catches, final Statement finallyBlock) {
        this.body = body;
        this.catches = catches;
        this.finallyBlock = finallyBlock;
    }
}
