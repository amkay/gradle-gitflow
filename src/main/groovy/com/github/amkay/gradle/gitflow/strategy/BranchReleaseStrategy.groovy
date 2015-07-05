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
package com.github.amkay.gradle.gitflow.strategy

import com.github.amkay.gradle.gitflow.dsl.GitflowPluginExtension
import com.github.amkay.gradle.gitflow.version.NearestVersionLocator
import com.github.amkay.gradle.gitflow.version.VersionWithType
import com.github.amkay.gradle.gitflow.version.VersionWithTypeBuilder
import org.ajoberstar.grgit.Grgit

import static com.github.amkay.gradle.gitflow.version.VersionType.RELEASE

/**
 * The strategy to use when Gitflow's <code>release</code> branch is the current branch.
 *
 * @author Max Käufer
 */
class BranchReleaseStrategy extends AbstractStrategy {

    private static final String CONFIG_BRANCH_RELEASE  = 'master'
    private static final String DEFAULT_BRANCH_RELEASE = 'master'


    @Override
    protected VersionWithType doInfer(final Grgit grgit, final GitflowPluginExtension ext) {
        def nearestVersion = new NearestVersionLocator().locate grgit

        new VersionWithTypeBuilder(nearestVersion)
          .branch(ext.preReleaseIds.release)
          .distanceFromRelease()
          .sha(grgit, ext)
          .dirty(grgit, ext)
          .type(RELEASE)
          .build()
    }

    @Override
    boolean canInfer(final Grgit grgit) {
        grgit.branch.current.name == getMasterBranchName(grgit)
    }

    private static String getMasterBranchName(final Grgit grgit) {
        getBranchName(grgit, CONFIG_BRANCH_RELEASE) ?: DEFAULT_BRANCH_RELEASE
    }

}
