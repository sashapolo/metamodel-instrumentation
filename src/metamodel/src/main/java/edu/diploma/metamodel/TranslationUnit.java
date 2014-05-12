/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel;

import edu.diploma.metamodel.declarations.Declaration;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
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
public class TranslationUnit implements Entity {
    private final List<Import> imports;
    private final List<Declaration> types;
    
    public TranslationUnit(@ElementList(name = "imports") final List<Import> imports, 
                           @ElementList(name = "types") final List<Declaration> types) {
        this.imports = imports;
        this.types = types;
    }

    public List<Import> getImports() {
        return Collections.unmodifiableList(imports);
    }

    public List<Declaration> getTypes() {
        return Collections.unmodifiableList(types);
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Import i : imports) {
            visitor.visit(i);
        }
        for (final Declaration d : types) {
            visitor.visit(d);
        }
    }
}
