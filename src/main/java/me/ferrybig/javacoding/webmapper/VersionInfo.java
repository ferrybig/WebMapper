/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	private VersionInfo() {
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/version.properties"));
			this.groupId = prop.getProperty("maven-group-id", "");
			this.artifactId = prop.getProperty("maven-artifact-id", "");
			this.version = prop.getProperty("maven-version", "");
			this.gitsha = prop.getProperty("git-version", "");
		} catch (IOException ex) {
			throw new AssertionError("Unable to load version properties", ex);
		}
	}
	
	public static VersionInfo getInstance() {
		return INSTANCE;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	public String getGitsha() {
		return gitsha;
	}
	
}
