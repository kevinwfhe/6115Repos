/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
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
package a.classes.constructors;

/**
 * Tests add a protected constructor.
 */
public class AddSingleProtectedConstructor {
	
	public int publicMethod(String arg) {
		return -1;
	}
	
	protected AddSingleProtectedConstructor(int i) {
		
	}
}
