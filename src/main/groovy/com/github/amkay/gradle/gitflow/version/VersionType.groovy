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

/**
 * The type of a {@link Version} according to Gitflow's semantics
 *
 * @author Max Kaeufer
 */
enum VersionType {

    /**
     * A production release. That is when you are on the production release branch.
     */
    RELEASE,

    /**
     * A pre-release. That is when you are on a branch that prepares the next production release.
     */
    PRE_RELEASE,

    /**
     * A develop release. That is when you are on the development branch.
     */
     DEVELOP,

    /**
     * A feature release. That is when you are on a feature branch.
     */
     FEATURE,

    /**
     * A hotfix release. That is when you are on a hotfix branch.
     */
     HOTFIX,

    /**
     * A support release. That is when you are on a support branch.
     */
     SUPPORT,

    /**
     * A detached-head release. That is when you are on a detached head.
     */
     DETACHED_HEAD

}