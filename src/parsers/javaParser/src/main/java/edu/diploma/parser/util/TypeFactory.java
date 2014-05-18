/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.parser.util;

import edu.diploma.metamodel.declarations.Declaration;
import edu.diploma.metamodel.types.ClassType;
import edu.diploma.metamodel.types.TemplateParameter;
import edu.diploma.metamodel.types.Type;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alexander
 */
public class TypeFactory {
    public static Type createJavaClassType(final Type type) {
        final List<TemplateParameter> params = new LinkedList<>();
        params.add(new TemplateParameter(type));
        return new ClassType("Class", params);
    }
    
    public static Declaration.Visibility parseModifiers(final List<String> modifiers) {
        final Iterator<String> it = modifiers.iterator();
        while (it.hasNext()) {
            final String mod = it.next();
            switch (mod) {
                case "private":
                    it.remove();
                    return Declaration.Visibility.PRIVATE;
                case "protected":
                    it.remove();
                    return Declaration.Visibility.PROTECTED;
                case "public":
                    it.remove();
                    return Declaration.Visibility.PUBLIC;
            }
        }
        return Declaration.Visibility.DEFAULT;
    }

    private TypeFactory() {
    }
}
