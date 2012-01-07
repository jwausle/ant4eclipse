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
package org.ant4eclipse.ant.platform.core.condition;

import org.ant4eclipse.ant.core.AbstractAnt4EclipseCondition;
import org.ant4eclipse.ant.platform.core.EclipseProjectComponent;
import org.ant4eclipse.ant.platform.core.delegate.EclipseProjectDelegate;
import org.ant4eclipse.lib.platform.model.resource.EclipseProject;
import org.ant4eclipse.lib.platform.model.resource.Workspace;
import org.ant4eclipse.lib.platform.model.resource.role.ProjectRole;
import org.apache.tools.ant.BuildException;

import java.io.File;

/**
 * <p>
 * Abstract condition for all project based conditions.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractProjectBasedCondition extends AbstractAnt4EclipseCondition implements
    EclipseProjectComponent {

  /** the project delegate */
  private EclipseProjectDelegate _projectDelegate;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractProjectBasedCondition}.
   * </p>
   */
  public AbstractProjectBasedCondition() {
    this._projectDelegate = new EclipseProjectDelegate( this );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProjectName( String project ) {
    this._projectDelegate.setProjectName( project );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void setWorkspace( String workspace ) {
    this._projectDelegate.setWorkspaceDirectory( workspace );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setWorkspaceDirectory( String workspaceDirectory ) {
    this._projectDelegate.setWorkspaceDirectory( workspaceDirectory );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ensureRole( Class<? extends ProjectRole> projectRoleClass ) {
    this._projectDelegate.ensureRole( projectRoleClass );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EclipseProject getEclipseProject() throws BuildException {
    return this._projectDelegate.getEclipseProject();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Workspace getWorkspace() {
    return this._projectDelegate.getWorkspace();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final File getWorkspaceDirectory() {
    return this._projectDelegate.getWorkspaceDirectory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isProjectNameSet() {
    return this._projectDelegate.isProjectNameSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isWorkspaceDirectorySet() {
    return this._projectDelegate.isWorkspaceDirectorySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getWorkspaceId() {
    return this._projectDelegate.getWorkspaceId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWorkspaceIdSet() {
    return this._projectDelegate.isWorkspaceIdSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requireWorkspaceDirectoryOrWorkspaceIdSet() {
    this._projectDelegate.requireWorkspaceDirectoryOrWorkspaceIdSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWorkspaceId( String identifier ) {
    this._projectDelegate.setWorkspaceId( identifier );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void requireWorkspaceAndProjectNameSet() {
    this._projectDelegate.requireWorkspaceAndProjectNameSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings( "deprecation" )
  public void setProject( File projectPath ) {
    this._projectDelegate.setProject( projectPath );
  }
  
} /* ENDCLASS */
