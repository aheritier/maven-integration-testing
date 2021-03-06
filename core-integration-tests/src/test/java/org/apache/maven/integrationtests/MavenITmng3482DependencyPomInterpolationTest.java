/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.maven.integrationtests;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.it.IntegrationTestRunner;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3482">MNG-3482</a>.
 *
 * @todo Fill in a better description of what this test verifies!
 *
 * @author <a href="mailto:brianf@apache.org">Brian Fox</a>
 * @author jdcasey
 *
 */
public class MavenITmng3482DependencyPomInterpolationTest
    extends AbstractMavenIntegrationTestCase
{
    public void testitMNG3482()
        throws Exception
    {
        // The testdir is computed from the location of this
        // file.
        File testDir = extractTestResources( getClass(), "/mng-3482" );

        File settings = writeSettings( testDir );

        IntegrationTestRunner itr;

        /*
         * We must first make sure that any artifact created
         * by this test has been removed from the local
         * repository. Failing to do this could cause
         * unstable test results. Fortunately, the itr
         * makes it easy to do this.
         */
        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );

        itr.deleteArtifact( "org.apache.maven.its.mng3482", "mng-3482", "1", "pom" );
        itr.deleteArtifact( "org.apache.maven.its.mng3482", "mng-3482", "1", "jar" );
        itr.deleteArtifact( "test", "dep", "1", "pom" );
        itr.deleteArtifact( "test", "dep2", "1", "pom" );
        itr.deleteArtifact( "test", "dep2", "1", "jar" );

        /*
         * The Command Line Options (CLI) are passed to the
         * itr as a list. This is handy for things like
         * redefining the local repository if needed. In
         * this case, we use the -N flag so that Maven won't
         * recurse. We are only installing the parent pom to
         * the local repo here.
         */
        List cliOptions = new ArrayList();

        cliOptions.add( "-s" );
        cliOptions.add( "\"" + settings.getAbsolutePath() + "\"" );
        itr.executeGoal( "compile", cliOptions );

        /*
         * This is the simplest way to check a build
         * succeeded. It is also the simplest way to create
         * an IT test: make the build pass when the test
         * should pass, and make the build fail when the
         * test should fail. There are other methods
         * supported by the itr. They can be seen here:
         * http://maven.apache.org/shared/maven-itr/apidocs/index.html
         */
        itr.verifyErrorFreeLog();

        /*
         * Reset the streams before executing the itr
         * again.
         */
        itr.resetStreams();
    }

    private File writeSettings( File testDir )
        throws IOException
    {
        File settingsIn = new File( testDir, "settings.xml.in" );

        String settingsContent = null;
        Reader reader = null;
        try
        {
            reader = new FileReader( settingsIn );
            settingsContent = IOUtil.toString( reader );
        }
        finally
        {
            IOUtil.close( reader );
        }

        settingsContent = StringUtils.replace( settingsContent,
                                               "@TESTDIR@",
                                               testDir.getAbsolutePath() );

        File settingsOut = File.createTempFile( "settings.", ".xml" );
        settingsOut.deleteOnExit();

        if ( settingsOut.exists() )
        {
            settingsOut.delete();
        }

        Writer writer = null;
        try
        {
            writer = new FileWriter( settingsOut );
            IOUtil.copy( settingsContent, writer );
        }
        finally
        {
            IOUtil.close( writer );
        }

        return settingsOut;
    }
}
