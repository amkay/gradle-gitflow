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

/**
 * The interface for all strategies used to infer the version.
 * See the package
 * <a href="{@docRoot}/edu/uw/concert/gradle/gitflow/strategy/package-summary.html#package-description">
 *     strategy
 * </a>
 * to see all classes implementing this interface.
 *
 * @author Max Käufer
 */
interface Strategy {

    /**
     * All available strategies.
     * See the package
     * <a href="{@docRoot}/com/github/amkay/gradle/gitflow/strategy/package-summary.html#package-description">
     *     strategy
     * </a>
     * to see all classes implementing this interface.
     */
    static final STRATEGIES = [
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
     * Infers the current version. This method is called by {@link edu.uw.concert.gradle.gitflow.GitflowPlugin}.
     *
     * @param grgit
     * @param extension
     * @return
     */
    VersionWithType infer(final Grgit grgit, final GitflowPluginExtension extension)

    /**
     * Determines if the strategy can infer the version. This is used to match the current branch, for example.
     *
     * @param grgit
     * @return
     */
    boolean canInfer(final Grgit grgit)

}
