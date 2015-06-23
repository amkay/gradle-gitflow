/*
 * Copyright 2015 Max Kaeufer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.amkay.gradle.gitflow.version

import com.github.amkay.gradle.gitflow.dsl.GitflowPluginExtension
import com.github.zafarkhaja.semver.Version
import org.ajoberstar.grgit.Grgit

/**
 * A helper class that uses the builder pattern to create a {@link VersionWithGitflowBranch}.
 *
 * @author Max Kaeufer
 */
class VersionWithGitflowBranchBuilder {

    private NearestVersion nearestVersion
    private String         normal
    private String         branch
    private int            distanceFromRelease
    private String         sha
    private String         dirty
    private GitflowBranch  gitflowBranch

    /**
     * @param normal the normal part of the version according to semantic versioning
     */
    VersionWithGitflowBranchBuilder(final String normal) {
        this.normal = normal
    }

    /**
     * Creates a new instance using the normal part of the given {@link NearestVersion} that allows to use all other
     * methods in this class that <strong>do not</strong> require a {@link NearestVersion} argument.
     *
     * @param nearestVersion
     */
    VersionWithGitflowBranchBuilder(final NearestVersion nearestVersion) {
        this.nearestVersion = nearestVersion
        this.normal = nearestVersion.any.toString()
    }

    /**
     * Sets the branch part of the version
     *
     * @param branch
     * @return
     */
    VersionWithGitflowBranchBuilder branch(final String branch) {
        this.branch = branch

        this
    }

    /**
     * Sets the distance to the last release tag
     *
     * @param nearestVersion
     * @return
     */
    VersionWithGitflowBranchBuilder distanceFromRelease(final NearestVersion nearestVersion) {
        distanceFromRelease = nearestVersion.distanceFromAny

        this
    }

    /**
     * Sets the distance to the last release tag
     *
     * @return
     */
    VersionWithGitflowBranchBuilder distanceFromRelease() {
        distanceFromRelease(nearestVersion)
    }

    /**
     * Sets the sha part of the version
     *
     * @param grgit
     * @param extension
     * @return
     */
    VersionWithGitflowBranchBuilder sha(final Grgit grgit, final GitflowPluginExtension extension) {
        def id = grgit.head().abbreviatedId

        sha = "${extension.buildMetadataIds.sha}.$id"

        this
    }

    /**
     * Sets the dirty part of the version
     *
     * @param grgit
     * @param extension
     * @return
     */
    VersionWithGitflowBranchBuilder dirty(final Grgit grgit, final GitflowPluginExtension extension) {
        if (!grgit.status().clean) {
            dirty = extension.buildMetadataIds.dirty
        }

        this
    }

    VersionWithGitflowBranchBuilder gitflowBranch(final GitflowBranch gitflowBranch) {
        this.gitflowBranch = gitflowBranch

        this
    }

    /**
     * Builds the version
     *
     * @return
     */
    VersionWithGitflowBranch build() {
        def preRelease = new StringBuilder()
        def buildMetadata = new StringBuilder()

        if (distanceFromRelease) {
            append preRelease, branch
            append preRelease, Integer.toString(distanceFromRelease)
            append buildMetadata, sha
        }
        append buildMetadata, dirty

        def version = new Version.Builder(normal)
          .setPreReleaseVersion(preRelease.toString())
          .setBuildMetadata(buildMetadata.toString())
          .build()

        new VersionWithGitflowBranch(version, gitflowBranch)
    }

    private void append(final StringBuilder sb, final String s) {
        if (!s) {
            return
        }

        if (sb.length()) {
            sb.append '.'
        }

        sb.append s
    }

}
