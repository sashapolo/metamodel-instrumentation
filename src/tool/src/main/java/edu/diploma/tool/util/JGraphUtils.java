/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.util;

import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexander
 */
public class JGraphUtils {
    private final static int VERTEX_OFFSET = 20;
    
    public static Object createVertex(final mxGraph graph, final String label) {
        final Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();
        final mxRectangle size = mxUtils.getLabelSize(label, style, false, 1);
        return graph.createVertex(graph.getDefaultParent(), null, label, 0, 0, 
                                  size.getWidth() + VERTEX_OFFSET, 
                                  size.getHeight() + VERTEX_OFFSET, 
                                  null);
    }
    
    public static Object createEmptyVertex(final mxGraph graph) {
        final Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();
        return graph.createVertex(graph.getDefaultParent(), null, "", 0, 0, 20, 20, null);
    }
    
    public static void insertEdge(final mxGraph graph, final Object source, final Object dest) {
        graph.insertEdge(graph.getDefaultParent(), null, "", source, dest);
    }
    
    public static int removeOrphanes(final mxGraph graph) {
        final Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
        final List<Object> remove = new LinkedList<>();
        for (final Object vertex : vertices) {
            if (graph.getConnections(vertex).length == 0) {
                remove.add(vertex);
            }
        }
        graph.removeCells(remove.toArray());
        return remove.size();
    }

    private JGraphUtils() {
    }
}
