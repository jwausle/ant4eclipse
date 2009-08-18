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
package org.ant4eclipse.pydt.ant;

import org.ant4eclipse.pydt.test.AbstractWorkspaceBasedTest;
import org.ant4eclipse.pydt.test.BuildResult;
import org.apache.tools.ant.BuildException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * Basic test implementation for the ant task: 'getPythonSourcePath'.
 * 
 * <ul>
 * <li>The <i>emptyXXX</i> tests do show the functionality of the task.</li>
 * <li>The <i>complexXXX</i> tests do show that dependencies don't alter the source pathes.</li>
 * </ul>
 * 
 * @author Daniel Kasmeroglu (Daniel.Kasmeroglu@Kasisoft.net)
 */
public class AbstractSourcePathTest extends AbstractWorkspaceBasedTest {

  private URL _sourcepathxml;

  /**
   * Initialises this set of tests.
   */
  public AbstractSourcePathTest(final boolean dltk) {
    super(dltk);
  }

  /**
   * {@inheritDoc}
   */
  @Before
  public void setup() {
    super.setup();
    _sourcepathxml = getResource("/org/ant4eclipse/pydt/ant/sourcepath.xml");
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void teardown() {
    super.teardown();
    _sourcepathxml = null;
  }

  @Test
  public void emptyProject() {
    final String projectname = createEmptyProject(_sourcepathxml, false);
    final BuildResult buildresult = execute(projectname, "get-source-path");
    final String[] content = buildresult.getTargetOutput("get-source-path");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}" + File.separator + projectname, content[0]);
  }

  @Test(expected = BuildException.class)
  public void emptyProjectMultipleFoldersFailure() {
    final String projectname = createEmptyProject(_sourcepathxml, true);
    execute(projectname, "get-source-path");
  }

  @Test
  public void emptyProjectMultipleFolders() {
    final String projectname = createEmptyProject(_sourcepathxml, true);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}" + File.separator + projectname + File.pathSeparator + "${workspacedir}"
        + File.separator + projectname + File.separator + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void emptyProjectRelative() {
    final String projectname = createEmptyProject(_sourcepathxml, false);
    final BuildResult buildresult = execute(projectname, "get-source-path-relative");
    final String[] content = buildresult.getTargetOutput("get-source-path-relative");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals(".", content[0]);
  }

  @Test(expected = BuildException.class)
  public void emptyProjectMultipleFoldersRelativeFailure() {
    final String projectname = createEmptyProject(_sourcepathxml, true);
    execute(projectname, "get-source-path-relative");
  }

  @Test
  public void emptyProjectMultipleFoldersRelative() {
    final String projectname = createEmptyProject(_sourcepathxml, true);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders-relative");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders-relative");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("." + File.pathSeparator + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void emptyProjectDirseparator() {
    final String projectname = createEmptyProject(_sourcepathxml, false);
    final BuildResult buildresult = execute(projectname, "get-source-path-dirseparator", "@");
    final String[] content = buildresult.getTargetOutput("get-source-path-dirseparator");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}@" + projectname, content[0]);
  }

  @Test(expected = BuildException.class)
  public void emptyProjectMultipleFoldersFailureDirseparator() {
    final String projectname = createEmptyProject(_sourcepathxml, true);
    execute(projectname, "get-source-path-dirseparator", "@");
  }

