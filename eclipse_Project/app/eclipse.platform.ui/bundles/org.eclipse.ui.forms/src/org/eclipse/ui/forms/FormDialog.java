/*******************************************************************************
 * Copyright (c) 2006, 2015 IBM Corporation and others.
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
package org.eclipse.ui.forms;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.internal.forms.Messages;

/**
 * A general-purpose dialog that hosts a form. Clients should extend the class
 * and override <code>createFormContent(IManagedForm)</code> protected method.
 * <p>
 * Since forms with wrapped text typically don't have a preferred size, it is
 * important to set the initial dialog size upon creation:
 * </p>
 *
 * <pre>
 * MyFormDialog dialog = new MyFormDialog(shell);
 * dialog.create();
 * dialog.getShell().setSize(500, 500);
 * dialog.open();
 * </pre>
 *
 * <p>
 * Otherwise, the dialog may open very wide.
 * </p>
 *
 * @since 3.3
 */

public class FormDialog extends TrayDialog {
	private FormToolkit toolkit;

	/**
	 * Creates a new form dialog for a provided parent shell.
	 *
	 * @param shell
	 *            the parent shell
	 */
	public FormDialog(Shell shell) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * Creates a new form dialog for a provided parent shell provider.
	 *
	 * @param parentShellProvider
	 *            the parent shell provider
	 */
	public FormDialog(IShellProvider parentShellProvider) {
		super(parentShellProvider);
	}

	@Override
	public boolean close() {
		boolean rcode = super.close();
		toolkit.dispose();
		return rcode;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		ScrolledForm sform = toolkit.createScrolledForm(parent);
		GridDataFactory.create(GridData.FILL_BOTH).applyTo(sform);
		ManagedForm mform = new ManagedForm(toolkit, sform);
		createFormContent(mform);
		applyDialogFont(sform.getBody());
		return sform;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Label sep = new Label(parent, SWT.HORIZONTAL|SWT.SEPARATOR);
		GridDataFactory.create(GridData.FILL_HORIZONTAL).applyTo(sep);
		return super.createButtonBar(parent);
	}

	/**
	 * Configures the dialog form and creates form content. Clients should
	 * override this method.
	 *
	 * @param mform
	 *            the dialog form
	 */
	protected void createFormContent(IManagedForm mform) {
		mform.getForm().setText(Messages.FormDialog_defaultTitle);
	}
}
