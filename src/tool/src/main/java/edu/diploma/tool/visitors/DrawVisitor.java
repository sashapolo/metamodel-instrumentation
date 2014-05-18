/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.visitors;

import com.mxgraph.view.mxGraph;
import edu.diploma.tool.graph.Graph;
import edu.diploma.visitors.VisitorAdapter;

/**
 *
 * @author alexander
 */
public class DrawVisitor extends VisitorAdapter {
    protected final Graph graph;
    
    public DrawVisitor() {
        graph = Graph.createRoundedGraph();
        graph.setAllowLoops(true);
        graph.setCellsMovable(true);
        graph.setCellsResizable(false);
        graph.setCellsEditable(false);
    }
    public DrawVisitor(final Graph graph) {
        this.graph = graph;
        this.graph.setAllowLoops(true);
        this.graph.setCellsMovable(true);
        this.graph.setCellsResizable(false);
        this.graph.setCellsEditable(false);
    }
    
    public mxGraph getGraph() {
        return graph;
    }
}
