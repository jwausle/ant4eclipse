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
package org.ant4eclipse.jdt.ecj.internal.tools;

import org.ant4eclipse.jdt.ecj.CompileJobResult;

import org.eclipse.jdt.core.compiler.CategorizedProblem;

public class CompileJobResultImpl implements CompileJobResult {

  private boolean              _succeeded;

  private CategorizedProblem[] _categorizedProblems;

  /**
   * {@inheritDoc}
   */
  public boolean succeeded() {
    return this._succeeded;
  }

  /**
   * {@inheritDoc}
   */
  public CategorizedProblem[] getCategorizedProblems() {
    return this._categorizedProblems == null ? new CategorizedProblem[0] : this._categorizedProblems;
  }

  void setSucceeded(boolean succeeded) {
    this._succeeded = succeeded;
  }

  protected void setCategorizedProblems(CategorizedProblem[] categorizedProblems) {
    this._categorizedProblems = categorizedProblems;
  }
}
