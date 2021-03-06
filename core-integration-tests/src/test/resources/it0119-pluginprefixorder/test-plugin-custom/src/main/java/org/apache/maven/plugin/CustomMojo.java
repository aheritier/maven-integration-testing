package org.apache.maven.plugin;

import java.net.URL;

/**
 * @author <a href="mailto:brianf@apache.org">Brian Fox</a>
 * @goal custom
 */
public class CustomMojo
    extends AbstractMojo
{

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        this.getLog().info("Ran Test Custom");
    }

}
