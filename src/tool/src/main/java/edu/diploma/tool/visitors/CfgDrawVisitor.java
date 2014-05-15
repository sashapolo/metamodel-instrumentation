/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.diploma.tool.visitors;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxStackLayout;
import com.mxgraph.model.mxICell;
import edu.diploma.metamodel.Entity;
import edu.diploma.metamodel.Metamodel;
import edu.diploma.metamodel.TranslationUnit;
import edu.diploma.metamodel.declarations.ClassDecl;
import edu.diploma.metamodel.declarations.DeclBody;
import edu.diploma.metamodel.declarations.Declaration;
import edu.diploma.metamodel.declarations.FunctionDecl;
import edu.diploma.metamodel.declarations.VariableDecl;
import edu.diploma.metamodel.expressions.ArrayAccessExpression;
import edu.diploma.metamodel.expressions.ArrayConstructorCall;
import edu.diploma.metamodel.expressions.ArrayInitializer;
import edu.diploma.metamodel.expressions.AssignmentExpression;
import edu.diploma.metamodel.expressions.AttributeAccess;
import edu.diploma.metamodel.expressions.BinaryExpression;
import edu.diploma.metamodel.expressions.CastExpression;
import edu.diploma.metamodel.expressions.ConstructorCall;
import edu.diploma.metamodel.expressions.ExpressionList;
import edu.diploma.metamodel.expressions.FunctionCall;
import edu.diploma.metamodel.expressions.StaticAttributeAccess;
import edu.diploma.metamodel.expressions.TernaryExpression;
import edu.diploma.metamodel.expressions.TypeExpression;
import edu.diploma.metamodel.expressions.UnaryExpression;
import edu.diploma.metamodel.expressions.VariableReference;
import edu.diploma.metamodel.statements.BreakStatement;
import edu.diploma.metamodel.statements.CatchStatement;
import edu.diploma.metamodel.statements.ContinueStatement;
import edu.diploma.metamodel.statements.DoWhileStatement;
import edu.diploma.metamodel.statements.EmptyStatement;
import edu.diploma.metamodel.statements.ForEachStatement;
import edu.diploma.metamodel.statements.ForStatement;
import edu.diploma.metamodel.statements.IfStatement;
import edu.diploma.metamodel.statements.LabelStatement;
import edu.diploma.metamodel.statements.ReturnStatement;
import edu.diploma.metamodel.statements.StatementBlock;
import edu.diploma.metamodel.statements.StatementList;
import edu.diploma.metamodel.statements.SwitchStatement;
import edu.diploma.metamodel.statements.ThrowStatement;
import edu.diploma.metamodel.statements.TryStatement;
import edu.diploma.metamodel.statements.TryWithResourcesStatement;
import edu.diploma.metamodel.statements.VariableDeclStatement;
import edu.diploma.metamodel.statements.WhileStatement;
import edu.diploma.util.Stringifier;
import java.util.List;

/**
 *
 * @author alexander
 */
public class CfgDrawVisitor extends DrawVisitor {
    private Object parent;

    private Object insertVertex(final Entity entity) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = graph.insertVertex(entity.toString());
            if (parent != null) {
                graph.insertEdge(parent, vertex);
            }
            return vertex;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    private void createCycle(final String label, final Entity body) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = graph.insertVertex(label);
            final Object exit = graph.insertEmptyVertex();
            graph.insertEdge(parent, vertex);

            parent = vertex;

            dispatch(body);
            if (parent != vertex) {
                graph.insertEdge(parent, exit);
            }
            graph.insertEdge(exit, vertex);
            graph.insertEdge(vertex, exit);

            parent = exit;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    private void drawTryStatement(final Entity body, final List<CatchStatement> catches,
            final Entity finallyBlock, final String label) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = graph.insertVertex(label);
            final Object exit = graph.insertEmptyVertex();
            graph.insertEdge(parent, vertex);

            parent = vertex;
            dispatch(body);
            if (parent != vertex) {
                graph.insertEdge(parent, exit);
            }

            for (final CatchStatement state : catches) {
                parent = vertex;
                dispatch(state);
                if (parent != vertex) {
                    graph.insertEdge(parent, exit);
                }
            }

            if (finallyBlock != null) {
                final Object fin = graph.insertVertex("finally");
                graph.insertEdge(vertex, fin);

                parent = fin;
                dispatch(finallyBlock);
                if (parent != fin) {
                    graph.insertEdge(parent, exit);
                }
            }

            parent = exit;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public CfgDrawVisitor() {
        graph.setAllowLoops(true);
        graph.setCellsMovable(true);
        graph.setCellsResizable(false);
        graph.setCellsEditable(false);
    }

    @Override
    public void navigate(Entity entity) {
    }

    public void visit(final Metamodel entity) {
        entity.accept(this);
        
        final mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
    }
    
