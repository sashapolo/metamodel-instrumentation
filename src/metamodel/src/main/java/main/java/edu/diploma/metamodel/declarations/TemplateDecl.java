/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.List;
import main.java.edu.diploma.metamodel.types.Type;
import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class TemplateDecl extends Declaration {
    private final List<Type> bounds;
    
    public TemplateDecl(final String name, final List<Type> bounds) {
        super(name);
        this.bounds = bounds;
    }
}
