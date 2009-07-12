package org.ant4eclipse.jdt.ant.containerargs;

import org.ant4eclipse.jdt.tools.container.JdtClasspathContainerArgument;

import java.util.LinkedList;
import java.util.List;

public class JdtClasspathContainerArgumentDelegate implements JdtClasspathContainerArgumentComponent {

  /** the container argument list */
  private final List<JdtClasspathContainerArgument> _containerArguments = new LinkedList<JdtClasspathContainerArgument>();

  /**
   * @see org.ant4eclipse.jdt.ant.containerargs.JdtClasspathContainerArgumentComponent#createContainerArg()
   */
  public JdtClasspathContainerArgument createJdtClasspathContainerArgument() {

    // create argument
    final JdtClasspathContainerArgument argument = new JdtClasspathContainerArgument();

    // add argument to argument list
    this._containerArguments.add(argument);

    // return result
    return argument;
  }

  /**
   * @see org.ant4eclipse.jdt.ant.containerargs.JdtClasspathContainerArgumentComponent#getContainerArguments()
   */
  public List<JdtClasspathContainerArgument> getJdtClasspathContainerArguments() {

    // return result
    return this._containerArguments;
  }
}