  @Test
  public void emptyProjectMultipleFoldersDirseparator() {
    final String projectname = createEmptyProject(_sourcepathxml, true);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders-dirseparator", "@");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders-dirseparator");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}@" + projectname + File.pathSeparator + "${workspacedir}@" + projectname + "@"
        + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void complexProject() {
    final String projectname = createComplexProject(_sourcepathxml, false, false);
    final BuildResult buildresult = execute(projectname, "get-source-path");
    final String[] content = buildresult.getTargetOutput("get-source-path");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}" + File.separator + projectname, content[0]);
  }

  @Test(expected = BuildException.class)
  public void complexProjectMultipleFoldersFailure() {
    final String projectname = createComplexProject(_sourcepathxml, true, false);
    execute(projectname, "get-source-path");
  }

  @Test(expected = BuildException.class)
  public void complexProjectMultipleFoldersBothFailure() {
    final String projectname = createComplexProject(_sourcepathxml, true, true);
    execute(projectname, "get-source-path");
  }

  @Test
  public void complexProjectMultipleFolders() {
    final String projectname = createComplexProject(_sourcepathxml, true, false);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}" + File.separator + projectname + File.pathSeparator + "${workspacedir}"
        + File.separator + projectname + File.separator + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void complexProjectMultipleFoldersBoth() {
    final String projectname = createComplexProject(_sourcepathxml, true, true);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}" + File.separator + projectname + File.pathSeparator + "${workspacedir}"
        + File.separator + projectname + File.separator + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void complexProjectRelative() {
    final String projectname = createComplexProject(_sourcepathxml, false, false);
    final BuildResult buildresult = execute(projectname, "get-source-path-relative");
    final String[] content = buildresult.getTargetOutput("get-source-path-relative");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals(".", content[0]);
  }

  @Test(expected = BuildException.class)
  public void complexProjectMultipleFoldersRelativeFailure() {
    final String projectname = createComplexProject(_sourcepathxml, true, false);
    execute(projectname, "get-source-path-relative");
  }

  @Test(expected = BuildException.class)
  public void complexProjectMultipleFoldersRelativeBothFailure() {
    final String projectname = createComplexProject(_sourcepathxml, true, true);
    execute(projectname, "get-source-path-relative");
  }

  @Test
  public void complexProjectMultipleFoldersRelative() {
    final String projectname = createComplexProject(_sourcepathxml, true, false);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders-relative");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders-relative");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("." + File.pathSeparator + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void complexProjectMultipleFoldersRelativeBoth() {
    final String projectname = createComplexProject(_sourcepathxml, true, true);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders-relative");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders-relative");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("." + File.pathSeparator + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void complexProjectDirseparator() {
    final String projectname = createComplexProject(_sourcepathxml, false, false);
    final BuildResult buildresult = execute(projectname, "get-source-path-dirseparator", "@");
    final String[] content = buildresult.getTargetOutput("get-source-path-dirseparator");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}@" + projectname, content[0]);
  }

  @Test(expected = BuildException.class)
  public void complexProjectMultipleFoldersFailureDirseparator() {
    final String projectname = createComplexProject(_sourcepathxml, true, false);
    execute(projectname, "get-source-path-dirseparator", "@");
  }

  @Test(expected = BuildException.class)
  public void complexProjectMultipleFoldersBothFailureDirseparator() {
    final String projectname = createComplexProject(_sourcepathxml, true, true);
    execute(projectname, "get-source-path-dirseparator", "@");
  }

  @Test
  public void complexProjectMultipleFoldersDirseparator() {
    final String projectname = createComplexProject(_sourcepathxml, true, false);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders-dirseparator", "@");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders-dirseparator");
    Assert.assertEquals(1, content.length);
    Assert.assertEquals("${workspacedir}@" + projectname + File.pathSeparator + "${workspacedir}@" + projectname + "@"
        + NAME_GENERATEDSOURCE, content[0]);
  }

  @Test
  public void complexProjectMultipleFoldersBothDirseparator() {
    final String projectname = createComplexProject(_sourcepathxml, true, true);
    final BuildResult buildresult = execute(projectname, "get-source-path-multiple-folders-dirseparator", "@");
    final String[] content = buildresult.getTargetOutput("get-source-path-multiple-folders-dirseparator");
    Assert.assertEquals(1, content.length);
    System.err.println("> " + projectname + " := [" + content[0] + "]");
    Assert.assertEquals("${workspacedir}@" + projectname + File.pathSeparator + "${workspacedir}@" + projectname + "@"
        + NAME_GENERATEDSOURCE, content[0]);
  }

} /* ENDCLASS */