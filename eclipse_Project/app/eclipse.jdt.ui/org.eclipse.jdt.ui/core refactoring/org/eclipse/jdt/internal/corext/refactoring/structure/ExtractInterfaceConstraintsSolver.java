/*******************************************************************************
 * Copyright (c) 2005, 2011 IBM Corporation and others.
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
package org.eclipse.jdt.internal.corext.refactoring.structure;

import org.eclipse.core.runtime.Assert;

import org.eclipse.jdt.internal.corext.refactoring.structure.constraints.SuperTypeConstraintsModel;
import org.eclipse.jdt.internal.corext.refactoring.structure.constraints.SuperTypeConstraintsSolver;
import org.eclipse.jdt.internal.corext.refactoring.structure.constraints.SuperTypeSet;
import org.eclipse.jdt.internal.corext.refactoring.typeconstraints.types.TType;
import org.eclipse.jdt.internal.corext.refactoring.typeconstraints2.ConstraintVariable2;
import org.eclipse.jdt.internal.corext.refactoring.typeconstraints2.ITypeSet;
import org.eclipse.jdt.internal.corext.refactoring.typeconstraints2.ImmutableTypeVariable2;

/**
 * Type constraint solver to solve the extract interface problem.
 */
public final class ExtractInterfaceConstraintsSolver extends SuperTypeConstraintsSolver {

	/** The extracted type name, without any qualification or type parameters */
	private final String fName;

	/**
	 * Creates a new extract interface constraints solver.
	 *
	 * @param model the model to solve
	 * @param name the name of the extracted type
	 */
	public ExtractInterfaceConstraintsSolver(final SuperTypeConstraintsModel model, final String name) {
		super(model);
		Assert.isNotNull(name);
		fName= name;
	}

	/*
	 * @see org.eclipse.jdt.internal.corext.refactoring.structure.constraints.SuperTypeConstraintsSolver#computeTypeEstimate(org.eclipse.jdt.internal.corext.refactoring.typeconstraints2.ConstraintVariable2)
	 */
	@Override
	protected ITypeSet computeTypeEstimate(final ConstraintVariable2 variable) {
		final TType type= variable.getType();
		if (variable instanceof ImmutableTypeVariable2 || !type.getErasure().equals(fModel.getSubType().getErasure()))
			return SuperTypeSet.createTypeSet(type);
		for (TType t : type.getInterfaces()) {
			if (t.getName().startsWith(fName) && t.getErasure().equals(fModel.getSuperType().getErasure())) {
				return SuperTypeSet.createTypeSet(type, t);
			}
		}
		return SuperTypeSet.createTypeSet(type);
	}
}
