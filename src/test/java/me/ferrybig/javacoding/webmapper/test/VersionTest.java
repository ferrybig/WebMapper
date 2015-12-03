/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test;

import me.ferrybig.javacoding.webmapper.VersionInfo;
import java.util.Date;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author Fernando
 */
public class VersionTest {
	@Test
	public void getCommitIdTest() {
		String git = VersionInfo.getCommitId();
		assertNotNull(git);
		assertNotEquals(git, "");
	}
	
	@Test
	public void getArtifactIdTest() {
		String artifact = VersionInfo.getArtifactId();
		assertNotNull(artifact);
		assertNotEquals(artifact, "");
	}
	
	@Test
	public void getGroupIdTest() {
		String groupid = VersionInfo.getGroupId();
		assertNotNull(groupid);
		assertNotEquals(groupid, "");
	}
	
	@Test
	public void getVersionTest() {
		String version = VersionInfo.getVersion();
		assertNotNull(version);
		assertNotEquals(version, "");
	}
	
	@Test
	public void getCommitTimeTest() {
		Date date = VersionInfo.getCommitTime();
		assertNotNull(date);
	}
	
	@Test
	public void getCommitIdAbbrTest() {
		String version = VersionInfo.getCommitIdAbbrev();
		assertNotNull(version);
		assertNotEquals(version, "");
		assertTrue(VersionInfo.getCommitId().startsWith(version));
	}
	
	@Test
	public void getTagsTest() {
		String version = VersionInfo.getTags();
		assertNotNull(version);
	}
	
	@Test
	public void getFullVersionTest() {
		String version = VersionInfo.getFullVersion();
		assertNotNull(version);
		assertNotEquals(version, "");
		assertTrue(version.contains(VersionInfo.getArtifactId()));
		assertTrue(version.contains(VersionInfo.getVersion()));
		assertTrue(version.contains(VersionInfo.getGroupId()));
	}
	
}
