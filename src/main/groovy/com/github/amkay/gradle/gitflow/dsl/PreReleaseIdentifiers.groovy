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
package com.github.amkay.gradle.gitflow.dsl

/**
 * The holder for pre-release identifiers according to semantic versioning.
 *
 * @author Max Kaeufer
 */
class PreReleaseIdentifiers {

    /**
     * The static text that is used to indicate that the current branch is the master branch according to Gitflow's
     * semantics
     */
    String master = ''

    /**
     * The static text that is used to indicate that the current branch is a release branch according to Gitflow's
     * semantics
     */
    String release = 'pre'

    /**
     * The static text that is used to indicate that the current branch is the develop branch according to Gitflow's
     * semantics
     */
    String develop = 'dev'

    /**
     * The static text that is used to indicate that the current branch is a feature branch according to Gitflow's
     * semantics
     */
    String feature = 'feature'

    /**
     * The static text that is used to indicate that the current branch is a hotfix branch according to Gitflow's
     * semantics
     */
    String hotfix = 'fix'

    /**
     * The static text that is used to indicate that the current branch is a support branch according to Gitflow's
     * semantics
     */
    String support = 'support'

    /**
     * Helper method to allow keyword-based configuration of the <code>master</code> property
     * @param master
     */
    void master(final String master) {
        setMaster(master)
    }

    /**
     * Helper method to allow keyword-based configuration of the <code>release</code> property
     * @param release
     */
    void release(final String release) {
        setRelease(release)
    }

    /**
     * Helper method to allow keyword-based configuration of the <code>develop</code> property
     * @param develop
     */
    void develop(final String develop) {
        setDevelop(develop)
    }

    /**
     * Helper method to allow keyword-based configuration of the <code>feature</code> property
     * @param feature
     */
    void feature(final String feature) {
        setFeature(feature)
    }

    /**
     * Helper method to allow keyword-based configuration of the <code>hotfix</code> property
     * @param hotfix
     */
    void hotfix(final String hotfix) {
        setHotfix(hotfix)
    }

    /**
     * Helper method to allow keyword-based configuration of the <code>support</code> property
     * @param support
     */
    void support(final String support) {
        setSupport(support)
    }

}
