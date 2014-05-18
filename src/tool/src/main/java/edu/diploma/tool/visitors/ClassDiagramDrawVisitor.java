/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.visitors;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxGraphLayout;
import com.mxgraph.layout.mxOrganicLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.mxPartitionLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.util.mxConstants;
import edu.diploma.metamodel.Entity;
import edu.diploma.metamodel.Metamodel;
import edu.diploma.metamodel.TranslationUnit;
import edu.diploma.metamodel.declarations.AnnotationDecl;
import edu.diploma.metamodel.declarations.ClassDecl;
import edu.diploma.metamodel.declarations.DeclBody;
import edu.diploma.metamodel.declarations.Declaration;
import edu.diploma.metamodel.declarations.EnumDecl;
import edu.diploma.metamodel.declarations.FunctionDecl;
import edu.diploma.metamodel.declarations.TemplateDecl;
import edu.diploma.metamodel.declarations.VariableDecl;
import edu.diploma.metamodel.types.ArrayType;
import edu.diploma.metamodel.types.ClassType;
import edu.diploma.metamodel.types.Type;
import edu.diploma.tool.graph.Graph;
import edu.diploma.tool.util.UmlClass;
import edu.diploma.util.Pair;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexander
 */
public class ClassDiagramDrawVisitor extends DrawVisitor {
    private static final String ASSOCIATION_STYLE = "defaultEdge;" + mxConstants.STYLE_ENDARROW + "=" + mxConstants.ARROW_OPEN;
    private static final String AGGREGATION_STYLE = "defaultEdge;" + mxConstants.STYLE_ENDARROW + "=" + mxConstants.ARROW_DIAMOND;
    private static final String INHERITANCE_STYLE = "defaultEdge;" + mxConstants.STYLE_ENDARROW + "=" + mxConstants.ARROW_CLASSIC;
    
    private final Map<String, Pair<UmlClass, Object>> map = new HashMap<>();
    private UmlClass currentClass;

    public ClassDiagramDrawVisitor() {
        super(new Graph());
    }
    
    @Override
    public void navigate(Entity entity) {
    }
    
    public void visit(final Metamodel entity) {
        entity.accept(this);
        final mxGraphLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
    }
    
    public void visit(final TranslationUnit entity) {
        for (final Declaration decl : entity.getTypes()) {
            dispatch(decl);
        }
        enrich();
    }
    
    public void visit(final ClassDecl entity) {
        graph.getModel().beginUpdate();
        try {
            currentClass = new UmlClass(entity.getName(), entity.getInherits());
            dispatch(entity.getBody());
            final Object vertex = graph.insertVertex(currentClass.toString());
            map.put(entity.getName(), new Pair<>(currentClass, vertex));
            currentClass = null;
        } finally {
            graph.getModel().endUpdate();
        }
    }
    
    public void visit(final DeclBody entity) {
        for (final Declaration decl : entity.getDecls()) {
            dispatch(decl);
        }
    }
    
    public void visit(final VariableDecl entity) {
        if (currentClass != null) {
            currentClass.addVariable(entity);
        }
    }
    public void visit(final FunctionDecl entity) {
        if (currentClass != null) {
            currentClass.addMethod(entity);
        }
    }
    public void visit(final EnumDecl entity) {}
    public void visit(final AnnotationDecl entity) {}
    public void visit(final TemplateDecl entity) {}
    
    private void enrich() {
        for (final Pair<UmlClass, Object> pair : map.values()) {
            for (final VariableDecl var : pair.first.getVars()) {
                parseAssocType(var.getType(), pair.second, ASSOCIATION_STYLE);
            }
            for (final Type type : pair.first.getInherits()) {
                parseInheritance(type, pair.second);
            }
        }
    }
    
    private void insertAssoc(final Object from, final Object to, final String style) {
        graph.getModel().beginUpdate();
        try {
            graph.insertEdge(from, to, "", style);
        } finally {
            graph.getModel().endUpdate();
        }
    }
    
    private void parseAssocType(final Type type, final Object source, final String style) {
        if (type instanceof ClassType) {
            final ClassType t = (ClassType) type;
            if (map.containsKey(t.getName())) {
                insertAssoc(source, map.get(t.getName()).second, style);
            }
        } else if (type instanceof ArrayType) {
            final ArrayType t = (ArrayType) type;
            parseAssocType(t.getType(), source, AGGREGATION_STYLE);
        }
    }
    
    private void parseInheritance(final Type type, final Object source) {
        if (type instanceof ClassType) {
            final ClassType t = (ClassType) type;
            if (map.containsKey(t.getName())) {
                graph.insertEdge(source, map.get(t.getName()).second, "", INHERITANCE_STYLE);
            }
        }
    }
}
