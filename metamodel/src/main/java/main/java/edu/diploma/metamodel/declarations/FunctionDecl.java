/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.java.edu.diploma.metamodel.declarations;

import java.util.Collections;
import java.util.List;
import main.java.edu.diploma.metamodel.statements.StatementBlock;
import main.java.edu.diploma.metamodel.types.Type;

/**
 *
 * @author alexander
 */
public class FunctionDecl extends Declaration {
    private final Type retType;
    private final List<String> exceptions;
    private final List<ParameterDecl> params;
    private final List<TemplateDecl> templates;
    private final StatementBlock body;

    public static class Builder {
        private final String name;
        private final Type retType;
        private StatementBlock block = StatementBlock.EMPTY_BLOCK;
        private List<ParameterDecl> params = Collections.emptyList();
        private List<String> exceptions = Collections.emptyList();
        private List<TemplateDecl> templates = Collections.emptyList();

        public Builder(final Type retType, final String name) {
            this.retType = retType;
            this.name = name;
        }

        public Builder params(final List<ParameterDecl> params) {
            this.params = params;
            return this;
        }
        public Builder body(final StatementBlock body) {
            this.block = body;
            return this;
        }
        public Builder exceptions(final List<String> exceptions) {
            this.exceptions = exceptions;
            return this;
        }
        public Builder templates(final List<TemplateDecl> templates) {
            this.templates = templates;
            return this;
        }

        public FunctionDecl build() {
            return new FunctionDecl(retType, name, params, exceptions, templates, block);
        }
    }

    private FunctionDecl(final Type retType, final String name,
                         final List<ParameterDecl> params,
                         final List<String> exceptions,
                         final List<TemplateDecl> templates,
                         final StatementBlock body) {
        super(name);
        this.retType = retType;
        this.params = params;
        this.exceptions = exceptions;
        this.templates = templates;
        this.body = body;
    }


    public Type getRetType() {
        return retType;
    }
    public List<String> getExceptions() {
        return Collections.unmodifiableList(exceptions);
    }
    public List<ParameterDecl> getParams() {
        return Collections.unmodifiableList(params);
    }
    public List<TemplateDecl> getTemplates() {
        return Collections.unmodifiableList(templates);
    }
    public StatementBlock getBody() {
        return body;
    }
}
