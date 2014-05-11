/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.parser.util;

import java.util.LinkedList;
import java.util.List;
import edu.diploma.metamodel.types.ClassType;
import edu.diploma.metamodel.types.TemplateParameter;
import edu.diploma.metamodel.types.Type;

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

    private TypeFactory() {
    }
}
