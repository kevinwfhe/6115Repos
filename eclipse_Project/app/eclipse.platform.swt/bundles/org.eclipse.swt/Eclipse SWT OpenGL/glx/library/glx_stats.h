/*******************************************************************************
 * Copyright (c) 2000, 2020 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *******************************************************************************/

/* Note: This file was auto-generated by org.eclipse.swt.tools.internal.JNIGenerator */
/* DO NOT EDIT - your changes will be lost. */

#ifdef NATIVE_STATS
extern int GLX_nativeFunctionCount;
extern int GLX_nativeFunctionCallCount[];
extern char* GLX_nativeFunctionNames[];
#define GLX_NATIVE_ENTER(env, that, func) GLX_nativeFunctionCallCount[func]++;
#define GLX_NATIVE_EXIT(env, that, func) 
#else
#ifndef GLX_NATIVE_ENTER
#define GLX_NATIVE_ENTER(env, that, func) 
#endif
#ifndef GLX_NATIVE_EXIT
#define GLX_NATIVE_EXIT(env, that, func) 
#endif
#endif

typedef enum {
	XVisualInfo_1sizeof_FUNC,
	glGetIntegerv_FUNC,
	glViewport_FUNC,
	glXChooseVisual_FUNC,
	glXCreateContext_FUNC,
	glXDestroyContext_FUNC,
	glXGetConfig_FUNC,
	glXGetCurrentContext_FUNC,
	glXMakeCurrent_FUNC,
	glXSwapBuffers_FUNC,
	memmove_FUNC,
} GLX_FUNCS;
