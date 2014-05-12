/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
public class TryStatement extends Statement {
    @Element
    private final Statement body;
    @Element(required = false)
    private final Statement finallyBlock;
    @ElementList
    private final List<CatchStatement> catches;
    
    public TryStatement(final Statement body, final List<CatchStatement> catches) {
        this.body = body;
        this.catches = catches;
        this.finallyBlock = null;
    }
    public TryStatement(@Element(name = "body") final Statement body, 
                        @ElementList(name = "catches") final List<CatchStatement> catches, 
                        @Element(name = "finallyBlock") final Statement finallyBlock) {
        this.body = body;
        this.catches = catches;
        this.finallyBlock = finallyBlock;
    }
}
