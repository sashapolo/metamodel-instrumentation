/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.metamodel.declarations;

import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.types.Type;
import edu.diploma.visitors.Visitor;
import java.util.Collections;
import java.util.List;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 *
 * @author alexander
 */
@Default
public class EnumDecl extends Declaration {
    private final List<Type> inherits;
    private final List<EnumValue> enums;
    private final DeclBody body;
    
    private EnumDecl(@Element(name = "name") String name, 
                     @Element(name = "visibility") final Visibility visibility,
                     @ElementList(name = "modifiers") final List<String> modifiers,
                     @ElementList(name = "annotations") final List<Annotation> annotations,
                     @ElementList(name = "enums") final List<EnumValue> enums, 
                     @ElementList(name = "inherits") final List<Type> inherits,
                     @Element(name = "body") final DeclBody body) {
        super(name, visibility, modifiers, annotations);
        this.enums = enums;
        this.inherits = inherits;
        this.body = body;
    }
    
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

    public List<Type> getInherits() {
        return Collections.unmodifiableList(inherits);
    }

    public List<EnumValue> getEnums() {
        return Collections.unmodifiableList(enums);
    }

    public DeclBody getBody() {
        return body;
    }

    @Override
    public void accept(Visitor visitor) {
        for (final Annotation anno : getAnnotations()) {
            visitor.dispatch(anno);
        }
        for (final Type type : inherits) {
            visitor.dispatch(type);
        }
        for (final EnumValue val : enums) {
            visitor.dispatch(val);
        }
        visitor.dispatch(body);
    }
}
