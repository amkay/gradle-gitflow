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
package edu.uw.concert.gradle.gitflow.dsl

/**
 * The Gradle plugin extension. This is the entry point of the <em>DSL</em> to configure the plugin.
 *
 * @author Max Käufer
 */
class GitflowPluginExtension {

    /**
     * The root directory of the repository to use
     */
    String repositoryRoot

    /**
     * Holder that allows to configure the <em>pre-release identifiers</em> according to <em>semantic versioning</em>
     */
    final PreReleaseIdentifiers preReleaseIds = new PreReleaseIdentifiers()

    /**
     * Holder that allows to configure the <em>build metadata identifiers</em> according to <em>semantic versioning</em>
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
     * Helper method to allow the <em>keyword-based configuration (the DSL)</em> of the <em>pre-release
     * identifiers</em> (<code>preReleaseIds</code>) according to <em>semantic versioning</em>.
     *
     * @param cl
     */
    @SuppressWarnings('ConfusingMethodName')
    void preReleaseIds(@DelegatesTo(PreReleaseIdentifiers) final Closure cl) {
        cl.delegate = preReleaseIds
        cl.call()
    }

    /**
     * Helper method to allow the <em>keyword-based configuration (the DSL)</em> of the <em>build metadata identifiers
     * </em> (<code>buildMetadataIds</code>) according to <em>semantic versioning</em>.
     *
     * @param cl
     */
    @SuppressWarnings([ 'ConfusingMethodName', 'BuilderMethodWithSideEffects', 'FactoryMethodName' ])
    void buildMetadataIds(@DelegatesTo(BuildMetadataIdentifiers) final Closure cl) {
        cl.delegate = buildMetadataIds
        cl.call()
    }

}
