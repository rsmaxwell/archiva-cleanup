package com.rsmaxwell.archiva;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {

	private static final String USER = "richard";
	private static final String PASSWORD = "59N4257T5h4X2dz";

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		System.out.println("HelloWorld");

		ArchivaClient client = new ArchivaClient(USER, PASSWORD);

		String groupId = "com.rsmaxwell.players";
		String artifactId = "players-api-amd64-linux";
		String packaging = "zip";

		ArtifactVersions artifactVersions = client.GetArtifactVersions(groupId, artifactId, packaging);

		System.out.println(artifactVersions);
	}
}
