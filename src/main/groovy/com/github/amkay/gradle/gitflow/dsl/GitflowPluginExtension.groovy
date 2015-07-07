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
package com.github.amkay.gradle.gitflow.dsl

/**
 * The Gradle plugin extension. This is the entry point of the DSL to configure the plugin.
 *
 * @author Max Käufer
 */
class GitflowPluginExtension {

    /**
     * The root directory of the repository to use
     */
    String repositoryRoot

    /**
     * Holder that allows to configure the pre-release identifiers according to semantic versioning
     */
    final PreReleaseIdentifiers preReleaseIds = new PreReleaseIdentifiers()

    /**
     * Holder that allows to configure the build metadata identifiers according to semantic versioning
     */
    final BuildMetadataIdentifiers buildMetadataIds = new BuildMetadataIdentifiers()

    GitflowPluginExtension(final File projectDir) {
        setRepositoryRoot projectDir.path
    }

    /**
     * Helper method to allow keyword-based configuration of the <code>repositoryRoot</code> property
     * @param repositoryRoot
     */
    @SuppressWarnings('ConfusingMethodName')
    void repositoryRoot(final String repositoryRoot) {
        setRepositoryRoot repositoryRoot
    }

    /**
     * Helper method to allow the keyword-based configuration (the DSL) of the pre-release identifiers according to
     * semantic versioning.
     *
     * @param cl
     */
    @SuppressWarnings('ConfusingMethodName')
    void preReleaseIds(@DelegatesTo(PreReleaseIdentifiers) final Closure cl) {
        cl.delegate = preReleaseIds;
        cl.call()
    }

    /**
     * Helper method to allow the keyword-based configuration (the DSL) of the build metadata identifiers according
     * to semantic versioning.
     *
     * @param cl
     */
    @SuppressWarnings('ConfusingMethodName')
    void buildMetadataIds(@DelegatesTo(BuildMetadataIdentifiers) final Closure cl) {
        cl.delegate = buildMetadataIds
        cl.call()
    }

}
