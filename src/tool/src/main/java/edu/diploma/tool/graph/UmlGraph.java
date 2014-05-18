/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.tool.graph;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import edu.diploma.tool.util.UmlClass;

/**
 *
 * @author alexander
 */
public class UmlGraph extends Graph {

    @Override
    public String convertValueToString(Object cell) {
        if (cell instanceof mxCell) {
            Object value = ((mxICell) cell).getValue();

            if (value instanceof UmlClass) {
                return value.toString();
            }
        }

        return super.convertValueToString(cell);
    }
    
}
