package org.ant4eclipse.core.ant;

import org.ant4eclipse.core.Ant4EclipseConfigurator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.ProjectComponent;
import org.apache.tools.ant.taskdefs.condition.Condition;

/**
 * <p>
 * Abstract base class for all ant4eclipse conditions.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractAnt4EclipseCondition extends ProjectComponent implements Condition {

  /**
   * @see org.apache.tools.ant.taskdefs.condition.Condition#eval()
   */
  public final boolean eval() throws BuildException {
    // configure ant4eclipse
    Ant4EclipseConfigurator.configureAnt4Eclipse(getProject());

    AbstractAnt4EclipseDataType.validateAll();

    // delegate the implementation
    try {
      return doEval();
    } catch (Exception ex) {
      throw new BuildException(ex.toString(), ex);
    }
  }

  /**
   * <p>
   * This method replaces the original <code>eval()</code> method defined in
   * <code>org.apache.tools.ant.taskdefs.condition.Condition</code>. Overwrite this method to implement own condition
   * logic.
   * </p>
   * 
   * @see Task#execute()
   */
  protected abstract boolean doEval();
}
