/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel;

import java.util.List;
import main.java.edu.diploma.metamodel.declarations.Declaration;
import main.java.edu.diploma.metamodel.declarations.PackageDecl;

/**
 *
 * @author alexander
 */
public class TranslationUnit extends Entity {
    private final PackageDecl packageDecl;
    private final List<Annotation> annotations;
    private final List<Import> imports;
    private final List<Declaration> types;
    
    public TranslationUnit(final PackageDecl packageDecl,
                           final List<Annotation> annotations, 
                           final List<Import> imports,
                           final List<Declaration> types) {
        this.packageDecl = packageDecl;
        this.annotations = annotations;
        this.imports = imports;
        this.types = types;
    }
}
