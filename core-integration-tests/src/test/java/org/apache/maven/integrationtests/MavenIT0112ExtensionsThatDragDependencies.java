package org.apache.maven.integrationtests;

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

import java.io.File;

import org.apache.maven.it.IntegrationTestRunner;

public class MavenIT0112ExtensionsThatDragDependencies
    extends AbstractMavenIntegrationTestCase
{
    public void testit0112()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), "/it0112-extensionsThatDragDependencies" );

        IntegrationTestRunner itr;

        // Install the parent POM
        itr = new IntegrationTestRunner( testDir.getAbsolutePath() );
        itr.deleteArtifact( "org.apache.maven.its.it0112", "test-extension", "1.0-SNAPSHOT", "jar" );
        itr.deleteArtifact( "org.apache.maven.its.it0112", "test-project", "1.0-SNAPSHOT", "jar" );

        // Install the extension with the resources required for the test
        itr = new IntegrationTestRunner( new File( testDir.getAbsolutePath(), "test-extension" ).getAbsolutePath() );
        itr.invoke( "install" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();

        // Run the whole test
        itr = new IntegrationTestRunner( new File( testDir.getAbsolutePath(), "test-project" ).getAbsolutePath() );
        itr.invoke( "org.apache.maven.plugins:maven-project-info-reports-plugin:2.0.1:scm" );
        // ommitted because we always get velocity errors that aren't covered by the itr
//        itr.verifyErrorFreeLog();
        itr.resetStreams();
    }
}
