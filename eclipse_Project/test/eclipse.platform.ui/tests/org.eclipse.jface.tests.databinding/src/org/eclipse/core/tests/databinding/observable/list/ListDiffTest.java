/*******************************************************************************
 * Copyright (c) 2007, 2009 Matthew Hall and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 208858)
 *     Matthew Hall - bug 272651
 ******************************************************************************/

package org.eclipse.core.tests.databinding.observable.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.list.ListDiff;
import org.eclipse.core.databinding.observable.list.ListDiffEntry;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for ListDiff class
 *
 * @since 1.1
 */
public class ListDiffTest {
	ListDiffVisitorStub visitor;

	@Before
	public void setUp() throws Exception {
		visitor = new ListDiffVisitorStub();
	}

	@Test
	public void testAccept_Add() {
		createListDiff(add(0, "element")).accept(visitor);
		assertEquals("add(0,element)", visitor.log);
	}

	@Test
	public void testAccept_Remove() {
		createListDiff(remove(0, "element")).accept(visitor);
		assertEquals("remove(0,element)", visitor.log);
	}

	@Test
	public void testAccept_MoveForward_RemoveBeforeAdd() {
		createListDiff(remove(0, "element"), add(1, "element")).accept(visitor);
		assertEquals("move(0,1,element)", visitor.log);
	}

	@Test
	public void testAccept_MoveForward_AddBeforeRemove() {
		// Add at index 2 then remove at index 0 leaves the element at index 1
		createListDiff(add(2, "element"), remove(0, "element")).accept(visitor);
		assertEquals("move(0,1,element)", visitor.log);
	}

	@Test
	public void testAccept_MoveBackward_RemoveBeforeAdd() {
		createListDiff(remove(4, "element"), add(1, "element")).accept(visitor);
		assertEquals("move(4,1,element)", visitor.log);
	}

	@Test
	public void testAccept_MoveBackward_AddBeforeRemove() {
		// Element is originally at position 4 in this test, but we must remove
		// it at 5 since the adding it at 1 first changes the index
		createListDiff(add(1, "element"), remove(5, "element")).accept(visitor);
		assertEquals("move(4,1,element)", visitor.log);
	}

	@Test
	public void testAccept_Replace_RemoveBeforeAdd() {
		createListDiff(remove(0, "element0"), add(0, "element1")).accept(
				visitor);
		assertEquals("replace(0,element0,element1)", visitor.log);
	}

	@Test
	public void testAccept_Replace_AddBeforeRemove() {
		createListDiff(add(0, "element1"), remove(1, "element0")).accept(
				visitor);
		assertEquals("replace(0,element0,element1)", visitor.log);
	}

	@Test
	public void testAccept_AllPatterns() {
		createListDiff(
		// Replace (remove before add)
				remove(0, "element0"), add(0, "element1"),
				// Replace (add before remove)
				add(0, "element3"), remove(1, "element2"),
				// Remove
				remove(1, "element4"),
				// Add
				add(2, "element5"),
				// Move forward (remove before add)
				remove(5, "element6"), add(6, "element6"),
				// Move forward (add before remove)
				add(7, "element6"), remove(5, "element6"),
				// Move backward (remove before add)
				remove(12, "element7"), add(11, "element7"),
				// Move backward (add before remove)
				add(11, "element7"), remove(13, "element7"),
				// Remove then add in place -- treat as replace
				remove(11, "element8"), add(11, "element8"),
				// Add then remove in place (special case) -- treat as separate
				// add and remove
				add(12, "element9"), remove(12, "element9")).accept(visitor);
		assertEquals(
				"replace(0,element0,element1), replace(0,element2,element3), "
						+ "remove(1,element4), " + "add(2,element5), "
						+ "move(5,6,element6), move(5,6,element6), "
						+ "move(12,11,element7), move(12,11,element7), "
						+ "replace(11,element8,element8), "
						+ "add(12,element9), remove(12,element9)", visitor.log);
	}

	@Test
	public void testAccept_MoveDetectionUsesEqualityNotSameness() {
		Object element0 = new String("element");
		Object element1 = new String("element");
		assertNotSame(element0, element1);
		assertEquals(element0, element1);

		createListDiff(remove(0, element0), add(1, element1)).accept(visitor);
		assertEquals("move(0,1,element)", visitor.log);
	}

	private <E> ListDiffEntry<E> add(int index, E element) {
		return Diffs.createListDiffEntry(index, true, element);
	}

	private <E> ListDiffEntry<E> remove(int index, E element) {
		return Diffs.createListDiffEntry(index, false, element);
	}

	@SafeVarargs
	private final <E> ListDiff<E> createListDiff(ListDiffEntry<E>... differences) {
		return Diffs.createListDiff(differences);
	}

	static class ListDiffVisitorStub extends ListDiffVisitor<Object> {
		String log = "";

		@Override
		public void handleAdd(int index, Object element) {
			log("add(" + index + "," + element + ")");
		}

		@Override
		public void handleRemove(int index, Object element) {
			log("remove(" + index + "," + element + ")");
		}

		@Override
		public void handleMove(int oldIndex, int newIndex, Object element) {
			log("move(" + oldIndex + "," + newIndex + "," + element + ")");
		}

		@Override
		public void handleReplace(int index, Object oldElement,
				Object newElement) {
			log("replace(" + index + "," + oldElement + "," + newElement + ")");
		}

		private void log(String message) {
			if (log.length() > 0)
				log += ", ";
			log += message;
		}
	}
}
