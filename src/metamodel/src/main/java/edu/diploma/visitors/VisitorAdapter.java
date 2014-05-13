/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.diploma.visitors;

import edu.diploma.metamodel.Entity;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexander
 */
public class VisitorAdapter implements Visitor {
    @Override
    public void dispatch(final Entity entity) {
        if (entity == null) return;
        try {
            final Method m = getClass().getMethod("visit", new Class[] { entity.getClass() });
            m.invoke(this, new Object[] { entity });
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(VisitorAdapter.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        navigate(entity);
    }

    @Override
    public void navigate(Entity entity) {
        entity.accept(this);
    }
}
