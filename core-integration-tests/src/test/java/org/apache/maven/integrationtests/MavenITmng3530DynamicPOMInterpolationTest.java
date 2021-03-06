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

import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.it.IntegrationTestRunner;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3530">MNG-3530</a>.
 *
 * Contains various tests for dynamism of interpolation expressions within the POM.
 *
 * @author <a href="mailto:brianf@apache.org">Brian Fox</a>
 * @author jdcasey
 *
 */
public class MavenITmng3530DynamicPOMInterpolationTest
    extends AbstractMavenIntegrationTestCase
{
    private static final String BASEDIR = "/mng-3530-dynamicPOMInterpolation/";

    public MavenITmng3530DynamicPOMInterpolationTest()
        throws InvalidVersionSpecificationException
    {
        super( "(2.0.9,)" ); // only test in 2.0.9+
    }

    public void testitMNG3530_BuildPath()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), BASEDIR + "build-path" );
        File pluginDir = new File( testDir, "plugin" );
        File projectDir = new File( testDir, "project" );

        // First, install the plugin that modifies the project.build.directory and
        // validates that the modification propagated into the validation-mojo
        // configuration. Once this is installed, we can run a project build that
        // uses it to see how Maven will respond to a modification in the project build directory.
        IntegrationTestRunner itr = new IntegrationTestRunner( pluginDir.getAbsolutePath() );
        itr.invoke( "install" );

        itr.verifyErrorFreeLog();
        itr.resetStreams();

        // Now, build the project. If the plugin configuration doesn't recognize
        // the update to the project.build.directory, it will fail the build.
        itr = new IntegrationTestRunner( projectDir.getAbsolutePath() );

        itr.invoke( "package" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();
    }

    public void testitMNG3530_POMProperty()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), BASEDIR
                                                                             + "pom-property" );
        File pluginDir = new File( testDir, "plugin" );
        File projectDir = new File( testDir, "project" );

        // First, install the plugin that modifies the myDirectory and
        // validates that the modification propagated into the validation-mojo
        // configuration. Once this is installed, we can run a project build that
        // uses it to see how Maven will respond to a modification in the POM property.
        IntegrationTestRunner itr = new IntegrationTestRunner( pluginDir.getAbsolutePath() );
        itr.invoke( "install" );

        itr.verifyErrorFreeLog();
        itr.resetStreams();

        // Now, build the project. If the plugin configuration doesn't recognize
        // the update to the myDirectory, it will fail the build.
        itr = new IntegrationTestRunner( projectDir.getAbsolutePath() );

        itr.invoke( "package" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();
    }

    public void testitMNG3530_ResourceDirectoryInterpolation()
        throws Exception
    {
        File testDir = extractTestResources( getClass(), BASEDIR
                                                                             + "resource-object" );
        File pluginDir = new File( testDir, "plugin" );
        File projectDir = new File( testDir, "project" );

        // First, install the plugin which validates that all resource directory
        // specifications have been interpolated.
        IntegrationTestRunner itr = new IntegrationTestRunner( pluginDir.getAbsolutePath() );
        itr.invoke( "install" );

        itr.verifyErrorFreeLog();
        itr.resetStreams();

        // Now, build the project. If the plugin finds an uninterpolated resource
        // directory, it will fail the build.
        itr = new IntegrationTestRunner( projectDir.getAbsolutePath() );

        itr.invoke( "package" );
        itr.verifyErrorFreeLog();
        itr.resetStreams();
    }
}