    public void visit(TranslationUnit entity) {
        graph.getModel().beginUpdate();
        try {
            for (final Declaration decl : entity.getTypes()) {
                parent = null;
                dispatch(decl);
            }
            graph.removeOrphans();
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public void visit(ClassDecl entity) {
        parent = insertVertex(entity);
        visit(entity.getBody());
    }

    public void visit(final VariableDecl entity) {
        insertVertex(entity);
    }

    public void visit(DeclBody entity) {
        entity.accept(this);
    }

    public void visit(FunctionDecl entity) {
        final Object t = parent;
        parent = insertVertex(entity);
        visit(entity.getBody());
        parent = t;
    }

    public void visit(DoWhileStatement entity) {
        graph.getModel().beginUpdate();
        try {
            final Object start = parent;
            dispatch(entity.getBody());
            final mxICell edge = (mxICell) graph.getOutgoingEdges(start)[0];
            final Object returnPoint = edge.getTerminal(false);

            final Object vertex = graph.insertVertex("while (" + entity.getCondition() + ")");
            graph.insertEdge(graph.getDefaultParent(), null, "", parent, vertex);
            graph.insertEdge(graph.getDefaultParent(), null, "", vertex, returnPoint);
            parent = vertex;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public void visit(IfStatement entity) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = graph.insertVertex("if (" + entity.getCondition() + ")");
            final Object exit = graph.insertEmptyVertex();
            graph.insertEdge(parent, vertex);

            parent = vertex;
            dispatch(entity.getIfer());
            if (parent != vertex) {
                graph.insertEdge(parent, exit);
            }

            if (entity.getElser() != null) {
                parent = vertex;
                dispatch(entity.getElser());
                if (parent != vertex) {
                    graph.insertEdge(parent, exit);
                }
            } else {
                graph.insertEdge(vertex, exit);
            }

            parent = exit;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public void visit(final ForEachStatement entity) {
        final String label = "for (" + entity.getInit().toString() + " : " + entity.getRange().toString() + ")";
        createCycle(label, entity.getBody());
    }

    public void visit(final ForStatement entity) {
        final StringBuilder label = new StringBuilder("for (");
        label.append(entity.getInit().toString()).append("; ");
        label.append(entity.getCondition().toString()).append("; ");
        label.append(entity.getAction().toString()).append(")");
        createCycle(label.toString(), entity.getBody());
    }

    public void visit(WhileStatement entity) {
        final String label = "while (" + entity.getCondition() + ")";
        createCycle(label, entity.getBody());
    }

    public void visit(final SwitchStatement entity) {
        graph.getModel().beginUpdate();
        try {
            final String label = "switch (" + entity.getCondition() + ")";
            final Object vertex = graph.insertVertex(label);
            final Object exit = graph.insertEmptyVertex();
            graph.insertEdge(parent, vertex);
            parent = vertex;

            for (final SwitchStatement.Label state : entity.getCases()) {
                final String caseLabel = state.getExpr() == null ? "default:" : "case " + state.getExpr() + ":";
                final Object c = graph.insertVertex(caseLabel);
                graph.insertEdge(parent, c);
                parent = c;
                dispatch(state.getStates());
                if (parent != c) {
                    graph.insertEdge(parent, exit);
                    parent = vertex;
                }
            }

            parent = exit;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public void visit(final TryStatement entity) {
        drawTryStatement(entity.getBody(), entity.getCatches(), entity.getFinallyBlock(), "try");
    }

    public void visit(final TryWithResourcesStatement entity) {
        final String label = "try (" + Stringifier.toString(entity.getResources()) + ")";
        drawTryStatement(entity.getBody(), entity.getCatches(), entity.getFinallyBlock(), label);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // default stuff
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void visit(BinaryExpression entity) {
        parent = insertVertex(entity);
    }

    public void visit(VariableDeclStatement entity) {
        parent = insertVertex(entity);
    }

    public void visit(ReturnStatement entity) {
        insertVertex(entity);
        parent = null;
    }

    public void visit(FunctionCall entity) {
        parent = insertVertex(entity);
    }

    public void visit(final BreakStatement entity) {
        parent = insertVertex(entity);
    }

    public void visit(final ContinueStatement entity) {
        parent = insertVertex(entity);
    }

    public void visit(final CatchStatement entity) {
        parent = insertVertex(entity);
        dispatch(entity.getBody());
    }

    public void visit(final EmptyStatement entity) {
    }

    public void visit(final LabelStatement entity) {
        parent = insertVertex(entity);
    }

    public void visit(final ThrowStatement entity) {
        parent = insertVertex(entity);
    }

    public void visit(final ArrayAccessExpression entity) {
        parent = insertVertex(entity);
    }

    public void visit(final ArrayConstructorCall entity) {
        parent = insertVertex(entity);
    }

    public void visit(final ArrayInitializer entity) {
        parent = insertVertex(entity);
    }

    public void visit(final AssignmentExpression entity) {
        parent = insertVertex(entity);
    }

    public void visit(final AttributeAccess entity) {
        parent = insertVertex(entity);
    }

    public void visit(final CastExpression entity) {
        parent = insertVertex(entity);
    }

    public void visit(final ConstructorCall entity) {
        parent = insertVertex(entity);
    }

    public void visit(final StaticAttributeAccess entity) {
        parent = insertVertex(entity);
    }

    public void visit(final TernaryExpression entity) {
        parent = insertVertex(entity);
    }

    public void visit(final TypeExpression entity) {
        parent = insertVertex(entity);
    }

    public void visit(final UnaryExpression entity) {
        parent = insertVertex(entity);
    }

    public void visit(final VariableReference entity) {
        parent = insertVertex(entity);
    }

    public void visit(final StatementList entity) {
        entity.accept(this);
    }

    public void visit(StatementBlock entity) {
        entity.accept(this);
    }

    public void visit(final ExpressionList entity) {
        entity.accept(this);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
