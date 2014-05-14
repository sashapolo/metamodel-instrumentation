/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.diploma.tool.visitors;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
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
import edu.diploma.tool.util.JGraphUtils;
import edu.diploma.util.Stringifier;
import edu.diploma.visitors.VisitorAdapter;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author alexander
 */
public class CfgDrawVisitor extends VisitorAdapter {

    private final JComponent drawBoard;
    private final mxGraph graph;
    private Object parentVertex;
    private Object lastVertex;
    
    private Object[] toggleSubtree(final mxGraph graph, final Object cell, boolean show) {
        final List<Object> cells = new LinkedList<>();

        graph.traverse(cell, true, new mxGraph.mxICellVisitor() {
            @Override
            public boolean visit(Object vertex, Object edge) {
                if (!vertex.equals(cell)) {
                    cells.add(vertex);
                    return !graph.isCellCollapsed(vertex);
                }
                return true;
            }
        });

        final Object[] result = cells.toArray();
        graph.toggleCells(show, result, true);
        return result;
    }

    private Object insertDefaultVertex(final Entity entity) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = JGraphUtils.createVertex(graph, entity.toString());
            graph.addCell(vertex);
            JGraphUtils.insertEdge(graph, parentVertex, vertex);
            parentVertex = vertex;
            return vertex;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    private void createCycle(final String label, final Entity body) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = JGraphUtils.createVertex(graph, label);
            graph.addCell(vertex);
            JGraphUtils.insertEdge(graph, parentVertex, vertex);

            final Object exit = JGraphUtils.createEmptyVertex(graph);
            graph.addCell(exit);
            
            lastVertex = vertex;
            parentVertex = vertex;

