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
package org.ant4eclipse.pde.ant;

import org.ant4eclipse.core.ant.AbstractAnt4EclipseTask;
import org.ant4eclipse.core.ant.ExtendedBuildException;
import org.ant4eclipse.core.util.Utilities;

import org.ant4eclipse.pde.model.product.ProductDefinition;
import org.ant4eclipse.pde.model.product.ProductDefinitionParser;

import org.ant4eclipse.platform.ant.core.delegate.WorkspaceDelegate;
import org.ant4eclipse.platform.model.resource.EclipseProject;
import org.ant4eclipse.platform.model.resource.EclipseProject.PathStyle;

import org.apache.tools.ant.BuildException;
import org.osgi.framework.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * This task allows to request several information from an eclipse product configuration.
 * </p>
 * 
 * @author Daniel Kasmeroglu (Daniel.Kasmeroglu@Kasisoft.net)
 */
public class QueryProductTask extends AbstractAnt4EclipseTask {

  /**
   * The query type is used to select the kind of data that is being requested.
   */
  public static enum Query {

    /** - */
    basedonfeatures,

    /** - */
    configini,

    /** - */
    programargs,

    /** - */
    vmargs,

    /** - */
    application,

    /** - */
    version,

    /** - */
    launchername,

    /** - */
    plugins,

    /** - */
    fragments,

    /** - */
    features;

  }

  /**
   * Selection of the operating system currently used to query the product configuration.
   */
  public static enum Os {

    /** - */
    linux,

    /** - */
    solaris,

    /** - */
    macosx,

    /** - */
    win32;

  }

  /** - */
  private String            _property;

  /** - */
  private File              _product;

  /** - */
  private Query             _query;

  /** - */
  private String            _delimiter;

  /** - */
  private Os                _os;

  /** - */
  private WorkspaceDelegate _workspacedelegate;

  /** - */
  private boolean           _defaultisempty;

  /**
   * Initialises this task with default values.
   */
  public QueryProductTask() {
    super();
    this._workspacedelegate = new WorkspaceDelegate(this);
    this._os = Os.win32;
    this._delimiter = ",";
    this._query = null;
    this._product = null;
    this._property = null;
    this._defaultisempty = true;
  }

  /**
   * Enables/disables the property setting for the case no value is available.
   * 
   * @param newdefaultisempty
   *          <code>true</code> <=> Enables the property setting (empty value).
   */
  public void setDefaultIsEmpty(boolean newdefaultisempty) {
    this._defaultisempty = newdefaultisempty;
  }

  /**
   * Sets the workspace directory. Only necessary for queries trying to access filesystem information.
   * 
   * @param workspace
   *          The workspace directory. Not <code>null</code>.
   */
  public final void setWorkspaceDirectory(File workspace) {
    this._workspacedelegate.setWorkspaceDirectory(workspace);
  }

  /**
   * Changes the current os used for the querying of the product configuration.
   * 
   * @param newos
   *          The new os used for the querying of the product configuration. Not <code>null</code>.
   */
  public void setOs(Os newos) {
    this._os = newos;
  }

  /**
   * Changes the query used to access the product information.
   * 
   * @param newquery
   *          The new query used to access product information. Not <code>null</code>.
   */
  public void setQuery(Query newquery) {
    this._query = newquery;
  }

  /**
   * Changes the name of the property which will be set to access the value.
   * 
   * @param newproperty
   *          The new property name. Neither <code>null</code> nor empty.
   */
  public void setProperty(String newproperty) {
    this._property = Utilities.cleanup(newproperty);
  }

  /**
   * Changes the location of the product configuration file.
   * 
   * @param newproduct
   *          The new location of the product file. Not <code>null</code>.
   */
  public void setProduct(File newproduct) {
    this._product = newproduct;
  }

  /**
   * Changes the delimiter which is used for list values.
   * 
   * @param newdelimiter
   *          The delimiter to be used for list values.
   */
  public void setDelimiter(String newdelimiter) {
    this._delimiter = Utilities.cleanup(newdelimiter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void preconditions() throws BuildException {
    super.preconditions();
    if (this._property == null) {
      throw new BuildException("The attribute 'property' has to be set.");
    }
    if (this._product == null) {
      throw new BuildException("The attribute 'product' has to be set.");
    }
    if (!this._product.isFile()) {
      throw new ExtendedBuildException("The product configuration '%s' is not a regular file.", this._product);
    }
    if (this._query == null) {
      throw new ExtendedBuildException("The attribute 'query' has to be set to one of the following values: %s",
          Utilities.listToString(Query.values(), null));
    }
    if (this._query == Query.configini) {
      this._workspacedelegate.requireWorkspaceDirectorySet();
    }
  }

  /**
   * Loads the product definition.
   * 
   * @return The loaded product definition. Not <code>null</code>.
   */
  private ProductDefinition loadProductDefinition() {
    InputStream instream = null;
    try {
      instream = new FileInputStream(this._product);
      return ProductDefinitionParser.parseProductDefinition(instream);
    } catch (IOException ex) {
      throw new BuildException(ex);
    } finally {
      Utilities.close(instream);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doExecute() {
    ProductDefinition productdef = loadProductDefinition();
    String value = null;
    switch (this._query) {
    case plugins:
      value = Utilities.listToString(productdef.getPluginIds(), this._delimiter);
      break;
    case fragments:
      value = Utilities.listToString(productdef.getFragmentIds(), this._delimiter);
      break;
    case features:
      value = Utilities.listToString(productdef.getFeatureIds(), this._delimiter);
      break;
    case launchername:
      value = productdef.getLaunchername();
      break;
    case application:
      value = productdef.getApplication();
      break;
    case version:
      Version version = productdef.getVersion();
      if (version != null) {
        value = version.toString();
      }
      break;
    case programargs:
      value = getArgs(productdef.getProgramArgs(), productdef.getProgramArgs(ProductDefinition.Os.valueOf(this._os
          .name())));
      break;
    case vmargs:
      value = getArgs(productdef.getVmArgs(), productdef.getVmArgs(ProductDefinition.Os.valueOf(this._os.name())));
      break;
    case configini:
      String configini = productdef.getConfigIni(ProductDefinition.Os.valueOf(this._os.name()));
      if (configini != null) {
        int idx = configini.indexOf('/', 1);
        String projectname = configini.substring(1, idx);
        String path = configini.substring(idx + 1);
        EclipseProject project = this._workspacedelegate.getWorkspace().getProject(projectname);
        value = project.getChild(path, PathStyle.ABSOLUTE).getAbsolutePath();
      }
      break;
    case basedonfeatures:
      value = String.valueOf(productdef.isBasedOnFeatures());
      break;
    default:
      throw new ExtendedBuildException("The query type '%s' is currently not implemented.", this._query);
    }
    if (value != null) {
      getProject().setProperty(this._property, value);
    } else {
      if (this._defaultisempty) {
        getProject().setProperty(this._property, "");
      }
    }
  }

  /**
   * Concatenates the supplied arguments/argumentlists.
   * 
   * @param args
   *          The arguments/argumentlists that have to be concatenated.
   * 
   * @return The resulting argumentlist. Maybe <code>null</code>.
   */
  private String getArgs(String... args) {
    if (args == null) {
      return null;
    }
    StringBuffer buffer = new StringBuffer();
    for (String arg : args) {
      if (arg != null) {
        if (buffer.length() > 0) {
          buffer.append(' ');
        }
        buffer.append(arg);
      }
    }
    return Utilities.cleanup(buffer.toString());
  }

} /* ENDCLASS */