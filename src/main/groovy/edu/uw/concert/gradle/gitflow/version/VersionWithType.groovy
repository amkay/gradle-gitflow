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
 * Combines a {@link Version} with a {@link VersionType} using the <em>Delegation Pattern</em>.
 * This is needed because inheritance cannot be used due to the package-private constructors of {@link Version}.
 * This class therefore delegates to {@link Version}.
 *
 * @author Max Käufer
 */
class VersionWithType implements Comparable<Version> {

    @Delegate
    private final Version version

    /**
     * The type of the version
     */
    final VersionType type

    final boolean dirty

    /**
     * @param version the version
     * @param type the type of the version
     */
    VersionWithType(final Version version, final VersionType type, final boolean dirty) {
        this.version = version
        this.type = type
        this.dirty = dirty
    }


    /**
     * See {@link Object#equals(Object)}.
     * @param o
     * @return
     */
    @Override
    boolean equals(final o) {
        if (this.is(o)) {
            return true
        }
        if (getClass() != o.class) {
            return false
        }

        final VersionWithType that = (VersionWithType) o

        if (type != that.type) {
            return false
        }
        if (version != that.version) {
            return false
        }

        true
    }

    /**
     * See {@link Object#hashCode()}.
     * @return
     */
    @Override
    int hashCode() {
        int result

        result = (version != null ? version.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)

        result
    }

    /**
     * See {@link Object#toString()}.
     * @return
     */
    @Override
    String toString() {
        version.toString()
    }

}
