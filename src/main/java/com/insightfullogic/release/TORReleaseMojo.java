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

import java.util.List;
import java.util.Map;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * @goal release-all
 * @aggregator
 */
public class TORReleaseMojo extends AbstractMojo {

	/**
	 * The Maven Project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project = null;

	/**
	 * Path map of paths to dependencies
	 * 
	 * @parameter
	 * @readonly
	 */
	private Map<String, String> dependencyPaths;

	public void execute() throws MojoExecutionException {
		List<Dependency> dependencies = project.getDependencies();

		for (Dependency dependency : dependencies) {
			if (dependency.getVersion().contains("SNAPSHOT")) {
				String ga = dependency.getGroupId() + ":" + dependency.getArtifactId();
				String path = dependencyPaths.get(ga);
				if (path == null) {
					throw new MojoExecutionException("Unable to find path to release dependency: " + path);
				}

				
			}
		}
	}

}