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
package com.github.amkay.gradle.gitflow.version

import com.github.zafarkhaja.semver.Version
import com.github.zafarkhaja.semver.expr.Expression
import org.ajoberstar.grgit.Grgit
import org.gradle.api.Project

import static com.github.amkay.gradle.gitflow.GitflowPlugin.EXT_GITFLOW
import static com.github.amkay.gradle.gitflow.strategy.Strategy.STRATEGIES

/**
 * A helper class with the API of {@link Version} that uses the available strategies to infer the current version
 * <strong>lazily</strong>.
 * This class delegates to {@link VersionWithType}.
 *
 * @author Max Käufer
 */
class DelayedVersionWithType implements Comparable<Version> {

    private final Project         project
    private       Grgit           grgit
    private       VersionWithType delegate
    private final Object          lock = new Object()

    /**
     * @param project the project that the plugin was applied on
     */
    DelayedVersionWithType(final Project project) {
        this.project = project
    }


    private void infer() {
        // Double-checked locking
        if (!delegate) {
            synchronized (lock) {
                if (!delegate) {
                    grgit = Grgit.open dir: project[ EXT_GITFLOW ].repositoryRoot

                    delegate = STRATEGIES.find { it.canInfer grgit }.infer grgit, project[ EXT_GITFLOW ]
                }
            }
        }
    }

    /**
     * See {@link VersionWithType#compareWithBuildsTo(Version)}.
     * @param other
     * @return
     */
    int compareWithBuildsTo(final Version other) {
        infer()

        delegate.compareWithBuildsTo other
    }

    /**
     * See {@link VersionWithType#satisfies(String)}.
     * @param expr
     * @return
     */
    boolean satisfies(final String expr) {
        infer()

        delegate.satisfies expr
    }

    /**
     * See {@link VersionWithType#satisfies(Expression)}.
     * @param expr
     * @return
     */
    boolean satisfies(final Expression expr) {
        infer()

        delegate.satisfies expr
    }

    /**
     * See {@link VersionWithType#incrementMajorVersion()}.
     * @return
     */
    Version incrementMajorVersion() {
        infer()

        delegate.incrementMajorVersion()
    }

    /**
     * See {@link VersionWithType#incrementMajorVersion(String)}.
     * @param preRelease
     * @return
     */
    Version incrementMajorVersion(final String preRelease) {
        infer()

        delegate.incrementMajorVersion preRelease
    }

    /**
     * See {@link VersionWithType#incrementMinorVersion()}.
     * @return
     */
    Version incrementMinorVersion() {
        infer()

        delegate.incrementMinorVersion()
    }

    /**
     * See {@link VersionWithType#incrementMinorVersion(String)}.
     * @param preRelease
     * @return
     */
    Version incrementMinorVersion(final String preRelease) {
        infer()

        delegate.incrementMinorVersion preRelease
    }

    /**
     * See {@link VersionWithType#incrementPatchVersion()}.
     * @return
     */
    Version incrementPatchVersion() {
        infer()

        delegate.incrementPatchVersion()
    }

    /**
     * See {@link VersionWithType#incrementPatchVersion(String)}.
     * @param preRelease
     * @return
     */
    Version incrementPatchVersion(final String preRelease) {
        infer()

        delegate.incrementPatchVersion preRelease
    }

    /**
     * See {@link VersionWithType#incrementPreReleaseVersion()}.
     * @return
     */
    Version incrementPreReleaseVersion() {
        infer()

        delegate.incrementPreReleaseVersion()
    }

    /**
     * See {@link VersionWithType#incrementBuildMetadata()}.
     * @return
     */
    Version incrementBuildMetadata() {
        infer()

        delegate.incrementBuildMetadata()
    }

    /**
     * See {@link VersionWithType#setPreReleaseVersion(String)}.
     * @param preRelease
     * @return
     */
    Version setPreReleaseVersion(final String preRelease) {
        infer()

        delegate.setPreReleaseVersion preRelease
    }

    /**
     * See {@link VersionWithType#setBuildMetadata(String)}.
     * @param build
     * @return
     */
    Version setBuildMetadata(final String build) {
        infer()

        delegate.setBuildMetadata build
    }

    /**
     * See {@link VersionWithType#getMajorVersion()}.
     * @return
     */
    int getMajorVersion() {
        infer()

        delegate.majorVersion
    }

    /**
     * See {@link VersionWithType#getMinorVersion()}.
     * @return
     */
    int getMinorVersion() {
        infer()

        delegate.minorVersion
    }

    /**
     * See {@link VersionWithType#getPatchVersion()}.
     * @return
     */
    int getPatchVersion() {
        infer()

        delegate.patchVersion
    }

    /**
     * See {@link VersionWithType#getNormalVersion()}.
     * @return
     */
    String getNormalVersion() {
        infer()

        delegate.normalVersion
    }

    /**
     * See {@link VersionWithType#getPreReleaseVersion()}.
     * @return
     */
    String getPreReleaseVersion() {
        infer()

        delegate.preReleaseVersion
    }

    /**
     * See {@link VersionWithType#getBuildMetadata()}.
     * @return
     */
    String getBuildMetadata() {
        infer()

        delegate.buildMetadata
    }

    /**
     * See {@link VersionWithType#greaterThan(Version)}.
     * @param other
     * @return
     */
    boolean greaterThan(final Version other) {
        infer()

        delegate.greaterThan other
    }

    /**
     * See {@link VersionWithType#greaterThanOrEqualTo(Version)}.
     * @param other
     * @return
     */
    boolean greaterThanOrEqualTo(final Version other) {
        infer()

        delegate.greaterThanOrEqualTo other
    }

    /**
     * See {@link VersionWithType#lessThan(Version)}.
     * @param other
     * @return
     */
    boolean lessThan(final Version other) {
        infer()

        delegate.lessThan other
    }

    /**
     * See {@link VersionWithType#lessThanOrEqualTo(Version)}.
     * @param other
     * @return
     */
    boolean lessThanOrEqualTo(final Version other) {
        infer()

        delegate.lessThanOrEqualTo other
    }

    /**
     * @return the type of the version
     */
    VersionType getType() {
        infer()

        delegate.type
    }

    /**
     * See {@link Object#equals(Object)}.
     * @param other
     * @return
     */
    @Override
    boolean equals(final Object other) {
        infer()

        delegate == other
    }

    /**
     * See {@link Object#hashCode()}.
     * @return
     */
    @Override
    int hashCode() {
        infer()

        delegate.hashCode()
    }

    /**
     * See {@link Object#toString()}.
     * @return
     */
    @Override
    String toString() {
        infer()

        delegate.toString()
    }

    /**
     * See {@link Comparable#compareTo(Object)}
     * @param other
     * @return
     */
    @Override
    int compareTo(final Version other) {
        infer()

        delegate <=> other
    }

}
