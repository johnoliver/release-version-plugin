release-version-plugin
======================

Some helpful plugins for bumping versions for release and to the next snapshot version. Allowing releases with only a single full build lifecycle and avoiding the repeated builds of the standard release plugin.

This project is still somewhat in beta stages. Although this has proven to work well with versions defined via properties, some issues have been found, particularly bumping `SNAPSHOT` versions defined in the `<dependency>` section that refer to modules within the same reactor build.

This project contains two goals. 

## release-versions 

This moves versions in the project to release versions. When updating the 
parent versions it will update to the latest available release. 

## snapshot-versions

This updates the version of the current project to the next snapshot.

# Usage

A typical deployment would be:

```
mvn com.insightfullogic.release:release-version-plugin:release-versions
mvn clean deploy
mvn clean com.insightfullogic.release:release-version-plugin:snapshot-versions
```

A full build script including depolying a release, taging the release and moving to the next snapshot build would be:

```
PLUGIN_VERSION=1.1.6

git checkout master
git pull origin master
mvn clean
mvn com.insightfullogic.release:release-version-plugin:$PLUGIN_VERSION:release-versions -Drv.processProperties=true
mvn clean deploy -DupdateReleaseInfo=true
git commit --allow-empty -a -m "Move to release versions"
mvn com.insightfullogic.release:release-version-plugin:$PLUGIN_VERSION:tag-release
mvn com.insightfullogic.release:release-version-plugin:$PLUGIN_VERSION:snapshot-versions
git commit --allow-empty -a -m "Move to snapshot versions"
git push origin master
```

As opposed to the standard maven release plugin, only the deploy in this release process requires the full build and test.


Of course you can add it to the build section of your pom to reduce the command line:

```
<build>
	<plugins>
		<plugin>
			<groupId>com.insightfullogic.release</groupId>
			<artifactId>release-version-plugin</artifactId>
			<version>1.0-SNAPSHOT</version>
		</plugin>
	</plugins>
</build>
```
Then the goal can simply be invoked with.

```
mvn release-version:release-versions
mvn release-version:snapshot-versions
``` 
