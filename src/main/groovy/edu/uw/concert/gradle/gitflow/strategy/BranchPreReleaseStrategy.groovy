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

import edu.uw.concert.gradle.gitflow.dsl.GitflowPluginExtension
import edu.uw.concert.gradle.gitflow.version.VersionWithType
import edu.uw.concert.gradle.gitflow.version.VersionWithTypeBuilder
import edu.uw.concert.gradle.gitflow.version.NearestVersionLocator
import org.ajoberstar.grgit.Grgit

import static edu.uw.concert.gradle.gitflow.version.VersionType.PRE_RELEASE

/**
 * The strategy to use when one of Gitflow's <strong>pre-release / next release</strong> branches is the current branch.
 *
 * @author Max Käufer
 */
class BranchPreReleaseStrategy extends AbstractStrategy implements Strategy {

    private static final String CONFIG_PREFIX_PRE_RELEASE  = 'release'
    private static final String DEFAULT_PREFIX_PRE_RELEASE = 'release/'

    /**
     * See {@link AbstractStrategy#doInfer(Grgit, GitflowPluginExtension)}.
     * @param grgit
     * @param ext
     * @return
     */
    @Override
    protected VersionWithType doInfer(final Grgit grgit, final GitflowPluginExtension ext) {
        def nearestVersion = new NearestVersionLocator().locate grgit

        def matcher = (grgit.branch.current.name =~ $/^${getReleasePrefix grgit}(.*)/$)
        String releaseVersion = matcher[ 0 ][ 1 ]

        new VersionWithTypeBuilder(releaseVersion)
          .branch(ext.preReleaseIds.preRelease)
          .distanceFromRelease(nearestVersion)
          .sha(grgit, ext)
          .dirty(grgit, ext)
          .type(PRE_RELEASE)
          .build()
    }

    /**
     * See {@link Strategy#canInfer(Grgit)}.
     * @param grgit
     * @return
     */
    @Override
    boolean canInfer(final Grgit grgit) {
        grgit.branch.current.name.startsWith getReleasePrefix(grgit)
    }

    private static String getReleasePrefix(final Grgit grgit) {
        getPrefix(grgit, CONFIG_PREFIX_PRE_RELEASE) ?: DEFAULT_PREFIX_PRE_RELEASE
    }

}
