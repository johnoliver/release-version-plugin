package com.insightfullogic.release;

/*
 Copyright 2013 John Oliver

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

public abstract class AbstractVersionModMojo extends AbstractMojo {

	/**
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * The version number of the versions plugin to back onto
	 *
	 * @parameter property="rv.versionsVersion" default-value="2.1"
	 */
	protected String versionsPluginVersion;

	/**
	 * The version number of the scm plugin to back onto
	 *
	 * @parameter property="rv.scmVersion" default-value="1.8.1"
	 */
	protected String scmPluginVersion;

	/**
	 * The current Maven session.
	 * 
	 * @parameter property="session"
	 * @required
	 * @readonly
	 */
	protected MavenSession session;

	/**
	 * The Maven BuildPluginManager component.
	 * 
	 * @component
	 * @required
	 */
	protected BuildPluginManager pluginManager;

	/**
	 * Base directory of the project.
	 * 
	 * @parameter property="rv.gitDir" default-value="${basedir}"
	 * @required
	 * @readonly
	 */
	protected File basedir;

	public void writeVersion(ArtifactVersion newVersion) throws MojoExecutionException {
        if ( project.getOriginalModel().getVersion() != null ) {
			executeMojo(
					plugin( groupId("org.codehaus.mojo"),
							artifactId("versions-maven-plugin"),
							version(versionsPluginVersion)),
					goal("set"), 
					configuration(
					        element(name("newVersion"), newVersion.toString())
					        ),
					executionEnvironment(project, session, pluginManager));
        }
	}

	public void bumpProperties() throws MojoExecutionException {
		executeMojo(
			plugin( groupId("org.codehaus.mojo"),
					artifactId("versions-maven-plugin"),
					version(versionsPluginVersion)),
			goal("update-properties"), 
			configuration(),
			executionEnvironment(project, session, pluginManager));
	}

	public void executeStandardUpdateGoals() throws MojoExecutionException {
		List<String> goals = Arrays.asList("update-parent", "update-child-modules", "use-releases");
		for(String goal : goals) {			
			executeMojo(
					plugin( groupId("org.codehaus.mojo"),
							artifactId("versions-maven-plugin"),
							version(versionsPluginVersion)),
							goal(goal), 
							configuration(),
							executionEnvironment(project, session, pluginManager));
		}
		
	}
	

	protected boolean isRootProject() {
		return session.getExecutionRootDirectory().equalsIgnoreCase(basedir.toString());
	}
}