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
package com.github.amkay.gradle.gitflow.strategy

import com.github.amkay.gradle.gitflow.dsl.GitflowPluginExtension
import com.github.amkay.gradle.gitflow.version.VersionWithType
import org.ajoberstar.grgit.Grgit
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

/**
 * The base class of all strategies used to infer the version.
 *
 * @author Max Kaeufer
 */
abstract class AbstractStrategy {

    private static final Logger LOGGER = Logging.getLogger(AbstractStrategy);

    /**
     * All available strategies.
     */
    public static final STRATEGIES = [
      new BranchDevelopStrategy(),
      new BranchReleaseStrategy(),
      new BranchPreReleaseStrategy(),
      new BranchFeatureStrategy(),
      new BranchHotfixStrategy(),
      new BranchSupportStrategy(),
      // Must go to last position, see DetachedHeadStrategy.canInfer(Grgit) for that
      new DetachedHeadStrategy()
    ]

    /**
     * The section of Gitflow's configuration in .git/config
     */
    public static final String SECTION_GITFLOW = 'gitflow'

    /**
     * The subsection containing Gitflow's branch prefixes in .git/config
     */
    public static final String SUBSECTION_PREFIX = 'prefix'

    /**
     * The subsection containing Gitflow's branch names in .git/config
     */
    public static final String SUBSECTION_BRANCH = 'branch'

    /**
     * Infers the current version. This method is called by {@link com.github.amkay.gradle.gitflow.GitflowPlugin}
     * and applies the template method plugin to log the inferred version.
     *
     * @param grgit
     * @param extension
     * @return
     */
    VersionWithType infer(final Grgit grgit, final GitflowPluginExtension extension) {
        def version = doInfer(grgit, extension)

        LOGGER.lifecycle "Inferred version $version"

        version
    }

    /**
     * Infers the current version. This is the main method of this plugin and is implemented by the various
     * strategies available.
     *
     * @param grgit
     * @param extension
     * @return
     */
    abstract protected VersionWithType doInfer(Grgit grgit, GitflowPluginExtension extension)

    /**
     * Determines if the strategy can infer the version. This is used to match the current branch, for example.
     *
     * @param grgit
     * @return
     */
    abstract boolean canInfer(Grgit grgit)

    /**
     * Helper method to retrieve a Gitflow branch prefix from .git/config
     *
     * @param grgit
     * @param name
     * @return
     */
    protected static String getPrefix(final Grgit grgit, final String name) {
        grgit.repository.jgit.repository.config.getString(SECTION_GITFLOW, SUBSECTION_PREFIX, name)
    }

    /**
     * Helper method to retrieve a Gitflow branch name from .git/config
     *
     * @param grgit
     * @param name
     * @return
     */
    protected static String getBranchName(final Grgit grgit, final String name) {
        grgit.repository.jgit.repository.config.getString(SECTION_GITFLOW, SUBSECTION_BRANCH, name)
    }

}
