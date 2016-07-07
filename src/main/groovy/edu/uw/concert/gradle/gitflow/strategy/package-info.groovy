/**
 * Provides the strategies for inferring the version of the project to which the plugin is applied.
 *
 * <p>
 *     The entry point to this package is the interface {@link Strategy}.
 * </p>
 *
 * <p>
 *     There is exactly one class for each type of branch defined in the <em>Gitflow Branching Model</em> that
 *     provides a strategy to infer the version of the project.
 *     Furthermore, there is {@link DetachedHeadStrategy} that applies if the current commit is a <strong>detached
 *     head</strong>.
 * {@link AbstractStrategy} is the base class of these strategies and provides common logic like logging.
 * </p>
 */
package edu.uw.concert.gradle.gitflow.strategy;