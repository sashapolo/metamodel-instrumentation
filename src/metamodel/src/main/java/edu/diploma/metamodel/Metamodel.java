/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel;

import edu.diploma.visitors.Visitor;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author alexander
 */
@Root
@Default
public class Metamodel implements Entity {
    private final List<TranslationUnit> units;
    
    public Metamodel(@ElementList(name = "units") final List<TranslationUnit> units) {
        this.units = units;
    }
    
    @Override
    public void accept(Visitor visitor) {
        for (final TranslationUnit u : units) {
            visitor.dispatch(u);
        }
    }
    
}
