package jo.release;

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

import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal tag-release
 * @execute phase="initialize"
 */

public class TagReleaseMojo extends AbstractVersionModMojo {

	/**
	 * Base directory of the project.
	 * 
	 * @parameter expression="${gitDir}" default-value="${basedir}"
	 * @required
	 * @readonly
	 */
	private File basedir;
	
	public void execute() throws MojoExecutionException {
        tag();
	}

	private void tag() throws MojoExecutionException {
		
		boolean isRootProject = session.getExecutionRootDirectory().equalsIgnoreCase(basedir.toString());
        if(!isRootProject) {
        	return;
        }

        // Commit the version bump
		String message = "Move to release versions for release " + project.getVersion(); 
		executeMojo(
			plugin( groupId("org.apache.maven.plugins"),
					artifactId("maven-scm-plugin"),
					version(scmPluginVersion)),
			goal("checkin"), 
			configuration(
			        element(name("basedir"), basedir.getAbsolutePath()),
			        element(name("message"), message)
			        ),
			executionEnvironment(project, session, pluginManager));
		 
		//Commit tag
		executeMojo(
			plugin( groupId("org.apache.maven.plugins"),
					artifactId("maven-scm-plugin"),
					version(scmPluginVersion)),
			goal("tag"), 
			configuration(
			        element(name("basedir"), basedir.getAbsolutePath()),
			        element(name("tag"), project.getArtifactId()+"-"+project.getVersion())
			        ),
			executionEnvironment(project, session, pluginManager));
		
	}
}