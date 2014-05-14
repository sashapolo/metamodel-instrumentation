/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.util;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexander
 */
public class JGraphUtils {
    public static enum Shape {
        DEFAULT,
        ELLIPSE(mxConstants.SHAPE_ELLIPSE),
        RHOMBUS(mxConstants.SHAPE_RHOMBUS);
        
        Shape() {
            this.shape = "defaultVertex";
        }
        Shape(final String shape) {
            this.shape = "defaultVertex;" + mxConstants.STYLE_SHAPE + "=" + shape;
        }
        
        private final String shape;

        @Override
        public String toString() {
            return shape;
        }
    }
    
    public static class Options {
        private final Map<String, String> values = new HashMap<>();
        
        public void add(final String key, final String value) {
            values.put(key, value);
        }

        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder("default;");
            for (final Map.Entry<String, String> entry : values.entrySet()) {
                result.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
            return result.toString();
        }
    }
    
    private static mxRectangle getVertexSize(final mxGraph graph, final String label, final Shape shape) {
        final Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();
        final mxRectangle size = mxUtils.getLabelSize(label, style, false, 1);
        final mxRectangle result = new mxRectangle();
        switch (shape) {
            case DEFAULT:
                result.setHeight(size.getHeight() + 20);
                result.setWidth(size.getWidth() + 20);
                break;
            case RHOMBUS:
                result.setHeight(size.getHeight() + 60);
                result.setWidth(size.getWidth() + 60);
                break;
            case ELLIPSE:
                result.setHeight(size.getHeight() + 20);
                result.setWidth(size.getWidth() + 60);
                break;
        }
        return result;
    }
    
    public static Object createVertex(final mxGraph graph, final String label) {
        return createVertex(graph, label, Shape.DEFAULT);
    }
    
    public static Object createVertex(final mxGraph graph, final String label, final Options options) {
        final mxRectangle size = getVertexSize(graph, label, Shape.DEFAULT);
        return graph.createVertex(graph.getDefaultParent(), null, label, 0, 0, 
                                  size.getWidth(), size.getHeight(), options.toString());
    }
    
    public static Object createVertex(final mxGraph graph, final String label, final Shape shape) {
        final mxRectangle size = getVertexSize(graph, label, shape);
        return graph.createVertex(graph.getDefaultParent(), null, label, 0, 0, 
                                  size.getWidth(), size.getHeight(), shape.toString());
    }
    
    public static Object createEmptyVertex(final mxGraph graph) {
        final Object result = graph.createVertex(graph.getDefaultParent(), null, "", 0, 0, 20, 20, Shape.ELLIPSE.toString());
        return result;
    }
    public static Object createEmptyVertex(final mxGraph graph, final Options options) {
        options.add(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        final Object result = graph.createVertex(graph.getDefaultParent(), null, "", 0, 0, 20, 20, options.toString());
        return result;
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
