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
package edu.uw.concert.gradle.gitflow.strategy

import edu.uw.concert.gradle.gitflow.version.VersionWithType
import edu.uw.concert.gradle.gitflow.dsl.GitflowPluginExtension
import org.ajoberstar.grgit.Grgit
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

/**
 * The base class of all strategies used to infer the version. This is a helper class that holds common functionality
 * of all strategies like logging and retrieving branch names and prefixes from the config of Git.
 *
 * @author Max Käufer
 */
abstract class AbstractStrategy implements Strategy {

    private static final Logger LOGGER = Logging.getLogger(AbstractStrategy)

    /**
     * The section of Gitflow's configuration in <code>.git/config</code>
     */
    public static final String SECTION_GITFLOW = 'gitflow'

    /**
     * The subsection containing Gitflow's branch prefixes in <code>.git/config</code>
     */
    public static final String SUBSECTION_PREFIX = 'prefix'

    /**
     * The subsection containing Gitflow's branch names in <code>.git/config</code>
     */
    public static final String SUBSECTION_BRANCH = 'branch'

    /**
     * See {@link Strategy#infer(Grgit, edu.uw.concert.gradle.gitflow.dsl.GitflowPluginExtension)}.
     * This method applies the template method pattern to
     * log the inferred version.
     * @param grgit
     * @param extension
     * @return
     */
    @Override
    VersionWithType infer(final Grgit grgit, final GitflowPluginExtension extension) {
        def version = doInfer grgit, extension

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
    abstract protected VersionWithType doInfer(final Grgit grgit, final GitflowPluginExtension extension)

    /**
     * Helper method to retrieve a Gitflow branch prefix from .git/config
     *
     * @param grgit
     * @param name
     * @return
     */
    protected static String getPrefix(final Grgit grgit, final String name) {
        grgit.repository.jgit.repository.config.getString SECTION_GITFLOW, SUBSECTION_PREFIX, name
    }

    /**
     * Helper method to retrieve a Gitflow branch name from .git/config
     *
     * @param grgit
     * @param name
     * @return
     */
    protected static String getBranchName(final Grgit grgit, final String name) {
        grgit.repository.jgit.repository.config.getString SECTION_GITFLOW, SUBSECTION_BRANCH, name
    }

}
