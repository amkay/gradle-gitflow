gradle-gitflow
===============

An opinionated plugin that provides [Semantic Versioning](http://semver.org/) for [Gradle](https://gradle.org/) projects that use [Gitflow](http://nvie.com/posts/a-successful-git-branching-model/).

This plugin is heavily inspired by the [gradle-git](/ajoberstar/gradle-git) plugin.

It **intentionally** provides no Gradle tasks and interacts with [Git](http://git-scm.com/) just for inferring the version of the project.
This inferred version is set as the project version of the [Gradle](https://gradle.org/) project to which it is applied.



Versioning
-----------

### Version rules

The rules applied when inferring the version are that simple:

* If the current commit is tagged this tag is used as a version -- regardless of the current branch
    * this would usually be the normal version (`major.minor.patch`)
* Otherwise the version contains
    * the normal version (`major.minor.patch`) extracted from the next reachable tag in the history
    * a *pre-release identifier* indicating the current branch
    * a *pre-release identifier* indicating the number of commits since the last tag
    * a *build metadata identifier* indicating the SHA of the current commit
    * a *build metadata identifier* indicating if the repository is dirty


### Version structure

The inferred versions consist of the following components:

```
1.2.3-dev.65+sha.9066228.dirty
| | |  |  |   |     |      |
| | |  |  |   |     |      indicates if the repository is dirty
| | |  |  |   |     |
| | |  |  |   |     abbreviated SHA of the current commit
| | |  |  |   |
| | |  |  |   prefix of the SHA
| | |  |  |
| | |  |  # of commits since the last tag
| | |  |
| | |  denotes the current branch
| | |
| | patch version
| |
| minor version
|
major version
```


### Mapping between Gitflow branch and pre-release identifier

The following table shows the mapping between a Gitflow branch and the corresponding pre-release identifier that indicates this branch.
Note, that if you use a Gitflow plugin for Git, *gradle-gitflow* uses the branch names of your Gitflow configuration section in `.git/config`.

| Gitflow branch             | Default name in Gitflow plugins | Pre-release identifier | Notes                                                                      |
| -------------------------- | ------------------------------- | ---------------------- | -------------------------------------------------------------------------- |
| production release         | `master`                        | (empty string)         |                                                                            |
| development                | `develop`                       | `dev`                  |                                                                            |
| feature                    | `feature/foo`                   | `feature.foo`          |                                                                            |
| next release / pre-release | `release/1.2.3`                 | `pre`                  | The normal version (`major.minor.patch`) is extracted from the branch name |
| hotfix                     | `hotfix/foo`                    | `fix.foo`              |                                                                            |
| support                    | `support/foo`                   | `support.foo`          |                                                                            |

In case the current head is a *detached head* the *pre-release identifier* is `detached`.



Usage
------

Include the following in the project on which you want to apply the plugin:

```groovy
buildscript {
  repositories {
    jcenter()
  }

  dependencies {
    classpath 'com.github.amkay:gradle-gitflow:0.1.5'
  }
}

apply plugin: 'com.github.amkay.gitflow'
```


### Configuration

The plugin registers an extension object named `gitflow` that is the entry point to the configuration.
It also provides a DSL for keyword-based configuration, but configuration via properties is also possible.
The following example shows all configuration options:

```groovy
gitflow {
  // Use a different Git repository
  repositoryRoot 'foo/bar' // defaults to project.projectDir

  // Pre-release identifiers based on Gitflow branches
  preReleaseIds {
    release 'foo'      // defaults to ''
    develop 'foo'      // defaults to 'dev'
    preRelease 'foo'   // defaults to 'pre'
    detachedHead 'foo' // defaults to 'detached'

    // The following ones are used as prefixes
    feature 'foo' // defaults to 'feature'
    hotfix 'foo'  // defaults to 'fix'
    support 'foo' // defaults to 'support'
  }

  // Build metadata identifiers that are used as static text
  buildMetadataIds {
    sha 'foo'   // Defaults to 'sha'
    dirty 'foo' // Defaults to 'dirty'
  }
}
```


### Additional functionality of the version object

The following additional functionality is provided by the version object that this plugin provides.



#### Version type

The type of the inferred version is derived from the current branch and corresponds to the branches mentioned in [Mapping between Gitflow branch and pre-release identifier](#mapping-between-gitflow-branch-and-pre-release-identifier).
This can be used in your buildscripts for distinguishing between development and production builds, e.g. for deploying to staging and production as seen in the following example.

```groovy
import static com.github.amkay.gradle.gitflow.version.VersionType.*

task deploy << {
    if (version.type == DEVELOP) {
        // Deploy to staging...
    } else if (version.type == RELEASE) {
        // Deploy to production...
    }
}
```
