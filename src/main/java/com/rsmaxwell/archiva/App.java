package com.rsmaxwell.archiva;

import java.util.Arrays;

public class App {

	private static final String nl = System.getProperty("line.separator");

	public static void main(String[] args) throws Exception {
		System.out.println("ArchivaCleanup");

		Configuration config = Configuration.readConfiguration();

		// System.out.println(config);

		ArchivaClient client = new ArchivaClient(config.getScheme(), config.getHost(), config.getPort(),
				config.getBase(), config.getUser(), config.getPassword());

		// client.getPingServicePing();
		// client.getPingServicePingWithAuthz();

		for (Item item : config.getItems()) {

			// System.out.println("---[ item ]------------------");

			for (String repositoryId : item.getRepositoryIds()) {

				System.out.println("repositoryId: " + repositoryId);

				BrowseServiceVersionsList browseServiceVersionList = client.getBrowseServiceVersionsList(repositoryId,
						item.getGroupId(), item.getArtifactId());

				browseServiceVersionList.init();

				Arrays.sort(browseServiceVersionList.getComparableVersions(), new SortByComparableVersionReverse());

				// System.out.print(browseServiceVersionList.toString());

				int count = 0;
				for (ComparableVersion comparableVersion : browseServiceVersionList.getComparableVersions()) {
					count++;

					boolean keep = true;
					try {
						int maximum_number = Integer.parseInt(item.keep.maximum_number);
						if (count > maximum_number) {
							keep = false;
						}
					} catch (NumberFormatException e) {
					}

					if (!keep) {
						System.out.println("    Deleting: " + item.getGroupId() + ":" + comparableVersion + ":"
								+ item.getArtifactId() + "    version:" + comparableVersion.toString());
						client.deleteRepositoriesServiceProjectVersion(repositoryId, item.getGroupId(),
								item.getArtifactId(), comparableVersion.toString());
					}
				}
			}
		}
	}
}
