/*
 * Copyright 2015 Max Käufer
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
package edu.uw.concert.gradle.gitflow.version

import com.github.zafarkhaja.semver.Version
import edu.uw.concert.gradle.gitflow.dsl.GitflowPluginExtension
import org.ajoberstar.grgit.Grgit

/**
 * A helper class that uses the <em>Builder Pattern</em> to create a {@link VersionWithType}.
 *
 * @author Max Käufer
 */
class VersionWithTypeBuilder {

    private  static final String SNAPSHOT_SUFFIX = '-SNAPSHOT'

    private final NearestVersion nearestVersion
    private final String         normal
    private       String         branch
    private       int            distanceFromRelease
    private       String         sha
    private       String         dirty
    private       VersionType    type

    /**
     * @param normal the normal part of the version according to semantic versioning
     */
    VersionWithTypeBuilder(final String normal) {
        this.normal = normal
    }

    /**
     * Creates a new instance using the normal part of the given {@link NearestVersion} that allows to use all other
     * methods in this class that <strong>do not</strong> require a {@link NearestVersion} argument.
     *
     * @param nearestVersion
     */
    VersionWithTypeBuilder(final NearestVersion nearestVersion) {
        this.nearestVersion = nearestVersion
        this.normal = nearestVersion.any.toString()
    }

    /**
     * Sets the branch part of the version
     *
     * @param branch
     * @return
     */
    @SuppressWarnings('ConfusingMethodName')
    VersionWithTypeBuilder branch(final String branch) {
        this.branch = branch

        this
    }

    /**
     * Sets the distance to the last release tag
     *
     * @param nearestVersion
     * @return
     */
    @SuppressWarnings('ConfusingMethodName')
    VersionWithTypeBuilder distanceFromRelease(final NearestVersion nearestVersion) {
        distanceFromRelease = nearestVersion.distanceFromAny

        this
    }

    /**
     * Sets the distance to the last release tag
     *
     * @return
     */
    @SuppressWarnings('ConfusingMethodName')
    VersionWithTypeBuilder distanceFromRelease() {
        distanceFromRelease nearestVersion
    }

    /**
     * Sets the sha part of the version
     *
     * @param grgit
     * @param extension
     * @return
     */
    @SuppressWarnings('ConfusingMethodName')
    VersionWithTypeBuilder sha(final Grgit grgit, final GitflowPluginExtension extension) {
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
    @SuppressWarnings('ConfusingMethodName')
    VersionWithTypeBuilder dirty(final Grgit grgit, final GitflowPluginExtension extension) {
        if (!grgit.status().clean) {
            dirty = extension.buildMetadataIds.dirty
        }

        this
    }

    /**
     * Sets the type of the version.
     *
     * @param type
     * @return
     */
    @SuppressWarnings('ConfusingMethodName')
    VersionWithTypeBuilder type(final VersionType type) {
        this.type = type

        this
    }

    /**
     * Builds the version
     *
     * @return
     */
    VersionWithType build() {
        def preRelease = new StringBuilder()
        def buildMetadata = new StringBuilder()

        if (distanceFromRelease) {
            append preRelease, branch
            //append preRelease, Integer.toString(distanceFromRelease)
            //append buildMetadata, sha
        }
        append buildMetadata, dirty

        if (type != VersionType.RELEASE ) {
            preRelease.append(SNAPSHOT_SUFFIX)
        }

        def version = new Version.Builder(normal)
          .setPreReleaseVersion(preRelease.toString())
          .setBuildMetadata(buildMetadata.toString())
          .build()

        def isDirty = false
        if (dirty) {
            isDirty = true
        }
        new VersionWithType(version, type, isDirty)
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
