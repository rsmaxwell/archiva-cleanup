package com.rsmaxwell.archiva.cleanup;

public class BrowseServiceVersionsList {

	private static final String nl = System.getProperty("line.separator");

	public String[] versions;
	private ComparableVersion[] comparableVersions;

	public void init() {

		int size = versions.length;
		comparableVersions = new ComparableVersion[size];
		for (int i = 0; i < size; i++) {
			comparableVersions[i] = new ComparableVersion(versions[i]);
		}
	}

	public ComparableVersion[] getComparableVersions() {
		return comparableVersions;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		String separator = "";
		sb.append("comparableVersions: [ ");
		for (ComparableVersion version : comparableVersions) {
			sb.append(separator + version);
			separator = ", ";
		}
		sb.append(" ]" + nl);

		return sb.toString();
	}
}