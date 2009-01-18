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
package org.ant4eclipse.jdt.ant;

import java.io.File;

import org.ant4eclipse.core.Assert;
import org.ant4eclipse.jdt.model.project.JavaProjectRole;
import org.ant4eclipse.platform.ant.base.AbstractGetProjectPathTask;
import org.ant4eclipse.platform.model.resource.EclipseProject;
import org.apache.tools.ant.BuildException;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GetOutputPathTask extends AbstractGetProjectPathTask {

  /** DEFAULT_FOLDER */
  private static final String DEFAULT_FOLDER        = "defaultFolder";

  /** ALL */
  private static final String ALL                   = "all";

  /** FOR_SOURCE_FOLDER */
  private static final String FOR_SOURCE_FOLDER     = "forSourceFolder";

  /** defaultFolder, all, forSourceFolder */
  private String              _resolve              = DEFAULT_FOLDER;

  /** sourceFolder */
  private String              _sourceFolder;

  /** - */
  private boolean             _allowMultipleFolders = false;

  /**
   * Returns true if multiple folders are allowed.
   * 
   * @return true <=> Multiple folders are allowed.
   */
  public boolean isAllowMultipleFolders() {
    return this._allowMultipleFolders;
  }

  /**
   * @param allowMultipleFolders
   */
  public void setAllowMultipleFolders(final boolean allowMultipleFolders) {
    this._allowMultipleFolders = allowMultipleFolders;
  }

  /**
   * @param resolve
   */
  public final void setResolve(final String resolve) {
    if (DEFAULT_FOLDER.equals(resolve) || FOR_SOURCE_FOLDER.equals(resolve) || ALL.equals(resolve)) {
      this._resolve = resolve;
    } else {
      throw new BuildException("Attribute resolve must have one of the following values: '" + FOR_SOURCE_FOLDER
          + "', '" + ALL + "' or '" + DEFAULT_FOLDER + "'!");
    }

  }

  /**
   * Returns the location of the source folder.
   * 
   * @return The location of the source folder.
   */
  public final String getSourceFolder() {
    return this._sourceFolder;
  }

  /**
   * @param sourceFolder
   */
  public final void setSourceFolder(final String sourceFolder) {
    this._sourceFolder = sourceFolder;
  }

  public final boolean isSourceFolderSet() {
    return this._sourceFolder != null;
  }

  protected final void requireSourceFolderSet() {
    if (!isSourceFolderSet()) {
      throw new BuildException("Attribute 'sourceFolder' has to be set if resolve='forSourceFolder'!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public File[] resolvePath() {
    final int relative = isRelative() ? EclipseProject.PROJECT_RELATIVE_WITHOUT_LEADING_PROJECT_NAME
        : EclipseProject.ABSOLUTE;

    if (FOR_SOURCE_FOLDER.equals(this._resolve)) {
      requireSourceFolderSet();

      // try {

      final JavaProjectRole javaProjectRole = (JavaProjectRole) getEclipseProject().getRole(JavaProjectRole.class);

      final String pathName = javaProjectRole.getOutputFolderForSourceFolder(getSourceFolder());
      final File resolvedPathEntry = getEclipseProject().getChild(pathName, relative);
      return new File[] { resolvedPathEntry };
      // TODO
      // } catch (final MultipleFolderException e) {
      // final StringBuffer buffer = new StringBuffer();
      // buffer.append("Project '");
      // buffer.append(getEclipseProject().getName());
      // buffer.append("' contains multiple output folder for source folder '");
      // buffer.append(getSourceFolder());
      // buffer.append("'!");
      // throw new BuildException(buffer.toString());
      // } catch (final BuildException ex) {
      // throw ex;
      // } catch (final Exception e) {
      // throw new BuildException(e.getMessage());
      // }
    } else if (ALL.equals(this._resolve)) {
      // try {

      final JavaProjectRole javaProjectRole = (JavaProjectRole) getEclipseProject().getRole(JavaProjectRole.class);
      final String[] pathNames = javaProjectRole.getAllOutputFolders();
      final File[] resolvedPathEntries = getEclipseProject().getChildren(pathNames, relative);
      return resolvedPathEntries;

      // TODO
      // } catch (final MultipleFolderException e) {
      // A4ELogging.debug(e.getMessage());
      // final StringBuffer buffer = new StringBuffer();
      // buffer.append("Project '");
      // buffer.append(getEclipseProject().getName());
      // buffer.append("' contains multiple output folder! ");
      // buffer.append("If you want to allow this, ");
      // buffer.append(" set allowMultipleFolder='true'!");
      // throw new BuildException(buffer.toString());
      // } catch (final BuildException ex) {
      // throw ex;
      // } catch (final Exception e) {
      // throw new BuildException(e.getMessage());
      // }
    } else {
      Assert.assertTrue(DEFAULT_FOLDER.equals(this._resolve), "Illegal value for attribute resolve!");
      // try {
      final JavaProjectRole javaProjectRole = (JavaProjectRole) getEclipseProject().getRole(JavaProjectRole.class);
      final String path = javaProjectRole.getDefaultOutputFolder();
      final File resolvedPathEntry = getEclipseProject().getChild(path, relative);
      return new File[] { resolvedPathEntry };
      // TODO
      // } catch (final MultipleFolderException e) {
      // A4ELogging.debug(e.getMessage());
      // // Should never happen here...
      // final StringBuffer buffer = new StringBuffer();
      // buffer.append("Project '");
      // buffer.append(getEclipseProject().getName());
      // buffer.append("' contains multiple default output folder! ");
      // throw new BuildException(buffer.toString());
      // } catch (final BuildException ex) {
      // throw ex;
      // } catch (final Exception e) {
      // throw new BuildException(e.getMessage());
      // }
    }
  }
}
