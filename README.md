release-version-plugin
======================

Some helpful plugins for bumping versions for release and to the next snapshot version

This project contains two goals. 

## release-versions 

This moves versions in the project to release versions. When updating the 
parent versions and versions specified in properties it will update to the latest available 
release. 

## snapshot-versions

This updates the version of the current project to the next snapshot.

# Usage

A typical deployment would be:

```
mvn org.jclarity:release-version:release-versions
mvn clean deploy
mvn clean org.jclarity:release-version:snapshot-versions
```

Of course you can add it to the build section of your pom to reduce the command line:

```
<build>
	<plugins>
		<plugin>
			<groupId>jo.release</groupId>
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
