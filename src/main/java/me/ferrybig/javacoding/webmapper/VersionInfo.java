/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Fernando
 */
public class VersionInfo {
	
	private final static VersionInfo INSTANCE = new VersionInfo();
	
	private final String groupId;
	private final String artifactId;
	private final String version;
	private final String gitsha;
	private final String fullVersion;
	
	private VersionInfo() {
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/version.properties"));
			this.groupId = prop.getProperty("maven-group-id", "");
			this.artifactId = prop.getProperty("maven-artifact-id", "");
			this.version = prop.getProperty("maven-version", "");
			this.gitsha = prop.getProperty("git-version", "");
			this.fullVersion = groupId + "." + artifactId + " V" + version + " (" + gitsha + ")";
		} catch (IOException ex) {
			throw new AssertionError("Unable to load version properties", ex);
		}
	}
	
	@Deprecated
	public static VersionInfo getInstance() {
		return INSTANCE;
	}
	
	private static VersionInfo getInstance0() {
		return INSTANCE;
	}

	public static String getGroupId() {
		return getInstance0().groupId;
	}

	public static String getArtifactId() {
		return getInstance0().artifactId;
	}
	
	public static String getVersion() {
		return getInstance0().version;
	}
	
	public static String getGitsha() {
		return getInstance0().gitsha;
	}
	
	public static String getFullVersion() {
		return getInstance0().fullVersion;
	}
	
}
