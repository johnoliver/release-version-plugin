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

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal snapshot-versions
 * @execute phase="initialize"
 */
public class MoveToSnapshotMojo extends AbstractVersionModMojo {

	public void execute() throws MojoExecutionException {

        ArtifactVersion artifactVersion = new Version( project.getVersion() );
        Version newVersion = new Version(    artifactVersion.getMajorVersion(), 
						        			 artifactVersion.getMinorVersion(),
						        			 artifactVersion.getIncrementalVersion()+1,
						        			 artifactVersion.getBuildNumber(),
						        			 "SNAPSHOT");
        writeVersion(newVersion);
	}
}