/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.PermissionLevel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fernando
 */
public class PermissionLevelTest {

	@Test
	public void parentsNeverContainsItselfTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			assertFalse(level.getParents().contains(level));
		}
	}

	@Test
	public void fullParentsNeverContainsItselfTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			assertFalse(level.getFullParents().contains(level));
		}
	}

	@Test
	public void fullParentsAndMeContainsItselfTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			assertTrue(level.getFullParentsAndMe().contains(level));
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void noEditableParentsTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			try {
				level.getParents().clear();
				fail();
			} catch (UnsupportedOperationException ex) {
			}
		}
		throw new UnsupportedOperationException();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void noEditableFullParentsTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			try {
				level.getFullParents().clear();
				fail();
			} catch (UnsupportedOperationException ex) {
			}
		}
		throw new UnsupportedOperationException();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void noEditableFullParentsIncludingMeTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			try {
				level.getFullParentsAndMe().clear();
				fail();
			} catch (UnsupportedOperationException ex) {
			}
		}
		throw new UnsupportedOperationException();
	}

	@Test
	public void fullParentsIncludesAllParentsTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			assertTrue(level.getFullParents().containsAll(level.getParents()));
		}
	}

	@Test
	public void fullParentsAndMeIncludesAllFullParentsTest() {
		for (PermissionLevel level : PermissionLevel.values()) {
			assertTrue(level.getFullParentsAndMe().containsAll(level.getFullParents()));
		}
	}
}
