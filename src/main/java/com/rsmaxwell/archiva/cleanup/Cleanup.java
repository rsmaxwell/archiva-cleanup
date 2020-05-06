package com.rsmaxwell.archiva.cleanup;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

public class Cleanup {

	public void perform() throws Exception {

		Date date = new Date();
		String timestamp = new Timestamp(date.getTime()).toString();
		System.out.println(timestamp + "    ---[ ArchivaCleanup ]---------------------------");

		Configuration config = Configuration.readConfiguration();

		// System.out.println(config);

		ArchivaClient client = new ArchivaClient(config.getScheme(), config.getHost(), config.getPort(), config.getBase(), config.getUser(),
				config.getPassword());

		// client.getPingServicePing();
		// client.getPingServicePingWithAuthz();

		for (Item item : config.getItems()) {

			System.out.println(item.title());

			for (String repositoryId : item.getRepositoryIds()) {

				System.out.printf("    %-20s", repositoryId);

				BrowseServiceVersionsList browseServiceVersionList = client.getBrowseServiceVersionsList(repositoryId, item.getGroupId(),
						item.getArtifactId());

				browseServiceVersionList.init();

				Arrays.sort(browseServiceVersionList.getComparableVersions(), new SortByComparableVersionReverse());

				System.out.printf("%5d\n", browseServiceVersionList.getComparableVersions().length);

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
						System.out.println("    Deleting: " + item.getGroupId() + ":" + comparableVersion + ":" + item.getArtifactId()
								+ "    version:" + comparableVersion.toString());
						client.deleteRepositoriesServiceProjectVersion(repositoryId, item.getGroupId(), item.getArtifactId(),
								comparableVersion.toString());
					}
				}
			}
		}
	}
}
