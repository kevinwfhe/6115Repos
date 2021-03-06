/*******************************************************************************
 * Copyright (c) 2007, 2018 compeople AG and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  	compeople AG (Stefan Liebig) - initial API and implementation
 * 	IBM Corporation - ongoing development
 *******************************************************************************/
package org.eclipse.equinox.internal.p2.artifact.optimizers.pack200;

import java.io.*;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.artifact.optimizers.Activator;
import org.eclipse.equinox.internal.p2.artifact.processing.AbstractBufferingStep;
import org.eclipse.equinox.internal.p2.core.helpers.FileUtils;
import org.eclipse.internal.provisional.equinox.p2.jarprocessor.JarProcessor;
import org.eclipse.internal.provisional.equinox.p2.jarprocessor.JarProcessorExecutor;
import org.eclipse.internal.provisional.equinox.p2.jarprocessor.JarProcessorExecutor.Options;

/**
 * The Pack200Packer expects an input containing normal ".jar" data.
 * 
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noreference This class is not intended to be referenced by clients.
 * @deprecated See <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=572043">bug</a> for details.
 */
@Deprecated(forRemoval = true, since = "1.2.0")
public class Pack200OptimizerStep extends AbstractBufferingStep {
	private static final String PACKED_SUFFIX = ".pack.gz"; //$NON-NLS-1$
	private File incoming;
	private static boolean pack200Available;

	static {
		try {
			// TODO This is not very elegant. But there is currently no other way to
			// find it out. PackStep.canPack() is not visible due to restrictions.
			JarProcessor.getPackProcessor(null);
			pack200Available = true;
		} catch (UnsupportedOperationException e) {
			pack200Available = false;
		}
	}

	@Override
	protected OutputStream createIncomingStream() throws IOException {
		incoming = File.createTempFile(INCOMING_ROOT, JAR_SUFFIX);
		return new BufferedOutputStream(new FileOutputStream(incoming));
	}

	@Override
	protected void cleanupTempFiles() {
		super.cleanupTempFiles();
		if (incoming != null)
			incoming.delete();
	}

	@Override
	protected void performProcessing() throws IOException {
		File resultFile = null;
		try {
			resultFile = process();
			// now write the optimized content to the destination
			if (resultFile.length() > 0) {
				InputStream resultStream = new BufferedInputStream(new FileInputStream(resultFile));
				FileUtils.copyStream(resultStream, true, getDestination(), false);
			} else {
				setStatus(new Status(IStatus.ERROR, Activator.ID, "Empty intermediate file: " + resultFile)); //$NON-NLS-1$
			}
		} finally {
			if (resultFile != null)
				resultFile.delete();
		}
	}

	protected File process() throws IOException {
		if (!pack200Available)
			// TODO Slightly abused API!!
			throw new FileNotFoundException("Could not find pack200 command.");
		// unpack
		Options options = new Options();
		options.pack = true;
		// TODO use false here assuming that all content is conditioned. Need to revise
		// this
		options.processAll = false;
		options.input = incoming;
		options.outputDir = getWorkDir().getPath();
		options.verbose = true;
		new JarProcessorExecutor().runJarProcessor(options);
		return new File(getWorkDir(), incoming.getName() + PACKED_SUFFIX);
	}
}