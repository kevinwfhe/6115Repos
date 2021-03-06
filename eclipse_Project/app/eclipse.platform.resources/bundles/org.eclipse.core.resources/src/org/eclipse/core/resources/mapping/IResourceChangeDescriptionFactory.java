/*******************************************************************************
 * Copyright (c) 2006, 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.resources.mapping;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This factory is used to build a resource delta that represents a proposed
 * change that can then be passed to the
 * {@link ResourceChangeValidator#validateChange(IResourceDelta, IProgressMonitor)}
 * method in order to validate the change with any model providers stored in
 * those resources. The deltas created by calls to the methods of this interface
 * will be the same as those generated by the workspace if the proposed
 * operations were performed, except an additional
 * {@link IResourceDelta#DELETE_CONTENT_PROPOSED} flag can indicate if the
 * change is going to delete resources not only from the workspace model but
 * also physically, so it cannot be undone.
 * <p>
 * This factory does not validate that the proposed operation is valid given the current
 * state of the resources and any other proposed changes. It only records the
 * delta that would result.
 *
 * @see ResourceChangeValidator
 * @see ModelProvider
 * @since 3.2
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IResourceChangeDescriptionFactory {

	/**
	 * Record a delta that represents a content change for the given file.
	 * @param file the file whose contents will be changed
	 */
	void change(IFile file);

	/**
	 *  Record the set of deltas representing the closed of a project.
	 * @param project the project that will be closed
	 */
	void close(IProject project);

	/**
	 * Record the set of deltas representing a copy of the given resource to the
	 * given workspace path.
	 * @param resource the resource that will be copied
	 * @param destination the full workspace path of the destination the resource is being copied to
	 */
	void copy(IResource resource, IPath destination);

	/**
	 * Record a delta that represents a resource being created.
	 * @param resource the resource that is created
	 */
	void create(IResource resource);

	/**
	 * Record the set of deltas representing a deletion of the given resource.
	 * @param resource the resource that will be deleted
	 */
	void delete(IResource resource);

	/**
	 * Record the set of deltas representing a deletion of the given project.
	 *
	 * @param project       the project that will be deleted
	 * @param deleteContent <code>true</code> if the project content on the disk
	 *                      should be deleted. The content delete is not undoable.
	 * @since 3.16
	 * @see IResourceDelta#DELETE_CONTENT_PROPOSED
	 */
	void delete(IProject project, boolean deleteContent);

	/**
	 * Return the proposed delta that has been accumulated by this factory.
	 * @return the proposed delta that has been accumulated by this factory
	 */
	IResourceDelta getDelta();

	/**
	 * Record the set of deltas representing a move of the given resource to the
	 * given workspace path. Note that this API is used to describe a resource
	 * being moved to another path in the workspace, rather than a move in the
	 * file system.
	 * @param resource the resource that will be moved
	 * @param destination the full workspace path of the destination the resource is being moved to
	 */
	void move(IResource resource, IPath destination);

}
