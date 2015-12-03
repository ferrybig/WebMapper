/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.ferrybig.javacoding.webmapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.util.Objects.requireNonNull;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public final class VersionInfo {

	private final static VersionInfo INSTANCE = new VersionInfo();

	private final String groupId;
	private final String artifactId;
	private final String version;
	private final String tags;
	private final boolean dirty;
	private final String commitId;
	private final String commitIdAbbrev;
	private final Date commitTime;
	
	private final String fullVersion;

	private VersionInfo() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/version.properties"));
			this.groupId = requireNonNull(prop.getProperty("maven-group-id"));
			this.artifactId = requireNonNull(prop.getProperty("maven-artifact-id"));
			this.version = requireNonNull(prop.getProperty("maven-version"));
			prop.load(this.getClass().getResourceAsStream("/git.properties"));
			this.tags = requireNonNull(prop.getProperty("git.tags"));
			this.dirty = Boolean.parseBoolean(prop.getProperty("git.dirty"));
			this.commitId = requireNonNull(prop.getProperty("git.commit.id.full"));
			this.commitIdAbbrev = requireNonNull(prop.getProperty("git.commit.id.abbrev"));
			this.commitTime = format.parse(prop.getProperty("git.commit.time"));

			this.fullVersion = groupId + "." + artifactId + " V" + version
					+ " (" + commitIdAbbrev + (dirty ? "-dirty" : "") + ")";
		} catch (IOException | ParseException ex) {
			throw new IllegalStateException("Unable to load version properties", ex);
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

	/**
	 * Gte git commit id
	 * @return the last git commit sha1 hash
	 * @deprecated use getCommitId() instead
	 */
	@Deprecated
	public static String getGitsha() {
		return getInstance0().commitId;
	}

	public static String getFullVersion() {
		return getInstance0().fullVersion;
	}

	public static String getTags() {
		return getInstance0().tags;
	}

	public static boolean isDirty() {
		return getInstance0().dirty;
	}

	public static String getCommitId() {
		return getInstance0().commitId;
	}

	public static String getCommitIdAbbrev() {
		return getInstance0().commitIdAbbrev;
	}

	public static Date getCommitTime() {
		return getInstance0().commitTime;
	}

}
