package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3948">MNG-3948</a>.
 * 
 * @author Benjamin Bentmann
 * @version $Id$
 */
public class MavenITmng3948ParentResolutionFromProfileReposTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng3948ParentResolutionFromProfileReposTest()
    {
        super( "(2.0.10,2.1.0-M1),(2.1.0-M1,)" );
    }

    /**
     * Test that parent POMs can be resolved from remote repositories defined by (active) profiles in profiles.xml.
     */
    public void testitFromProfilesXml()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3948/test-1" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3948" );
        verifier.filterFile( "profiles.xml", "profiles.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.assertArtifactPresent( "org.apache.maven.its.mng3948", "parent", "0.1", "pom" );
    }

    /**
     * Test that parent POMs can be resolved from remote repositories defined by (active) profiles in the POM.
     */
    public void testitFromPom()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3948/test-2" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3948" );
        verifier.filterFile( "pom.xml", "pom.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.assertArtifactPresent( "org.apache.maven.its.mng3948", "parent", "0.2", "pom" );
    }

}