/*******************************************************************************
 * Copyright (c) 2000, 2019 IBM Corporation and others.
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
extern int C_nativeFunctionCount;
extern int C_nativeFunctionCallCount[];
extern char* C_nativeFunctionNames[];
#define C_NATIVE_ENTER(env, that, func) C_nativeFunctionCallCount[func]++;
#define C_NATIVE_EXIT(env, that, func) 
#else
#ifndef C_NATIVE_ENTER
#define C_NATIVE_ENTER(env, that, func) 
#endif
#ifndef C_NATIVE_EXIT
#define C_NATIVE_EXIT(env, that, func) 
#endif
#endif

typedef enum {
	PTR_1sizeof_FUNC,
	free_FUNC,
	getenv_FUNC,
	malloc_FUNC,
	memmove__JJJ_FUNC,
	memmove__J_3BJ_FUNC,
	memmove__J_3CJ_FUNC,
	memmove__J_3DJ_FUNC,
	memmove__J_3FJ_FUNC,
	memmove__J_3IJ_FUNC,
	memmove__J_3JJ_FUNC,
	memmove__J_3SJ_FUNC,
	memmove___3BJJ_FUNC,
	memmove___3B_3CJ_FUNC,
	memmove___3CJJ_FUNC,
	memmove___3DJJ_FUNC,
	memmove___3FJJ_FUNC,
	memmove___3IJJ_FUNC,
	memmove___3I_3BJ_FUNC,
	memmove___3JJJ_FUNC,
	memmove___3SJJ_FUNC,
	memset_FUNC,
	setenv_FUNC,
	strlen_FUNC,
} C_FUNCS;