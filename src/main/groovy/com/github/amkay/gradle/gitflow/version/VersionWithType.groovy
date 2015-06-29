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
 * TODO
 *
 * @author Max Kaeufer
 */
public class VersionWithType {

    @Delegate
    private final Version version

    final VersionType type


    VersionWithType(final Version version, final VersionType type) {
        this.version = version
        this.type = type
    }


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

        return true
    }

    @Override
    int hashCode() {
        int result

        result = (version != null ? version.hashCode() : 0)
        result = 31 * result + (type != null ? type.hashCode() : 0)

        return result
    }

    @Override
    String toString() {
        version.toString()
    }

}
