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
package org.ant4eclipse.ant.platform.core.task;

import org.ant4eclipse.ant.core.AbstractAnt4EclipseTask;
import org.ant4eclipse.ant.platform.core.EclipseProjectComponent;
import org.ant4eclipse.ant.platform.core.delegate.EclipseProjectDelegate;
import org.ant4eclipse.lib.platform.model.resource.EclipseProject;
import org.ant4eclipse.lib.platform.model.resource.Workspace;
import org.ant4eclipse.lib.platform.model.resource.role.ProjectRole;
import org.apache.tools.ant.BuildException;

import java.io.File;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractProjectBasedTask extends AbstractAnt4EclipseTask implements EclipseProjectComponent {

  /** the project delegate */
  private EclipseProjectDelegate _eclipseProjectDelegate;

  /**
   * <p>
   * Creates a new instance of type AbstractProjectBasedTask.
   * </p>
   */
  public AbstractProjectBasedTask() {
    super();

    // create delegate
    this._eclipseProjectDelegate = new EclipseProjectDelegate( this );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void ensureRole( Class<? extends ProjectRole> projectRoleClass ) {
    this._eclipseProjectDelegate.ensureRole( projectRoleClass );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EclipseProject getEclipseProject() throws BuildException {
    return this._eclipseProjectDelegate.getEclipseProject();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Workspace getWorkspace() {
    return this._eclipseProjectDelegate.getWorkspace();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File getWorkspaceDirectory() {
    return this._eclipseProjectDelegate.getWorkspaceDirectory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isProjectNameSet() {
    return this._eclipseProjectDelegate.isProjectNameSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWorkspaceDirectorySet() {
    return this._eclipseProjectDelegate.isWorkspaceDirectorySet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requireWorkspaceAndProjectNameSet() {
    this._eclipseProjectDelegate.requireWorkspaceAndProjectNameSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getWorkspaceId() {
    return this._eclipseProjectDelegate.getWorkspaceId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWorkspaceIdSet() {
    return this._eclipseProjectDelegate.isWorkspaceIdSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requireWorkspaceDirectoryOrWorkspaceIdSet() {
    this._eclipseProjectDelegate.requireWorkspaceDirectoryOrWorkspaceIdSet();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWorkspaceId( String identifier ) {
    this._eclipseProjectDelegate.setWorkspaceId( identifier );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProjectName( String projectName ) {
    this._eclipseProjectDelegate.setProjectName( projectName );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings( "deprecation" )
  public void setWorkspace( String workspace ) {
    this._eclipseProjectDelegate.setWorkspace( workspace );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWorkspaceDirectory( String workspaceDirectory ) {
    this._eclipseProjectDelegate.setWorkspaceDirectory( workspaceDirectory );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Deprecated
  public void setProject( File projectPath ) {
    this._eclipseProjectDelegate.setProject( projectPath );
  }
  
} /* ENDCLASS */
