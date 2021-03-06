package org.apache.maven.integrationtests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.it.IntegrationTestRunner;

/**
 * Test activation and deactivation of profiles.
 * 
 */
public class MavenITmng3545ProfileDeactivation
    extends AbstractMavenIntegrationTestCase
{
    public MavenITmng3545ProfileDeactivation()
        throws InvalidVersionSpecificationException
    {
        super( "(2.0.9,)" );
    }

    /**
     * Test build with two active by default profiles
     * 
     */
    public void testBasicBuildWithDefaultProfiles()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/mng-3545-ProfileDeactivation" );

        IntegrationTestRunner itr;

        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );

        List cliOptions = new ArrayList();
        itr.executeGoal( "package", cliOptions );
        itr.verifyErrorFreeLog();
        // profile 1 and 2 are active by default
        itr.assertFilePresent( "target/profile1/touch.txt" );
        itr.assertFilePresent( "target/profile2/touch.txt" );
        itr.assertFileNotPresent( "target/profile3/touch.txt" );
        itr.assertFileNotPresent( "target/profile4/touch.txt" );
        itr.assertFileNotPresent( "target/profile5/touch.txt" );
        itr.resetStreams();

    }

    /**
     * Test command line deactivation of active by default profiles.
     * 
     */
    public void testDeactivateDefaultProfilesDash()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/mng-3545-ProfileDeactivation" );

        IntegrationTestRunner itr;

        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );

        List cliOptions = new ArrayList();

        // Deactivate active by default profiles
        cliOptions.add( "-P-profile1" );
        cliOptions.add( "-P -profile2" );
        itr.executeGoal( "package", cliOptions );
        itr.verifyErrorFreeLog();
        itr.assertFileNotPresent( "target/profile1/touch.txt" );
        itr.assertFileNotPresent( "target/profile2/touch.txt" );
        itr.resetStreams();

    }

    public void testDeactivateDefaultProfilesExclamation()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/mng-3545-ProfileDeactivation" );
    
        IntegrationTestRunner itr;
    
        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );
    
        List cliOptions = new ArrayList();
    
        // Deactivate active by default profiles
        cliOptions.add( "-P!profile1" );
        cliOptions.add( "-P !profile2" );    
        itr.executeGoal( "package", cliOptions );
        itr.verifyErrorFreeLog();
        itr.assertFileNotPresent( "target/profile1/touch.txt" );
        itr.assertFileNotPresent( "target/profile2/touch.txt" );
        itr.resetStreams();
    
    }

    /**
     * Test command line deactivation of a profile that was activated
     * by a property
     * 
     */
    public void testDeactivateActivatedByProp()
        throws Exception
    {

        File testDir = extractTestResources( getClass(), "/mng-3545-ProfileDeactivation" );

        IntegrationTestRunner itr;

        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );

        List cliOptions = new ArrayList();

        // Activate with a prop, then deactivate
        cliOptions.add( "-Dprofile3-active-by-property=true" );
        cliOptions.add( "-P-profile3" );
        itr.executeGoal( "package", cliOptions );
        itr.verifyErrorFreeLog();
        itr.assertFilePresent( "target/profile1/touch.txt" );
        itr.assertFilePresent( "target/profile2/touch.txt" );
        itr.assertFileNotPresent( "target/profile3/touch.txt" );
        itr.assertFileNotPresent( "target/profile4/touch.txt" );
        itr.assertFileNotPresent( "target/profile5/touch.txt" );
        itr.resetStreams();
    }

    /**
     * Test that deactivating from the command line takes priority over
     * activating from the command line.
     * 
     */
    public void testActivateThenDeactivate()
        throws Exception
    {

        File testDir = extractTestResources( getClass(), "/mng-3545-ProfileDeactivation" );

        IntegrationTestRunner itr;

        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );

        List cliOptions = new ArrayList();

        // Activate then deactivate
        cliOptions.add( "-Pprofile4" );
        cliOptions.add( "-P-profile4" );
        itr.executeGoal( "package", cliOptions );
        itr.verifyErrorFreeLog();
        itr.assertFilePresent( "target/profile1/touch.txt" );
        itr.assertFilePresent( "target/profile2/touch.txt" );
        itr.assertFileNotPresent( "target/profile3/touch.txt" );
        itr.assertFileNotPresent( "target/profile4/touch.txt" );
        itr.assertFileNotPresent( "target/profile5/touch.txt" );
        itr.resetStreams();
    }

    /**
     * Test that default profiles are deactivated when another profile is
     * activated.
     * 
     */
    public void testDefaultProfileAutoDeactivation()
        throws Exception
    {

        File testDir = extractTestResources( getClass(), "/mng-3545-ProfileDeactivation" );

        IntegrationTestRunner itr;

        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );

        List cliOptions = new ArrayList();

        // Activate
        cliOptions.add( "-Pprofile4" );
        itr.executeGoal( "package", cliOptions );
        itr.verifyErrorFreeLog();
        itr.assertFileNotPresent( "target/profile1/touch.txt" );
        itr.assertFileNotPresent( "target/profile2/touch.txt" );
        itr.assertFileNotPresent( "target/profile3/touch.txt" );
        itr.assertFilePresent( "target/profile4/touch.txt" );
        itr.assertFileNotPresent( "target/profile5/touch.txt" );
        itr.resetStreams();
    }
    
    /**
     * remove the target dir after each test run
     */
    public void tearDown()
        throws IOException
    {
        File testDir = extractTestResources( getClass(), "/mng-3545-ProfileDeactivation" );

        File targetDir = new File( testDir, "target" );
        if ( targetDir.exists() )
        {
            targetDir.delete();
        }
    }

}
