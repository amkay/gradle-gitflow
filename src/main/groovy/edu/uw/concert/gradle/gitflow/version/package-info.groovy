/**
 * Contains the object that is set as <code>version</code> property on the project to which this plugin is applied.
 *
 * The most important aspects of this <code>version</code> object are:
 *
 * <ul>
 *     <li>
 *         The version is <strong>lazily</strong> inferred. That means the plugin only interacts with Your
 *         <em>Git</em> repository if a method on this <code>version</code> object is called, e.g. <code>toString()
 *         </code>. This functionality is realized by {@link DelayedVersionWithType}, which is the actual type of the
 *         set <code>version</code> object.
 *     </li>
 *     <li>
 *         This {@link DelayedVersionWithType} delegates to {@link VersionWithType}, which provides the real
 *         functionality of the <code>version</code> object.
 *     </li>
 *     <li>
 *         The class {@link VersionWithType} actually delegates to {@link Version} and provides additional
 *         functionality to determine the {@link VersionType type} of the inferred version.
 *     </li>
 * <ul>
 */
package edu.uw.concert.gradle.gitflow.version

import com.github.zafarkhaja.semver.Version;