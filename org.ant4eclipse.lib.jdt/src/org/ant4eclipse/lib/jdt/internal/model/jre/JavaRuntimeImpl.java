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
package org.ant4eclipse.lib.jdt.internal.model.jre;

import java.io.File;

import org.ant4eclipse.lib.core.Assure;
import org.ant4eclipse.lib.core.data.Version;
import org.ant4eclipse.lib.jdt.model.jre.JavaProfile;
import org.ant4eclipse.lib.jdt.model.jre.JavaRuntime;

/**
 * <p>
 * Defines a java runtime environment.
 * </p>
 * 
 * @author Daniel Kasmeroglu (daniel.kasmeroglu@kasisoft.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public final class JavaRuntimeImpl implements JavaRuntime {

  /** the id */
  private final String      _id;

  /** the location */
  private final File        _location;

  /** the version */
  private final Version     _javaVersion;

  /** the version */
  private final Version     _javaSpecificationVersion;

  /** - */
  private final JavaProfile _javaProfile;

  /** the libraries */
  private File[]            _libraries = new File[0];

  /**
   * <p>
   * Creates a new instance of type {@link JavaRuntimeImpl}.
   * </p>
   * 
   * @param id
   * @param location
   * @param libraries
   * @param javaVersion
   * @param javaSpecificationVersion
   * @param javaProfile
   */
  JavaRuntimeImpl(String id, File location, File[] libraries, Version javaVersion, Version javaSpecificationVersion,
      JavaProfile javaProfile) {

    Assure.nonEmpty("id", id);
    Assure.isDirectory("location", location);
    Assure.notNull("libraries", libraries);
    Assure.notNull("javaVersion", javaVersion);
    Assure.notNull("javaSpecificationVersion", javaSpecificationVersion);
    Assure.notNull("javaProfile", javaProfile);

    this._id = id;
    this._location = location;
    this._libraries = libraries;
    this._javaVersion = javaVersion;
    this._javaSpecificationVersion = javaSpecificationVersion;
    this._javaProfile = javaProfile;
  }

  /**
   * {@inheritDoc}
   */
  public String getId() {
    return this._id;
  }

  /**
   * {@inheritDoc}
   */
  public File getLocation() {
    return this._location;
  }

  /**
   * {@inheritDoc}
   */
  public File[] getLibraries() {
    return this._libraries;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isJavaVersion(Version version) {
    return (version.getMajor() == this._javaVersion.getMajor()) && (version.getMinor() == this._javaVersion.getMinor());
  }

  /**
   * {@inheritDoc}
   */
  public Version getJavaVersion() {
    return this._javaVersion;
  }

  /**
   * {@inheritDoc}
   */
  public Version getSpecificationVersion() {
    return this._javaSpecificationVersion;
  }

  /**
   * {@inheritDoc}
   */
  public JavaProfile getJavaProfile() {
    return this._javaProfile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[JavaRuntime:");
    buffer.append(" id: ");
    buffer.append(this._id);
    buffer.append(" javaVersion: ");
    buffer.append(this._javaVersion);
    buffer.append(" javaSpecificationVersion: ");
    buffer.append(this._javaSpecificationVersion);
    buffer.append(" javaProfile: ");
    buffer.append(this._javaProfile);
    buffer.append(" location: ");
    buffer.append(this._location);
    buffer.append(" { ");
    for (int i0 = 0; (this._libraries != null) && (i0 < this._libraries.length); i0++) {
      buffer.append(" libraries[" + i0 + "]: ");
      buffer.append(this._libraries[i0]);
    }
    buffer.append(" } ");
    buffer.append("]");
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int prime = 31;
    int result = 1;
    result = prime * result + ((this._id == null) ? 0 : this._id.hashCode());
    result = prime * result + ((this._location == null) ? 0 : this._location.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    JavaRuntimeImpl other = (JavaRuntimeImpl) obj;
    if (this._id == null) {
      if (other._id != null) {
        return false;
      }
    } else if (!this._id.equals(other._id)) {
      return false;
    }
    if (this._location == null) {
      if (other._location != null) {
        return false;
      }
    } else if (!this._location.equals(other._location)) {
      return false;
    }
    return true;
  }

} /* ENDCLASS */
