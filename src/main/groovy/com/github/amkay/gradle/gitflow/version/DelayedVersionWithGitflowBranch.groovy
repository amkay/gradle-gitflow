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

import com.github.zafarkhaja.semver.Version
import com.github.zafarkhaja.semver.expr.Expression
import org.ajoberstar.grgit.Grgit
import org.gradle.api.Project

import static com.github.amkay.gradle.gitflow.GitflowPlugin.EXT_GITFLOW
import static com.github.amkay.gradle.gitflow.strategy.Strategy.STRATEGIES

/**
 * A helper class with the API of {@link Version} that uses the available strategies to infer the current version
 * <strong>lazily</strong>.
 *
 * @author Max Kaeufer
 */
class DelayedVersionWithGitflowBranch {

    private final Project project;
    private Grgit grgit;
    private VersionWithGitflowBranch delegate;

    /**
     * @param project the project that the plugin was applied on
     */
    DelayedVersionWithGitflowBranch(final Project project) {
        this.project = project;
    }


    private void infer() {
        // Double-checked locking
        if (delegate == null) {
            synchronized (this) {
                if (delegate == null) {
                    grgit = Grgit.open(dir: project[ EXT_GITFLOW ].repositoryRoot)

                    delegate = STRATEGIES.find { it.canInfer(grgit) }.infer(grgit, project[ EXT_GITFLOW ])
                }
            }
        }
    }

    int compareWithBuildsTo(final Version other) {
        infer();

        return delegate.compareWithBuildsTo(other)
    }

    boolean satisfies(final String expr) {
        infer();

        return delegate.satisfies(expr)
    }

    boolean satisfies(final Expression expr) {
        infer();

        return delegate.satisfies(expr)
    }

    Version incrementMajorVersion() {
        infer();

        return delegate.incrementMajorVersion()
    }

    Version incrementMajorVersion(final String preRelease) {
        infer();

        return delegate.incrementMajorVersion(preRelease)
    }

    Version incrementMinorVersion() {
        infer();

        return delegate.incrementMinorVersion()
    }

    Version incrementMinorVersion(final String preRelease) {
        infer();

        return delegate.incrementMinorVersion(preRelease)
    }

    Version incrementPatchVersion() {
        infer();

        return delegate.incrementPatchVersion()
    }

    Version incrementPatchVersion(final String preRelease) {
        infer();

        return delegate.incrementPatchVersion(preRelease)
    }

    Version incrementPreReleaseVersion() {
        infer();

        return delegate.incrementPreReleaseVersion()
    }

    Version incrementBuildMetadata() {
        infer();

        return delegate.incrementBuildMetadata()
    }

    Version setPreReleaseVersion(final String preRelease) {
        infer();

        return delegate.setPreReleaseVersion(preRelease)
    }

    Version setBuildMetadata(final String build) {
        infer();

        return delegate.setBuildMetadata(build)
    }

    int getMajorVersion() {
        infer();

        return delegate.getMajorVersion()
    }

    int getMinorVersion() {
        infer();

        return delegate.getMinorVersion()
    }

    int getPatchVersion() {
        infer();

        return delegate.getPatchVersion()
    }

    String getNormalVersion() {
        infer();

        return delegate.getNormalVersion()
    }

    String getPreReleaseVersion() {
        infer();

        return delegate.getPreReleaseVersion()
    }

    String getBuildMetadata() {
        infer();

        return delegate.getBuildMetadata()
    }

    boolean greaterThan(final Version other) {
        infer();

        return delegate.greaterThan(other)
    }

    boolean greaterThanOrEqualTo(final Version other) {
        infer();

        return delegate.greaterThanOrEqualTo(other)
    }

    boolean lessThan(final Version other) {
        infer();

        return delegate.lessThan(other)
    }

    boolean lessThanOrEqualTo(final Version other) {
        infer();

        return delegate.lessThanOrEqualTo(other)
    }

    GitflowBranch getBranch() {
        infer()

        delegate.branch
    }

    @Override
    boolean equals(final Object other) {
        infer();

        return delegate.equals(other)
    }

    @Override
    int hashCode() {
        infer();

        return delegate.hashCode()
    }

    @Override
    String toString() {
        infer();

        return delegate.toString()
    }

    int compareTo(final Version other) {
        infer();

        return delegate.compareTo(other)
    }

}
