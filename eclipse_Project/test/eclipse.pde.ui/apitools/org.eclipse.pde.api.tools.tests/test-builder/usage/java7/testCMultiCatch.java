/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation and others.
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
package x.y.z;

import c.MultipleThrowableClass;
import c.MultipleThrowableClass.ExceptionA;
import c.MultipleThrowableClass.ExceptionB;

public class testCMultiCatch {

	public void m1(){
		MultipleThrowableClass c = new MultipleThrowableClass();
		try {
			c.m1();
			ExceptionA a = new ExceptionA();
		} catch (ExceptionA | ExceptionB e) {
			ExceptionA a = new ExceptionA();
		}
	}
}
