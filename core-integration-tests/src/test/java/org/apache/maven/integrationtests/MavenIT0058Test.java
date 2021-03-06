package org.apache.maven.integrationtests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0058Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Verify that profiles from settings.xml do not pollute module lists
     * across projects in a reactorized build.
     */
    public void testit0058()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0058" );
        IntegrationTestRunner itr = new IntegrationTestRunner( testDir.getAbsolutePath() );
        List cliOptions = new ArrayList();
        cliOptions.add( "--settings ./settings.xml" );
        itr.executeGoal( "package", cliOptions );
        itr.assertFilePresent( "subproject/target/subproject-1.0.jar" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();

    }
}

