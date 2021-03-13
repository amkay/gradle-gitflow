# gradle-gitflow

## How to publish a new version

Due to the way EDM team set up authentication with Artifactory, it is not possible for projects to fetch gradle plugins from it. (See https://jira.cac.washington.edu/browse/CAB-4237 for details). Instead, builds for this plugin are statically kepts in a GitHub repository: https://github.com/uw-it-edm/edm-artifacts.

If there is a need to upload a new version:

1. Set the new version in `/gradle/maven-publish-defaults.gradle`.
2. Run publish task to your maven local. Note that the build files will be under `/Users/<user_name>/.m2/repository/edu/uw/concert/gradle-gitflow`.

```
./gradlew publishToMavenLocal
```

3. Copy the output of the build into the edm-artifacts repo (following the version convention).
4. Update file `https://github.com/uw-it-edm/edm-artifacts/blob/master/edu/uw/concert/gradle-gitflow/maven-metadata.xml` with the new version information (you can use `/Users/<user_name>/.m2/repository/edu/uw/concert/gradle-gitflow/maven-metadata-local.xml` as a reference).
5. Commit changes to the edm-artifacts repo.
