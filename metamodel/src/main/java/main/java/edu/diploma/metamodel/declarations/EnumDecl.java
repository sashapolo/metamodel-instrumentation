/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.Collections;
import java.util.List;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class EnumDecl extends Declaration {
    private final List<Type> inherits;
    private final List<EnumValue> enums;
    private final DeclBody body;
    
    public EnumDecl(final String name, final List<Type> inherits, final List<EnumValue> enums, final DeclBody body) {
        super(name);
        this.inherits = inherits;
        this.enums = enums;
        this.body = body;
    }
    public EnumDecl(final String name, final List<EnumValue> enums) {
        super(name);
        this.inherits = Collections.emptyList();
        this.enums = enums;
        this.body = null;
    }
}
