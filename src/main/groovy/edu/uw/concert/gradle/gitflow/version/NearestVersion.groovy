/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.uw.concert.gradle.gitflow.version

import com.github.zafarkhaja.semver.Version
import groovy.transform.Immutable

/**
 * Nearest version tags reachable from the <strong>current HEAD</strong>.
 * The version <code>0.0.0</code> will be returned for <code>any</code>.
 */
@Immutable(knownImmutableClasses = [ Version ])
class NearestVersion {

    /**
     * The nearest version that is tagged.
     */
    Version any

    /**
     * The number of commits since {@code any} reachable from <em>HEAD</em>.
     */
    int distanceFromAny

}

