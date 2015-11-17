/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper.test;

import me.ferrybig.javacoding.webmapper.VersionInfo;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author Fernando
 */
public class VersionTest {
	@Test
	public void gitshaTest() {
		String git = VersionInfo.getGitsha();
		assertNotEquals(git, "");
		assertNotEquals(git, "${buildNumber}");
	}
	
	@Test
	public void artifactTest() {
		String artifact = VersionInfo.getArtifactId();
		assertNotEquals(artifact, "");
		assertNotEquals(artifact, "${project.artifactId}");
	}
	
	@Test
	public void groupidTest() {
		String groupid = VersionInfo.getGroupId();
		assertNotEquals(groupid, "");
		assertNotEquals(groupid, "${project.groupId}");
	}
	
	@Test
	public void versionTest() {
		String version = VersionInfo.getVersion();
		assertNotEquals(version, "");
		assertNotEquals(version, "${project.version}");
	}
}
