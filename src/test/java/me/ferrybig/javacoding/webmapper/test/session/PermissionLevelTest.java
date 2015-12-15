/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template level, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test.session;

import me.ferrybig.javacoding.webmapper.session.PermissionLevel;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assume;
import static org.junit.Assume.assumeTrue;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Fernando
 */
@RunWith(Parameterized.class)
public class PermissionLevelTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.stream(PermissionLevel.values()).map(o -> new Object[]{o}).collect(Collectors.toList());
	}
	
	@Parameter 
    public PermissionLevel level;
	
	@Test
	public void parentsNeverContainsItselfTest() {
		assertFalse(level.getParents().contains(level));
	}

	@Test
	public void fullParentsNeverContainsItselfTest() {
		assertFalse(level.getFullParents().contains(level));
	}

	@Test
	public void fullParentsAndMeContainsItselfTest() {
		assertTrue(level.getFullParentsAndMe().contains(level));
	}

	@Test
	public void fullChildrenAndMeContainsItselfTest() {
		assertTrue(level.getFullChildrenAndMe().contains(level));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void noEditableParentsTest() {
		level.getParents().clear();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void noEditableFullParentsTest() {
		level.getFullParents().clear();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void noEditableFullParentsIncludingMeTest() {
		level.getFullParentsAndMe().clear();
	}

	@Test
	public void fullParentsIncludesAllParentsTest() {
		assertTrue(level.getFullParents().containsAll(level.getParents()));
	}

	@Test
	public void fullParentsAndMeIncludesAllFullParentsTest() {
		assertTrue(level.getFullParentsAndMe().containsAll(level.getFullParents()));
	}

	@Test
	public void noCyclicParentChainsPresent() {
		checkCyclicParents(level, level, EnumSet.noneOf(PermissionLevel.class));
	}

	private void checkCyclicParents(PermissionLevel current, PermissionLevel toTest, 
			Set<PermissionLevel> visited) {
		current.getParents().stream().forEach((p) -> {
			assertNotEquals(toTest, p);
			assumeTrue(visited.add(p));
			checkCyclicParents(p, toTest, visited);
			visited.remove(p);
		});
	}

}
