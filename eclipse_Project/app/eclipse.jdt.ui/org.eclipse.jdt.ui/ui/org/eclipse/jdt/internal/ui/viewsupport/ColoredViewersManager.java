/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
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
package org.eclipse.jdt.internal.ui.viewsupport;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Display;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;


public class ColoredViewersManager implements IPropertyChangeListener {

	public static final String INHERITED_COLOR_NAME= "org.eclipse.jdt.ui.ColoredLabels.inherited"; //$NON-NLS-1$

	public static final String HIGHLIGHT_BG_COLOR_NAME= "org.eclipse.jdt.ui.ColoredLabels.match_highlight"; //$NON-NLS-1$
	public static final String HIGHLIGHT_WRITE_BG_COLOR_NAME= "org.eclipse.jdt.ui.ColoredLabels.writeaccess_highlight"; //$NON-NLS-1$

	private static ColoredViewersManager fgInstance= new ColoredViewersManager();

	private Set<ColoringLabelProvider> fManagedLabelProviders;

	public ColoredViewersManager() {
		fManagedLabelProviders= new HashSet<>();
	}

	public void installColoredLabels(ColoringLabelProvider labelProvider) {
		if (fManagedLabelProviders.contains(labelProvider))
			return;

		if (fManagedLabelProviders.isEmpty()) {
			// first lp installed
			PlatformUI.getPreferenceStore().addPropertyChangeListener(this);
			JFaceResources.getColorRegistry().addListener(this);
		}
		fManagedLabelProviders.add(labelProvider);
	}

	public void uninstallColoredLabels(ColoringLabelProvider labelProvider) {
		if (!fManagedLabelProviders.remove(labelProvider))
			return; // not installed

		if (fManagedLabelProviders.isEmpty()) {
			PlatformUI.getPreferenceStore().removePropertyChangeListener(this);
			JFaceResources.getColorRegistry().removeListener(this);
			// last viewer uninstalled
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property= event.getProperty();
		if (JFacePreferences.QUALIFIER_COLOR.equals(property)
				|| JFacePreferences.COUNTER_COLOR.equals(property)
				|| JFacePreferences.DECORATIONS_COLOR.equals(property)
				|| HIGHLIGHT_BG_COLOR_NAME.equals(property)
				|| HIGHLIGHT_WRITE_BG_COLOR_NAME.equals(property)
				|| INHERITED_COLOR_NAME.equals(property)
				|| IWorkbenchPreferenceConstants.USE_COLORED_LABELS.equals(property)
		) {
			Display.getDefault().asyncExec(this::updateAllViewers);
		}
	}

	protected final void updateAllViewers() {
		for (ColoringLabelProvider lp : fManagedLabelProviders) {
			lp.update();
		}
	}

	public static boolean showColoredLabels() {
		return PlatformUI.getPreferenceStore().getBoolean(IWorkbenchPreferenceConstants.USE_COLORED_LABELS);
	}

	public static void install(ColoringLabelProvider labelProvider) {
		fgInstance.installColoredLabels(labelProvider);
	}

	public static void uninstall(ColoringLabelProvider labelProvider) {
		fgInstance.uninstallColoredLabels(labelProvider);
	}

}
