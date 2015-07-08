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
package com.github.amkay.gradle.gitflow.dsl

/**
 * The holder for <em>build metadata identifiers</em> according to <em>semantic versioning</em>.
 *
 * @author Max Käufer
 */
class BuildMetadataIdentifiers {

    /**
     * The static text that is used before the actual <em>SHA</em> of the current commit
     */
    String sha = 'sha'

    /**
     * The static text that is appended to indicate that the repository is dirty
     */
    String dirty = 'dirty'

    /**
     * Helper method to allow <em>keyword-based configuration</em> of the <code>sha</code> property
     * @param sha
     */
    @SuppressWarnings('ConfusingMethodName')
    void sha(final String sha) {
        setSha sha
    }

    /**
     * Helper method to allow <em>keyword-based configuration</em> of the <code>dirty</code> property
     * @param dirty
     */
    @SuppressWarnings('ConfusingMethodName')
    void dirty(final String dirty) {
        setDirty dirty
    }

}
