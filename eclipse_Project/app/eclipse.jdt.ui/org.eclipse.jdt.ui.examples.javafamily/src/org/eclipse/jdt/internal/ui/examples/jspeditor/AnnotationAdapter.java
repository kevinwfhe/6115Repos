/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
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

package org.eclipse.jdt.internal.ui.examples.jspeditor;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.IReconcileResult;
import org.eclipse.jface.text.source.Annotation;


/**
 * Adapts a temporary or persitent annotation to a reconcile result.
 *
 * @since 3.0
 */
public abstract class AnnotationAdapter implements IReconcileResult {

	/**
	 * Creates and returns the annotation adapted by this adapter.
	 *
	 * @return an annotation (can be temporary or persistent)
	 */
	public abstract Annotation createAnnotation();

	/**
	 * The position of the annotation adapted by this adapter.
	 *
	 * @return the position
	 */
	public abstract Position getPosition();

}
