/*******************************************************************************
 * Copyright (c) 2010 Oakland Software Incorporated and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Francis Upton IV, Oakland Software - initial API and implementation
 ******************************************************************************/
package org.eclipse.ui.tests.navigator.extension;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Provides some children for a given resource.
 */
public class TestSimpleChildrenContentProvider implements ITreeContentProvider {

	public static final int NUM_ITEMS = 4;

	public String _name;

	private Object[] _children;

	public static class SimpleChild {
		public String _name;
		public Object _parent;

		@Override
		public String toString() {
			return _name;
		}
	}

	public TestSimpleChildrenContentProvider() {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IResource) {
			if (_children == null) {
				List l = new ArrayList();
				for (int i = 0; i < NUM_ITEMS; i++) {
					SimpleChild child = new SimpleChild();
					child._parent = parentElement;
					child._name = _name + i;
					l.add(child);
				}
				_children = l.toArray();
			}
			return _children;
		}
		return new Object[] {};
	}

	@Override
	public Object getParent(Object element) {
		SimpleChild child = (SimpleChild) element;
		return child._parent;
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public void inputChanged(Viewer aViewer, Object oldInput, Object newInput) {
		_children = null;
	}

	@Override
	public void dispose() {
	}
}
