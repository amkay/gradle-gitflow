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
import com.github.amkay.gradle.gitflow.util.NearestVersionLocator
import com.github.amkay.gradle.gitflow.util.VersionBuilder
import com.github.zafarkhaja.semver.Version
import org.ajoberstar.grgit.Grgit

/**
 * TODO
 *
 * @author Max Kaeufer
 */
public class DetachedHeadStrategy extends Strategy {

    @Override
    protected Version doInfer(final Grgit grgit, final GitflowPluginExtension ext) {
        def nearestVersion = new NearestVersionLocator().locate(grgit)

        new VersionBuilder(nearestVersion)
          .branch(ext.preReleaseIds.detachedHead)
          .distanceFromRelease()
          .sha(grgit, ext)
          .dirty(grgit, ext)
          .build()
    }

    @Override
    public boolean canInfer(final Grgit grgit) {
        // grgit.branch.current should be used, but that does never return null (not even when on detached head)
        true
    }

}
