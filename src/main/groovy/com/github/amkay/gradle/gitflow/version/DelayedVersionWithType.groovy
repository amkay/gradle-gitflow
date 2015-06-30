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
import static com.github.amkay.gradle.gitflow.strategy.AbstractStrategy.STRATEGIES

/**
 * A helper class with the API of {@link Version} that uses the available strategies to infer the current version
 * <strong>lazily</strong>.
 *
 * @author Max Kaeufer
 */
class DelayedVersionWithType {

    private final Project project;
    private Grgit grgit;
    private VersionWithType delegate;

    /**
     * @param project the project that the plugin was applied on
     */
    DelayedVersionWithType(final Project project) {
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

    /**
     * See {@link Version}
     * @param other
     * @return
     */
    int compareWithBuildsTo(final Version other) {
        infer();

        return delegate.compareWithBuildsTo(other)
    }

    /**
     * See {@link Version}
     * @param expr
     * @return
     */
    boolean satisfies(final String expr) {
        infer();

        return delegate.satisfies(expr)
    }

    /**
     * See {@link Version}
     * @param expr
     * @return
     */
    boolean satisfies(final Expression expr) {
        infer();

        return delegate.satisfies(expr)
    }

    /**
     * See {@link Version}
     * @return
     */
    Version incrementMajorVersion() {
        infer();

        return delegate.incrementMajorVersion()
    }

    /**
     * See {@link Version}
     * @param preRelease
     * @return
     */
    Version incrementMajorVersion(final String preRelease) {
        infer();

        return delegate.incrementMajorVersion(preRelease)
    }

    /**
     * See {@link Version}
     * @return
     */
    Version incrementMinorVersion() {
        infer();

        return delegate.incrementMinorVersion()
    }

    /**
     * See {@link Version}
     * @param preRelease
     * @return
     */
    Version incrementMinorVersion(final String preRelease) {
        infer();

        return delegate.incrementMinorVersion(preRelease)
    }

    /**
     * See {@link Version}
     * @return
     */
    Version incrementPatchVersion() {
        infer();

        return delegate.incrementPatchVersion()
    }

    /**
     * See {@link Version}
     * @param preRelease
     * @return
     */
    Version incrementPatchVersion(final String preRelease) {
        infer();

        return delegate.incrementPatchVersion(preRelease)
    }

    /**
     * See {@link Version}
     * @return
     */
    Version incrementPreReleaseVersion() {
        infer();

        return delegate.incrementPreReleaseVersion()
    }

    /**
     * See {@link Version}
     * @return
     */
    Version incrementBuildMetadata() {
        infer();

        return delegate.incrementBuildMetadata()
    }

    /**
     * See {@link Version}
     * @param preRelease
     * @return
     */
    Version setPreReleaseVersion(final String preRelease) {
        infer();

        return delegate.setPreReleaseVersion(preRelease)
    }

    /**
     * See {@link Version}
     * @param build
     * @return
     */
    Version setBuildMetadata(final String build) {
        infer();

        return delegate.setBuildMetadata(build)
    }

    /**
     * See {@link Version}
     * @return
     */
    int getMajorVersion() {
        infer();

        return delegate.getMajorVersion()
    }

    /**
     * See {@link Version}
     * @return
     */
    int getMinorVersion() {
        infer();

        return delegate.getMinorVersion()
    }

    /**
     * See {@link Version}
     * @return
     */
    int getPatchVersion() {
        infer();

        return delegate.getPatchVersion()
    }

    /**
     * See {@link Version}
     * @return
     */
    String getNormalVersion() {
        infer();

        return delegate.getNormalVersion()
    }

    /**
     * See {@link Version}
     * @return
     */
    String getPreReleaseVersion() {
        infer();

        return delegate.getPreReleaseVersion()
    }

    /**
     * See {@link Version}
     * @return
     */
    String getBuildMetadata() {
        infer();

        return delegate.getBuildMetadata()
    }

    /**
     * See {@link Version}
     * @param other
     * @return
     */
    boolean greaterThan(final Version other) {
        infer();

        return delegate.greaterThan(other)
    }

    /**
     * See {@link Version}
     * @param other
     * @return
     */
    boolean greaterThanOrEqualTo(final Version other) {
        infer();

        return delegate.greaterThanOrEqualTo(other)
    }

    /**
     * See {@link Version}
     * @param other
     * @return
     */
    boolean lessThan(final Version other) {
        infer();

        return delegate.lessThan(other)
    }

    /**
     * See {@link Version}
     * @param other
     * @return
     */
    boolean lessThanOrEqualTo(final Version other) {
        infer();

        return delegate.lessThanOrEqualTo(other)
    }

    /**
     * @return the type of the version
     */
    VersionType getType() {
        infer()

        delegate.type
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
