package edu.diploma.metamodel;

import edu.diploma.visitors.Visitor;
import org.simpleframework.xml.Default;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 12/2/13
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */

@Default
public interface Entity {
    public void accept(final Visitor visitor);
}
