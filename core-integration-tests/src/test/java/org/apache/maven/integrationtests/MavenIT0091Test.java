package org.apache.maven.integrationtests;

import java.io.File;

import org.apache.maven.it.IntegrationTestRunner;

/**
 * # it0091 currrently fails. Not sure if there is an associated JIRA.
 */
public class MavenIT0091Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Test that currently demonstrates that properties are not correctly
     * interpolated into other areas in the POM. This may strictly be a boolean
     * problem: I captured the problem as it was reported.
     */
    public void testit0091()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0091" );
        IntegrationTestRunner itr = new IntegrationTestRunner( testDir.getAbsolutePath() );
        itr.invoke( "test" );
        itr.assertFilePresent( "target/classes/test.properties" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();

    }
}

