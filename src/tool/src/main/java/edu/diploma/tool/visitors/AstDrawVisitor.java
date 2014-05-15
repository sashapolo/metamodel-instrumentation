/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.visitors;

import com.mxgraph.layout.mxCompactTreeLayout;
import edu.diploma.metamodel.Annotation;
import edu.diploma.metamodel.Entity;
import edu.diploma.metamodel.Import;
import edu.diploma.metamodel.Metamodel;
import edu.diploma.metamodel.TranslationUnit;
import edu.diploma.metamodel.declarations.ClassDecl;
import edu.diploma.metamodel.declarations.DeclBody;
import edu.diploma.metamodel.declarations.Declaration;
import edu.diploma.metamodel.declarations.FunctionDecl;
import edu.diploma.metamodel.declarations.ParameterDecl;
import edu.diploma.metamodel.declarations.TemplateDecl;
import edu.diploma.metamodel.declarations.VariableDecl;
import edu.diploma.metamodel.expressions.ArrayAccessExpression;
import edu.diploma.metamodel.expressions.ArrayConstructorCall;
import edu.diploma.metamodel.expressions.ArrayInitializer;
import edu.diploma.metamodel.expressions.AssignmentExpression;
import edu.diploma.metamodel.expressions.AttributeAccess;
import edu.diploma.metamodel.expressions.BinaryExpression;
import edu.diploma.metamodel.expressions.CastExpression;
import edu.diploma.metamodel.expressions.ConstructorCall;
import edu.diploma.metamodel.expressions.Expression;
import edu.diploma.metamodel.expressions.ExpressionList;
import edu.diploma.metamodel.expressions.FunctionCall;
import edu.diploma.metamodel.expressions.StaticAttributeAccess;
import edu.diploma.metamodel.expressions.TypeExpression;
import edu.diploma.metamodel.expressions.UnaryExpression;
import edu.diploma.metamodel.expressions.VariableReference;
import edu.diploma.metamodel.literals.BooleanLiteral;
import edu.diploma.metamodel.literals.CharLiteral;
import edu.diploma.metamodel.literals.FloatLiteral;
import edu.diploma.metamodel.literals.IntegerLiteral;
import edu.diploma.metamodel.literals.SpecialLiteral;
import edu.diploma.metamodel.literals.StringLiteral;
import edu.diploma.metamodel.statements.BreakStatement;
import edu.diploma.metamodel.statements.CatchStatement;
import edu.diploma.metamodel.statements.ContinueStatement;
import edu.diploma.metamodel.statements.DoWhileStatement;
import edu.diploma.metamodel.statements.EmptyStatement;
import edu.diploma.metamodel.statements.ForEachStatement;
import edu.diploma.metamodel.statements.ForStatement;
import edu.diploma.metamodel.statements.IfStatement;
import edu.diploma.metamodel.statements.ReturnStatement;
import edu.diploma.metamodel.statements.Statement;
import edu.diploma.metamodel.statements.StatementBlock;
import edu.diploma.metamodel.statements.StatementList;
import edu.diploma.metamodel.statements.SwitchStatement;
import edu.diploma.metamodel.statements.ThrowStatement;
import edu.diploma.metamodel.statements.TryStatement;
import edu.diploma.metamodel.statements.VariableDeclStatement;
import edu.diploma.metamodel.statements.WhileStatement;
import edu.diploma.metamodel.types.ArrayType;
import edu.diploma.metamodel.types.ClassType;
import edu.diploma.metamodel.types.PrimitiveType;
import edu.diploma.metamodel.types.Type;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author alexander
 */
public class AstDrawVisitor extends DrawVisitor {
    final Deque<Object> parents = new ArrayDeque<>();
    final Deque<String> names = new ArrayDeque<>();
    
