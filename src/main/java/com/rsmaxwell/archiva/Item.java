package com.rsmaxwell.archiva;

public class Item {

	private static final String LS = System.getProperty("line.separator");

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
		sb.append("    groupId: " + groupId + LS);
		sb.append("    artifactId:" + artifactId + LS);
		sb.append("    packaging:" + packaging + LS);

		String seperator = "";
		sb.append("    repositoryIds: [ ");
		for (String repositoryId : repositoryIds) {
			sb.append(seperator + repositoryId);
			seperator = ", ";
		}
		sb.append(" ]" + LS);

		sb.append("    keep:" + LS);
		sb.append(keep);

		return sb.toString();
	}

	public String title() {

		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append("\"groupId\": \"" + groupId + "\", ");
		sb.append("\"artifactId\": \"" + artifactId + "\", ");
		sb.append("\"packaging\": \"" + packaging + "\", ");
		sb.append("}");

		return sb.toString();
	}
}
