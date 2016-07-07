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
package edu.uw.concert.gradle.gitflow.version

import com.github.zafarkhaja.semver.Version

/**
 * The type of a {@link Version} according to <em>Gitflow's semantics</em>
 *
 * @author Max Käufer
 */
@SuppressWarnings('SerializableClassMustDefineSerialVersionUID')
enum VersionType {

    /**
     * A production release. That is when you are on the <strong>production release</strong> branch.
     */
    RELEASE,

    /**
     * A pre-release. That is when you are on a branch that <strong>prepares the next production release</strong>.
     */
    PRE_RELEASE,

    /**
     * A develop release. That is when you are on the <strong>development</strong> branch.
     */
     DEVELOP,

    /**
     * A feature release. That is when you are on a <strong>feature</strong> branch.
     */
     FEATURE,

    /**
     * A hotfix release. That is when you are on a <strong>hotfix</strong> branch.
     */
     HOTFIX,

    /**
     * A support release. That is when you are on a <strong>support</strong> branch.
     */
     SUPPORT,

    /**
     * A detached-head release. That is when you are on a <strong>detached head</strong>.
     */
     DETACHED_HEAD

}
