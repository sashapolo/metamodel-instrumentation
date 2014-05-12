/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.statements;

import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class EmptyStatement implements Statement {

    @Override
    public void accept(Visitor visitor) {}
    
}