    private void visitDeclaration(final Declaration decl) {
        names.push("annotation");
        for (final Annotation anno : decl.getAnnotations()) {
            dispatch(anno);
        }
        for (final String mod : decl.getModifiers()) {
            final Object child = graph.insertVertex(mod);
            graph.insertEdge(parents.peek(), child, "modifier");
        }
        names.pop();
    }
    
    private void insertTerminal(final String label) {
        insertTerminal(label, "");
    }
    private void insertTerminal(final String label, final String edge) {
        final Object child = graph.insertVertex("'" + label + "'");
        graph.insertEdge(parents.peek(), child, edge);
    }
    
    public AstDrawVisitor() {
        graph.setAllowLoops(true);
        graph.setCellsMovable(true);
        graph.setCellsResizable(false);
        graph.setCellsEditable(false);
    }

    @Override
    public void navigate(Entity entity) {
    }

    public void visit(Annotation entity) {
        final Object vertex = graph.insertVertex("Annotation");
        graph.insertEdge(parents.peek(), vertex, names.peek());
                
        final Object name = graph.insertVertex(entity.getName());
        graph.insertEdge(vertex, name, "name");
        
        parents.push(vertex);
        names.push("value");
        entity.accept(this);
        parents.pop();
        names.pop();
    }
    
    public void visit(Import entity) {
        final Object vertex = graph.insertVertex("Import");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("import");
        Object child = graph.insertVertex(entity.getName());
        graph.insertEdge(vertex, child, "name");
        
        parents.pop();
    }

    
    public void visit(TranslationUnit entity) {
        final Object vertex = graph.insertVertex("Translation Unit");
        graph.insertEdge(parents.peek(), vertex, names.peek());

        parents.push(vertex);
        names.push("import");
        for (final Import i : entity.getImports()) {
            dispatch(i);
        }
        names.pop();
        names.push("declaration");
        for (final Declaration d : entity.getTypes()) {
            dispatch(d);
        }
        parents.pop();
        names.pop();
    }