            dispatch(body);
            JGraphUtils.insertEdge(graph, lastVertex, exit);
            JGraphUtils.insertEdge(graph, exit, vertex);
            JGraphUtils.insertEdge(graph, vertex, exit);
            parentVertex = exit;
            lastVertex = exit;
        } finally {
            graph.getModel().endUpdate();
        }
    }
    
    private void drawTryStatement(final Entity body, final List<CatchStatement> catches, 
            final Entity finallyBlock, final String label) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = JGraphUtils.createVertex(graph, label);
            graph.addCell(vertex);
            JGraphUtils.insertEdge(graph, parentVertex, vertex);
            parentVertex = vertex;

            final Object exit = JGraphUtils.createEmptyVertex(graph);
            graph.addCell(exit);

            lastVertex = null;
            dispatch(body);
            if (lastVertex != null) {
                JGraphUtils.insertEdge(graph, lastVertex, exit);
            }

            for (final CatchStatement state : catches) {
                lastVertex = null;
                parentVertex = vertex;
                dispatch(state);
                if (lastVertex != null) {
                    JGraphUtils.insertEdge(graph, lastVertex, exit);
                }
            }

            if (finallyBlock != null) {
                final Object fin = JGraphUtils.createVertex(graph, "finally");
                graph.addCell(fin);
                JGraphUtils.insertEdge(graph, vertex, fin);

                parentVertex = fin;
                lastVertex = null;
                dispatch(finallyBlock);
                if (lastVertex != null) {
                    JGraphUtils.insertEdge(graph, lastVertex, exit);
                }
            }

            parentVertex = exit;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public CfgDrawVisitor(final JComponent parent) {
        this.drawBoard = parent;
        this.graph = new mxGraph() {
            @Override
            public boolean isCellFoldable(Object o, boolean bln) {
                return getOutgoingEdges(o).length > 0;
            }

            @Override
            public Object[] foldCells(boolean collapse, boolean recurse, Object[] cells) {
                model.beginUpdate();
                try {
                    for (final Object cell : cells) {
                        toggleSubtree(this, cell, !collapse);
                        model.setCollapsed(cell, collapse);
                    }
                    return null;
                } finally {
                    model.endUpdate();
                }
            }
        };

        graph.setAllowLoops(true);
        graph.setCellsMovable(true);
        graph.setCellsResizable(false);
        graph.setCellsEditable(false);
    }

    @Override
    public void navigate(Entity entity) {
    }

    public void visit(TranslationUnit entity) {
        graph.getModel().beginUpdate();
        try {
            for (final Declaration decl : entity.getTypes()) {
                parentVertex = null;
                dispatch(decl);
            }
            JGraphUtils.removeOrphanes(graph);
        } finally {
            graph.getModel().endUpdate();
        }

        final mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);

        drawBoard.add(graphComponent);
        drawBoard.revalidate();
    }

    public void visit(ClassDecl entity) {
        final Object vertex = JGraphUtils.createVertex(graph, entity.toString());
        graph.addCell(vertex);
        if (parentVertex != null) {
            JGraphUtils.insertEdge(graph, parentVertex, vertex);
        } 
        parentVertex = vertex;
        visit(entity.getBody());
    }

    public void visit(final VariableDecl entity) {
        final Object t = parentVertex;
        lastVertex = insertDefaultVertex(entity);
        parentVertex = t;
    }

    public void visit(DeclBody entity) {
        entity.accept(this);
    }

    public void visit(FunctionDecl entity) {
        final Object t = parentVertex;
        lastVertex = insertDefaultVertex(entity);
        visit(entity.getBody());
        parentVertex = t;
    }

    public void visit(DoWhileStatement entity) {
        graph.getModel().beginUpdate();
        try {
            final Object start = parentVertex;
            dispatch(entity.getBody());
            final mxICell edge = (mxICell) graph.getOutgoingEdges(start)[0];
            final Object returnPoint = edge.getTerminal(false);

            final Object vertex = JGraphUtils.createVertex(graph, "while (" + entity.getCondition() + ")");
            graph.addCell(vertex);
            graph.insertEdge(graph.getDefaultParent(), null, "", parentVertex, vertex);
            graph.insertEdge(graph.getDefaultParent(), null, "", vertex, returnPoint);
            parentVertex = vertex;
            lastVertex = vertex;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public void visit(IfStatement entity) {
        graph.getModel().beginUpdate();
        try {
            final Object vertex = JGraphUtils.createVertex(graph, "if (" + entity.getCondition() + ")");
            graph.addCell(vertex);
            JGraphUtils.insertEdge(graph, parentVertex, vertex);
            parentVertex = vertex;

            final Object exit = JGraphUtils.createEmptyVertex(graph);
            graph.addCell(exit);

            lastVertex = null;
            dispatch(entity.getIfer());
            if (lastVertex != null) {
                JGraphUtils.insertEdge(graph, lastVertex, exit);
            }

            parentVertex = vertex;

            if (entity.getElser() != null) {
                lastVertex = null;
                dispatch(entity.getElser());
                if (lastVertex != null) {
                    JGraphUtils.insertEdge(graph, lastVertex, exit);
                }
            } else {
                JGraphUtils.insertEdge(graph, vertex, exit);
            }     

            parentVertex = exit;
            lastVertex = exit;
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
            final Object vertex = JGraphUtils.createVertex(graph, label);
            graph.addCell(vertex);
            JGraphUtils.insertEdge(graph, parentVertex, vertex);
            parentVertex = vertex;

            final Object exit = JGraphUtils.createEmptyVertex(graph);
            graph.addCell(exit);

            for (final SwitchStatement.Label state : entity.getCases()) {
                final Object c = JGraphUtils.createVertex(graph, "case: " + state.getExpr());
                graph.addCell(vertex);
                JGraphUtils.insertEdge(graph, parentVertex, c);
                lastVertex = null;
                dispatch(state.getStates());
                if (lastVertex == null) {
                    parentVertex = c;
                } else {
                    parentVertex = vertex;
                    JGraphUtils.insertEdge(graph, lastVertex, exit);
                }
            }

            parentVertex = exit;
            lastVertex = exit;
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public void visit(final TryStatement entity) {
        drawTryStatement(entity.getBody(), entity.getCatches(), entity.getFinallyBlock(), "try");
    }
    
    public void visit (final TryWithResourcesStatement entity) {
        final String label = "try (" + Stringifier.toString(entity.getResources()) + ")";
        drawTryStatement(entity.getBody(), entity.getCatches(), entity.getFinallyBlock(), label);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // default stuff
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void visit(BinaryExpression entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(VariableDeclStatement entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(ReturnStatement entity) {
        insertDefaultVertex(entity);
        parentVertex = null;
        lastVertex = null;
    }

    public void visit(FunctionCall entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final BreakStatement entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final ContinueStatement entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final CatchStatement entity) {
        lastVertex = insertDefaultVertex(entity);
        dispatch(entity.getBody());
    }

    public void visit(final EmptyStatement entity) {
    }

    public void visit(final LabelStatement entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final ThrowStatement entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final ArrayAccessExpression entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final ArrayConstructorCall entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final ArrayInitializer entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final AssignmentExpression entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final AttributeAccess entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final CastExpression entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final ConstructorCall entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final StaticAttributeAccess entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final TernaryExpression entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final TypeExpression entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final UnaryExpression entity) {
        lastVertex = insertDefaultVertex(entity);
    }

    public void visit(final VariableReference entity) {
        lastVertex = insertDefaultVertex(entity);
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
    
    public void visit(final Metamodel entity) {
        entity.accept(this);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public mxGraph getGraph() {
        return graph;
    }

}
