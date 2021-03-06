package org.apache.maven.integrationtests;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0007Test
    extends AbstractMavenIntegrationTestCase
{

    /**
     * We specify a parent in the POM and make sure that it is downloaded as
     * part of the process.
     */
    public void testit0007()
        throws Exception
    {
        IntegrationTestRunner itr = createTestRunner();
        itr.deleteArtifact( "org.apache.maven.plugins", "maven-plugin-parent", "2.0", "pom" );
        itr.invoke( "package" );
        itr.assertFilePresent( "target/classes/org/apache/maven/it0007/Person.class" );
        itr.assertFilePresent( "target/test-classes/org/apache/maven/it0007/PersonTest.class" );
        itr.assertFilePresent( "target/maven-it-it0007-1.0.jar" );
        itr.assertFilePresent( "target/maven-it-it0007-1.0.jar!/it0007.properties" );
        itr.assertArtifactPresent( "org.apache.maven.plugins", "maven-plugin-parent", "2.0", "pom" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();
    }
}

