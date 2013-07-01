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
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal release-versions
 * @execute lifecycle="release-versions" phase="initialize"
 */
public class MoveToReleaseMojo extends AbstractVersionModMojo {

	/**
	 * Weather or not to update versions held in properties too. Careful with
	 * this, it will aggressively update to the most recent version available
	 * for that dependency.
	 * 
	 * @parameter expression="${includeProperties}" default-value="false"
	 */
	public Boolean includeProperties = false;

	public void execute() throws MojoExecutionException {
		if(includeProperties) {
			executeMojo(
				plugin( groupId("org.codehaus.mojo"),
						artifactId("versions-maven-plugin"),
						version(versionsPluginVersion)),
				goal("update-properties"), 
				configuration(),
				executionEnvironment(project, session, pluginManager));
		}

		ArtifactVersion artifactVersion = new Version(project.getVersion());
		Version newVersion = new Version(artifactVersion.getMajorVersion(),
				artifactVersion.getMinorVersion(),
				artifactVersion.getIncrementalVersion(),
				artifactVersion.getBuildNumber(), null);
		writeVersion(newVersion);
	}

}