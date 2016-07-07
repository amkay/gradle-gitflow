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
package edu.uw.concert.gradle.gitflow

import edu.uw.concert.gradle.gitflow.dsl.GitflowPluginExtension
import edu.uw.concert.gradle.gitflow.version.DelayedVersionWithType
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * The entry point of the plugin.
 *
 * <p>
 *     When the plugin is applied, it registers a {@link GitflowPluginExtension} and sets the version of the project
 *     to which it is applied to a {@link DelayedVersionWithType}.
 * </p>
 *
 * @author Max Käufer
 */
class GitflowPlugin implements Plugin<Project> {

    /**
     * The name of the registered plugin extension
     */
    public static final String EXT_GITFLOW = 'gitflow'

    /**
     * See {@link Plugin#apply(Object)}.
     * @param project
     */
    @Override
    void apply(final Project project) {
        project.extensions.create(EXT_GITFLOW, GitflowPluginExtension, project.projectDir)

        project.version = new DelayedVersionWithType(project)
    }

}
