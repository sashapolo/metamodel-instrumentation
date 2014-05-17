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
        graph = new Graph();
        graph.setAllowLoops(true);
        graph.setCellsMovable(true);
        graph.setCellsResizable(false);
        graph.setCellsEditable(false);
    }
    
    public mxGraph getGraph() {
        return graph;
    }
}
