/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.types;

import org.simpleframework.xml.Default;

/**
 *
 * @author alexander
 */
@Default
public class TemplateParameter extends Type {
    private final Type type;
    private final boolean isSuper;
    private final boolean isExtends;

    public TemplateParameter(final Type type) {
        this.type = type;
        this.isSuper = false;
        this.isExtends = false;
    }
    
    private TemplateParameter(final Type type, boolean isSuper, boolean isExtends) {
        this.type = type;
        this.isSuper = isSuper;
        this.isExtends = isExtends;
    }
    
    public static TemplateParameter createWildcardTemplate(final Type type, boolean isSuper) {
        return new TemplateParameter(type, isSuper, !isSuper);
    }
    
}
