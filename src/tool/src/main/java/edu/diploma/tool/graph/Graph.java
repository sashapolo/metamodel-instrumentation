/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.graph;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexander
 */
public class Graph extends mxGraph {
    
    public Graph() {
        final Map<String, Object> vertexStyle = getStylesheet().getDefaultVertexStyle();
        vertexStyle.put(mxConstants.STYLE_GRADIENTCOLOR, "white");
        final Map<String, Object> edgeStyle = getStylesheet().getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_EDGE, mxEdgeStyle.EntityRelation);
    }
    
    public static Graph createRoundedGraph() {
        Graph result = new Graph();
        final Map<String, Object> vertexStyle = result.getStylesheet().getDefaultVertexStyle();
        vertexStyle.put(mxConstants.STYLE_ROUNDED, true);
        final Map<String, Object> edgeStyle = result.getStylesheet().getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_ROUNDED, true);
        return result;
    }
    
    private Object[] toggleSubtree(final mxGraph graph, final Object cell, boolean show) {
        final List<Object> cells = new LinkedList<>();

        traverse(cell, true, new mxGraph.mxICellVisitor() {
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
    
    private mxRectangle getVertexSize(final String label) {
        final Map<String, Object> style = getStylesheet().getDefaultVertexStyle();
        final mxRectangle size = mxUtils.getLabelSize(label, style, false, 1);
        final mxRectangle result = new mxRectangle();
        result.setHeight(size.getHeight() + 20);
        result.setWidth(size.getWidth() + 20);
        return result;
    }
    
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
    
    public Object createVertex(final String label) {
        final mxRectangle size = getVertexSize(label);
        return createVertex(getDefaultParent(), null, label, 0, 0, size.getWidth(), size.getHeight(), null);
    }
    public Object createEmptyVertex() {
        return createVertex(getDefaultParent(), null, "", 0, 0, 20, 20, null);
    }
    
    public Object insertVertex(final String label) {
        final Object vertex = createVertex(label);
        return addCell(vertex);
    }
    public Object insertEmptyVertex() {
        final Object vertex = createEmptyVertex();
        return addCell(vertex);
    }
    
    public Object insertEdge(final Object source, final Object dest) {
        return insertEdge(source, dest, "", null);
    }
    public Object insertEdge(final Object source, final Object dest, final String label) {
        return insertEdge(source, dest, label, null);
    }
    public Object insertEdge(final Object source, final Object dest, final String label, final String style) {
        return insertEdge(getDefaultParent(), null, label, source, dest, style);
    }
    
    public int removeOrphans() {
        final Object[] vertices = getChildVertices(getDefaultParent());
        final List<Object> remove = new LinkedList<>();
        for (final Object vertex : vertices) {
            if (getConnections(vertex).length == 0) {
                remove.add(vertex);
            }
        }
        removeCells(remove.toArray());
        return remove.size();
    }
}
