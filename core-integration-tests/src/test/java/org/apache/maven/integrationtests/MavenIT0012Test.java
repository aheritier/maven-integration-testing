package org.apache.maven.integrationtests;

import java.io.File;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0012Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * Test simple POM interpolation
     */
    public void testit0012()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0012" );
        IntegrationTestRunner itr = new IntegrationTestRunner( testDir.getAbsolutePath() );
        itr.invoke( "org.apache.maven.its.plugins:maven-it-plugin-touch:touch" );
        itr.assertFilePresent( "target/touch-3.8.1.txt" );
        itr.assertFilePresent( "child-project/target/child-touch-3.0.3.txt" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();

    }
}

