/**********************************************************************
 * Copyright (c) 2005-2009 ant4eclipse project team.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Nils Hartmann, Daniel Kasmeroglu, Gerd Wuetherich
 **********************************************************************/
package org.ant4eclipse.ant.pydt;


import org.ant4eclipse.ant.platform.SubElementContribution;
import org.ant4eclipse.lib.pydt.internal.tools.UsedProjectsArgumentComponent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.ProjectComponent;

import java.util.Hashtable;
import java.util.Map;

/**
 * Contribution class used for subelements within the python support layer.
 * 
 * @todo [02-Aug-2009:KASI] The general support for subelements should be realised like this class which means that
 *       there would be only one SubElementContribution which sets up it's mapping table from the properties.
 * 
 * @author Daniel Kasmeroglu (Daniel.Kasmeroglu@Kasisoft.net)
 */
public class PythonSubelementContribution implements SubElementContribution {

  private static final String   ELEMENTNAME = "pydtReferencedProject";

  private Map<String, Class<?>> mapping;

  /**
   * Initialises this contributor to handle subelements known within the python support layer.
   */
  public PythonSubelementContribution() {
    this.mapping = new Hashtable<String, Class<?>>();
    /** @todo [02-Aug-2009:KASI] Why do we only get lowercase names ? */
    this.mapping.put(ELEMENTNAME.toLowerCase(), UsedProjectsArgumentComponent.class);
  }

  /**
   * {@inheritDoc}
   */
  public boolean canHandleSubElement(String name, ProjectComponent component) {
    return this.mapping.containsKey(name);
  }

  /**
   * {@inheritDoc}
   */
  public Object createSubElement(String name, ProjectComponent component) {
    Class<?> clazz = this.mapping.get(name);
    if (clazz != null) {
      try {
        return clazz.newInstance();
      } catch (InstantiationException ex) {
        throw new BuildException(ex);
      } catch (IllegalAccessException ex) {
        throw new BuildException(ex);
      }
    } else {
      return null;
    }
  }

} /* ENDCLASS */
