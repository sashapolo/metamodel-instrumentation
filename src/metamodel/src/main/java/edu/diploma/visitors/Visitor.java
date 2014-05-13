/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.visitors;

import edu.diploma.metamodel.Entity;

/**
 *
 * @author alexander
 */
public interface Visitor {
    public void dispatch(final Entity entity);
    public void navigate(final Entity entity);
}
