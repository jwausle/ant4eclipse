/**********************************************************************
 * Copyright (c) 2005-2008 ant4eclipse project team.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nils Hartmann, Daniel Kasmeroglu, Gerd Wuetherich
 **********************************************************************/
package org.ant4eclipse.core.ant;

import org.ant4eclipse.core.Ant4EclipseConfigurator;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.DataType;

import java.util.HashSet;
import java.util.Set;

/**
 * Base type for all ant4eclipse types.
 * 
 * <p>
 * Used to configure ant4eclipse runtime environment if necessary
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractAnt4EclipseDataType extends DataType {

  /** - */
  private static Set<AbstractAnt4EclipseDataType> instances     = new HashSet<AbstractAnt4EclipseDataType>();

  /** - */
  private static Object                           instancesLock = new Object();

  /**
   * <p>
   * Creates a new instance of type AbstractAnt4EclipseDataType.
   * </p>
   * 
   * @param project
   */
  public AbstractAnt4EclipseDataType(final Project project) {
    setProject(project);

    // configure ant4eclipse
    Ant4EclipseConfigurator.configureAnt4Eclipse(project);

    // add to instances map
    synchronized (instancesLock) {
      instances.add(this);
    }
  }

  /**
   * <p>
   * Override this method to validate an instance of Ant4EclipseDataType
   * </p>
   */
  protected void validate() {
    // empty default implementation
  }

  /**
   * <p>
   * Validates all registered AbstractAnt4EclipseDataTypes
   * </p>
   */
  static void validateAll() {

    // add to instances map
    synchronized (instancesLock) {

      // iterate over the registered AbstractAnt4EclipseDataTypes
      for (AbstractAnt4EclipseDataType dataType : instances) {
        dataType.validate();
      }
    }
  }
}
