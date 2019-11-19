package com.rsmaxwell.archiva;

import java.util.List;

public class ArtifactVersions {

	public List<ArtifactVersion> list;

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (ArtifactVersion artifactVersion : list) {
			sb.append("-----------------------");
			sb.append(artifactVersion);
		}

		sb.append("-----------------------");
		return sb.toString();
	}
}