    public void visit(ClassDecl entity) {
        final Object vertex = graph.insertVertex("Class Declaration");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("class");
        
        Object child = graph.insertVertex(entity.getName());
        graph.insertEdge(vertex, child, "name");
        
        visitDeclaration(entity);
        
        names.push("template-param");
        for (final TemplateDecl templ : entity.getTemplates()) {
            dispatch(templ);
        }
        names.pop();
        
        names.push("extends");
        for (final Type inh : entity.getInherits()) {
            dispatch(inh);
        }
        names.pop();
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(DeclBody entity) {
        final Object vertex = graph.insertVertex("Body");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        
        parents.push(vertex);
        visitDeclaration(entity);
        names.push("declaration");
        for (final Declaration decl : entity.getDecls()) {
            dispatch(decl);
        }
        names.pop();
        
        parents.pop();
    }
    
    public void visit(FunctionDecl entity) {
        final Object vertex = graph.insertVertex("Function Declaration");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        visitDeclaration(entity);
        
        names.push("return-type");
        dispatch(entity.getRetType());
        names.pop();
        
        final Object child = graph.insertVertex(entity.getName());
        graph.insertEdge(parents.peek(), child, "name");
        
        if (!entity.getTemplates().isEmpty()) {
            insertTerminal("<");
            names.push("template-parameter");
            for (final TemplateDecl templ : entity.getTemplates()) {
                dispatch(templ);
            }
            names.pop();
            insertTerminal(">");
        }
        
        names.push("parameter");
        insertTerminal("(");
        for (final ParameterDecl param : entity.getParams()) {
            dispatch(param);
        }
        insertTerminal(")");
        names.pop();
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        parents.pop();
    }
    
    public void visit(ParameterDecl entity) {
        final Object vertex = graph.insertVertex("Parameter");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        visitDeclaration(entity);
        
        names.push("value");
        dispatch(entity.getValue());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(TemplateDecl entity) {
        final Object vertex = graph.insertVertex("Template");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        visitDeclaration(entity);
        
        final Object child = graph.insertVertex(entity.getName());
        graph.insertEdge(parents.peek(), child, "name");
        names.push("bound");
        for (final Type type : entity.getBounds()) {
            dispatch(type);
        }
        names.pop();
        
        parents.pop();
    }

    
    public void visit(VariableDecl entity) {
        final Object vertex = graph.insertVertex("Variable Declaration");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        visitDeclaration(entity);
        
        names.push("type");
        dispatch(entity.getType());
        names.pop();
        insertTerminal(entity.getName(), "name");
        if (entity.getValue() != null) {
            insertTerminal("=");
            names.push("value");
            dispatch(entity.getValue());
            names.pop();
        }
        
        parents.pop();
    }
    
    public void visit(ArrayAccessExpression entity) {
        final Object vertex = graph.insertVertex("Array access");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("caller");
        dispatch(entity.getCaller());
        names.pop();
        
        insertTerminal("[");
        
        names.push("parameter");
        dispatch(entity.getParam());
        names.pop();
        
        insertTerminal("]");
        
        parents.pop();
    }

    
    public void visit(ArrayConstructorCall entity) {
        final Object vertex = graph.insertVertex("Array constructor");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        if (entity.isHeap()) {
            insertTerminal("new");
        }
        
        names.push("type");
        dispatch(entity.getType());
        names.pop();
        
        names.push("size");
        for (final Expression expr : entity.getSize().asList()) {
            insertTerminal("[");
            if (expr != null) {
                dispatch(expr);
            }
            insertTerminal("]");
        }
        names.pop();
        
        parents.pop();
    }

    
    public void visit(ArrayInitializer entity) {
        final Object vertex = graph.insertVertex("Initializer");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("{");
        names.push("value");
        for (final Entity value : entity.getValues()) {
            dispatch(value);
        }
        names.pop();
        insertTerminal("}");
        
        parents.pop();
    }

    
    public void visit(AssignmentExpression entity) {
        final Object vertex = graph.insertVertex("Assignment");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("lhs");
        dispatch(entity.getLhs());
        names.pop();
        insertTerminal("=");
        names.push("rhs");
        dispatch(entity.getRhs());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(AttributeAccess entity) {
        final Object vertex = graph.insertVertex("Attribute access");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("expression");
        dispatch(entity.getExpr());
        names.pop();
        insertTerminal(".");
        final Object child = graph.insertVertex(entity.getName());
        graph.insertEdge(parents.peek(), child, "attribute");
        
        parents.pop();
    }

    
    public void visit(BinaryExpression entity) {
        final Object vertex = graph.insertVertex("Binary Expression");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("lhs");
        dispatch(entity.getLhs());
        names.pop();
        insertTerminal(entity.getOperation());
        names.push("rhs");
        dispatch(entity.getRhs());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(CastExpression entity) {
        final Object vertex = graph.insertVertex("Cast Expression");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("(");
        names.push("type");
        dispatch(entity.getType());
        names.pop();
        insertTerminal(")");
        
        names.push("param");
        dispatch(entity.getParam());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(ConstructorCall entity) {
        final Object vertex = graph.insertVertex("Constructor Call");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        if (entity.isHeap()) {
            insertTerminal("new");
        }
        
        names.push("type");
        dispatch(entity.getType());
        names.pop();
        
        if (!entity.getTemplates().isEmpty()) {
            insertTerminal("<");
            names.push("template");
            for (final Type type : entity.getTemplates()) {
                dispatch(type);
            }
            names.pop();
            insertTerminal(">");
        }
        
        insertTerminal("(");
        names.push("expression");
        for (final Expression expr : entity.getParams()) {
            dispatch(expr);
        }
        names.pop();
        insertTerminal(")");
        
        parents.pop();
    }
    
    public void visit(ExpressionList entity) {
        final Object vertex = graph.insertVertex("Expression List");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("item");
        for (final Expression expr : entity.asList()) {
            dispatch(expr);
        }
        names.pop();
        
        parents.pop();
    }

    
    public void visit(FunctionCall entity) {
        final Object vertex = graph.insertVertex("Function Call");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        if (entity.getCaller() != null) {
            names.push("qualifier");
            dispatch(entity.getCaller());
            names.pop();
            insertTerminal(".");
        }
        
        final Object child = graph.insertVertex(entity.getName());
        graph.insertEdge(parents.peek(), child, "name");
        
        if (!entity.getTemplateParams().isEmpty()) {
            insertTerminal("<");
            names.push("template");
            for (final Type type : entity.getTemplateParams()) {
                dispatch(type);
            }
            names.pop();
            insertTerminal(">");
        }
        
        insertTerminal("(");
        names.push("expression");
        for (final Expression expr : entity.getParams()) {
            dispatch(expr);
        }
        names.pop();
        insertTerminal(")");
        
        parents.pop();
    }
    
    public void visit(StaticAttributeAccess entity) {
        final Object vertex = graph.insertVertex("Attribute Access");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("type");
        dispatch(entity.getQualifier());
        names.pop();
        insertTerminal(".");
        names.push("attribute");
        dispatch(entity.getAttribute());
        names.pop();
        parents.pop();
    }
    
    public void visit(TypeExpression entity) {
        dispatch(entity.getType());
    }
    
    public void visit(UnaryExpression entity) {
        final Object vertex = graph.insertVertex("Unary Expression");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal(entity.getOperation());
        
        names.push("expression");
        dispatch(entity.getOperand());
        names.pop();
        
        parents.pop();
    }
    
    public void visit(VariableReference entity) {
        final Object vertex = graph.insertVertex("Variable Reference");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        
        final Object child = graph.insertVertex(entity.getName());
        graph.insertEdge(vertex, child, "value");
    }
    
    public void visit(BooleanLiteral entity) {
        insertTerminal(entity.toString());
    }
    
    public void visit(CharLiteral entity) {
        insertTerminal(entity.toString());
    }
    
    public void visit(FloatLiteral entity) {
        insertTerminal(entity.toString());
    }
    
    public void visit(IntegerLiteral entity) {
        insertTerminal(entity.toString());
    }
    
    public void visit(SpecialLiteral entity) {
        insertTerminal(entity.toString());
    }
    
    public void visit(StringLiteral entity) {
        insertTerminal(entity.toString());
    }
    
    public void visit(BreakStatement entity) {
        insertTerminal("break");
    }
    
    public void visit(CatchStatement entity) {
        final Object vertex = graph.insertVertex("Catch");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("exception");
        dispatch(entity.getException());
        names.pop();
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(ContinueStatement entity) {
        insertTerminal("continue");
    }

    
    public void visit(DoWhileStatement entity) {
        final Object vertex = graph.insertVertex("Do While");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("do");
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        insertTerminal("while");
        insertTerminal("(");
        
        names.push("condition");
        dispatch(entity.getCondition());
        names.pop();
        
        insertTerminal(")");
        
        parents.pop();
    }

    
    public void visit(EmptyStatement entity) {
    }

    
    public void visit(ForEachStatement entity) {
        final Object vertex = graph.insertVertex("For");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("for");
        insertTerminal("(");
        
        names.push("initializer");
        dispatch(entity.getInit());
        names.pop();
        
        insertTerminal(":");
        
        names.push("range");
        dispatch(entity.getRange());
        names.pop();
        
        insertTerminal(")");
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(ForStatement entity) {
        final Object vertex = graph.insertVertex("For");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("for");
        insertTerminal("(");
        
        names.push("initializer");
        dispatch(entity.getInit());
        names.pop();
        
        insertTerminal(";");
        
        names.push("condition");
        dispatch(entity.getCondition());
        names.pop();
        
        insertTerminal(";");
        
        names.push("action");
        dispatch(entity.getAction());
        names.pop();
        
        insertTerminal(")");
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        parents.pop();
    }

    
    public void visit(IfStatement entity) {
        final Object vertex = graph.insertVertex("If");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("if");
        insertTerminal("(");
        
        names.push("condition");
        dispatch(entity.getCondition());
        names.pop();
        
        insertTerminal(")");
        
        names.push("if-branch");
        dispatch(entity.getIfer());
        names.pop();
        
        if (entity.getElser() != null) {
            names.push("else-branch");
            dispatch(entity.getElser());
            names.pop();
        }
        
        parents.pop();
    }
    
    public void visit(ReturnStatement entity) {
        final Object vertex = graph.insertVertex("Return");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("return");
        
        names.push("expression");
        dispatch(entity.getExpr());
        names.pop();
    }
   
    public void visit(StatementBlock entity) {
        final Object vertex = graph.insertVertex("Body");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        
        parents.push(vertex);
        names.push("statement");
        for (final Statement state : entity.getStatements()) {
            dispatch(state);
        }
        names.pop();
        
        parents.pop();
    }
    
    public void visit(StatementList entity) {
        final Object vertex = graph.insertVertex("Statement List");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("item");
        for (final Statement state : entity.asList()) {
            dispatch(state);
        }
        names.pop();
        
        parents.pop();
    }
    
    public void visit(SwitchStatement entity) {
        final Object vertex = graph.insertVertex("Switch");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("switch");
        insertTerminal("(");
        
        names.push("condition");
        dispatch(entity.getCondition());
        names.pop();
        
        insertTerminal(")");
        
        names.push("case");
        for (final Statement state : entity.getCases()) {
            dispatch(state);
        }
        names.pop();
        
        parents.pop();
    }
    
    public void visit(ThrowStatement entity) {
        final Object vertex = graph.insertVertex("Throw");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("throw");
        
        names.push("exception");
        dispatch(entity.getExpr());
        names.pop();
        
        parents.pop();
    }
    
    public void visit(TryStatement entity) {
        final Object vertex = graph.insertVertex("Try");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("try");
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        names.push("catch");
        for (final CatchStatement c : entity.getCatches()) {
            dispatch(c);
        }
        names.pop();
        
        if (entity.getFinallyBlock() != null) {
            insertTerminal("finally");
            names.push("finally");
            dispatch(entity.getFinallyBlock());
            names.pop();
        }
        
        parents.pop();
    }
    
    public void visit(VariableDeclStatement entity) {
        dispatch(entity.getVariable());
    }
    
    public void visit(WhileStatement entity) {
        final Object vertex = graph.insertVertex("While");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal("while");
        insertTerminal("(");
        
        names.push("condition");
        dispatch(entity.getCondition());
        names.pop();
        
        insertTerminal(")");
        
        names.push("body");
        dispatch(entity.getBody());
        names.pop();
        
        parents.pop();
    }

    public void visit(ArrayType entity) {
        final Object vertex = graph.insertVertex("Array Type");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        names.push("subtype");
        dispatch(entity.getType());
        names.pop();
        insertTerminal("[");
        insertTerminal("]");
        
        parents.pop();
    }
    
    public void visit(ClassType entity) {
        final Object vertex = graph.insertVertex("Class Type");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal(entity.getName());
        
        parents.pop();
    }
    
    public void visit(PrimitiveType entity) {
        final Object vertex = graph.insertVertex("Primitive Type");
        graph.insertEdge(parents.peek(), vertex, names.peek());
        parents.push(vertex);
        
        insertTerminal(entity.getName());
        parents.pop();
    }

    public void visit(Metamodel entity) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = graph.insertVertex("Metamodel");
            parents.push(vertex);
            names.push("");
            entity.accept(this);
        } finally {
            graph.getModel().endUpdate();
        }
        final mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
        layout.execute(graph.getDefaultParent());
    }
}
