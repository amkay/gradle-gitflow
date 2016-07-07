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
import org.ajoberstar.grgit.Commit
import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Locates the nearest {@link Tag}s whose names can be parsed as {@link Version}s.
 * Both the absolute nearest version tag and the nearest "normal version" tag are included.
 *
 * <p>
 *   Primarily used as part of version inference to determine the previous version.
 * </p>
 */
@SuppressWarnings('AbcMetric')
class NearestVersionLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger NearestVersionLocator

    /**
     * The name of the config section of <em>Gitflow</em> plugins in <code>.git/config</code>
     */
    static final String CONFIG_SECTION_GITFLOW = 'gitflow'

    /**
     * The name of the config sub-section for prefixes of <em>Gitflow</em> plugins in <code>.git/config</code>
     */
    static final String CONFIG_SUBSECTION_PREFIX = 'prefix'

    /**
     * The name of the config key for the prefix of version tags of <em>Gitflow</em> plugins in <code>.git/config</code>
     */
    static final String CONFIG_VERSION_TAG = 'versionTag'

    /**
     * The default value of the config value of <code>CONFIG_VERSION_TAG</code>.
     * This is used if the respective sections are missing in <code>.git/config</code>, e.g. if You are not using a
     * <em>Gitflow</em> plugin for <em>Git</em>.
     */
    static final String DEFAULT_PREFIX_VERSION = 'v'

    /**
     * Locate the nearest version in the given repository starting from the <strong>current HEAD</strong>.
     *
     * <p>
     * All <em>tag</em> names are parsed to determine if they are valid version strings.
     * Tag names can begin with <code>DEFAULT_PREFIX_VERSION</code> (which will be stripped off).
     * </p>
     *
     * <p>
     * The nearest tag is determined by getting a commit log between the tag and {@code HEAD}.
     * The version tag with the smallest log from a pure count of commits will have its version returned.
     * <strong>If two version tags have a log of the same size, the versions will be compared to find the one with the
     * highest precedence according to semver rules</strong>.
     * For example, {@code 1.0.0} has higher precedence than {@code 1.0.0-rc.2}.
     * For tags with logs of the same size and versions of the same precedence it is undefined which will be returned.
     * </p>
     *
     * <p>
     * Two versions will be returned: the <em>"any"</em> version and the <em>"normal"</em> version.
     * <em>"Any"</em> is the absolute nearest tagged version.
     * <em>"Normal"</em> is the nearest tagged version that <strong>does not</strong> include a <em>pre-release</em>
     * segment.
     * </p>
     *
     * @param grgit the repository to locate the tag in
     * @param fromRevStr the revision to consider current.
     * Defaults to {@code HEAD}.
     * @return the version corresponding to the nearest tag
     */
    @SuppressWarnings('AbcMetric')
    NearestVersion locate(Grgit grgit) {
        def versionPrefix = grgit.repository.jgit.repository.config
                                 .getString(CONFIG_SECTION_GITFLOW,
                                            CONFIG_SUBSECTION_PREFIX,
                                            CONFIG_VERSION_TAG) ?: DEFAULT_PREFIX_VERSION

        LOGGER.debug "Locate beginning on branch: ${grgit.branch.current.fullName}"

        Commit head = grgit.head()

        List versionTags = grgit.tag.list().inject([ ]) { list, tag ->
            Version version = Version.valueOf(tag.name[ 0 ] == versionPrefix ? tag.name[ 1..-1 ] : tag.name)
            LOGGER.debug "Tag ${tag.name} (${tag.commit.abbreviatedId}) parsed as ${version} version."

            if (version) {
                def data
                if (tag.commit == head) {
                    LOGGER.debug "Tag ${tag.fullName} is at head. Including as candidate."

                    data = [ version: version, distance: 0 ]
                } else {
                    if (grgit.isAncestorOf(tag, head)) {
                        LOGGER.debug "Tag ${tag.name} is an ancestor of HEAD. Including as a candidate."

                        def reachableCommitLog = grgit.log {
                            range tag.commit.id, head.id
                        }

                        LOGGER.debug "Reachable commits after tag ${tag.fullName}: {}",
                                     reachableCommitLog*.abbreviatedId

                        def distance = reachableCommitLog.size()
                        data = [ version: version, distance: distance ]
                    } else {
                        LOGGER.debug "Tag ${tag.name} is not an ancestor of HEAD. Excluding as a candidate."
                    }
                }
                if (data) {
                    LOGGER.debug "Tag data found: ${data}"

                    list << data
                }
            }
            list
        }

        Map any = versionTags.min { a, b ->
            a.distance <=> b.distance ?: (a.version <=> b.version) * -1
        }

        Version anyVersion = any ? any.version : Version.valueOf('0.0.0')
        int distanceFromAny = any ? any.distance : grgit.log(includes: [ head.id ]).size()

        new NearestVersion(anyVersion, distanceFromAny)
    }

}
