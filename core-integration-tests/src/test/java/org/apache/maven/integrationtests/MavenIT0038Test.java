package org.apache.maven.integrationtests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0038Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Test building project from outside the project directory using '-f'
     * option
     */
    public void testit0038()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0038" );
        IntegrationTestRunner itr = new IntegrationTestRunner( testDir.getAbsolutePath() );
        List cliOptions = new ArrayList();
        cliOptions.add( "-f project/pom2.xml" );
        itr.executeGoal( "package", cliOptions);
        itr.assertFilePresent( "project/target/maven-it-it0038-1.0-build2.jar" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();

    }
}

