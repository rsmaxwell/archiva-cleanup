package com.rsmaxwell.archiva;

public class Item {

	private static final String nl = System.getProperty("line.separator");

	public String groupId;
	public String artifactId;
	public String packaging;
	public String[] repositoryIds;
	public Keep keep;

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getPackaging() {
		return packaging;
	}

	public String[] getRepositoryIds() {
		return repositoryIds;
	}

	public Keep getKeep() {
		return keep;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("    groupId: " + groupId + nl);
		sb.append("    artifactId:" + artifactId + nl);
		sb.append("    packaging:" + packaging + nl);

		String seperator = "";
		sb.append("    repositoryIds: [ ");
		for (String repositoryId : repositoryIds) {
			sb.append(seperator + repositoryId);
			seperator = ", ";
		}
		sb.append(" ]" + nl);

		sb.append("    keep:" + nl);
		sb.append(keep);

		return sb.toString();
	}
}
