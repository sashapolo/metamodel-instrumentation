/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.util;

import edu.diploma.metamodel.declarations.FunctionDecl;
import edu.diploma.metamodel.declarations.VariableDecl;
import edu.diploma.metamodel.types.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class UmlClass {
    private final String name;
    private final List<Type> inherits;
    private final List<VariableDecl> vars;
    private final List<FunctionDecl> methods;
    
    public UmlClass(final String name, final List<Type> inherits) {
        this.name = name;
        this.inherits = inherits;
        this.vars = new LinkedList<>();
        this.methods = new LinkedList<>();
    }
    
    public void addVariable(final VariableDecl var) {
        vars.add(var);
    }
    public void addMethod(final FunctionDecl func) {
        methods.add(func);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder(name).append("\n\n");
        for (final VariableDecl decl : vars) {
            result.append(decl.toString()).append("\n");
        }
        result.append("\n");
        for (final FunctionDecl decl : methods) {
            result.append(decl.toString()).append("\n");
        }
        return result.toString();
    }

    public String getName() {
        return name;
    }

    public List<VariableDecl> getVars() {
        return Collections.unmodifiableList(vars);
    }

    public List<Type> getInherits() {
        return Collections.unmodifiableList(inherits);
    }
}
