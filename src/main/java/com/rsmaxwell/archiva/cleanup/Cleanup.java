package com.rsmaxwell.archiva.cleanup;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.rsmaxwell.archiva.client.ArchivaClient;
import com.rsmaxwell.archiva.client.LoginData;
import com.rsmaxwell.archiva.client.VersionData;

public class Cleanup {

	public void perform() throws Exception {

		List<String> log = new ArrayList<String>();
		int deleteCount = 0;
		Date date = new Date();
		String timestamp = new Timestamp(date.getTime()).toString();

		log.add(String.format(timestamp + "    ---[ ArchivaCleanup: %s ]---------------------------\n", Version.version()));

		Configuration config = Configuration.readConfiguration();

		ArchivaClient client = new ArchivaClient(config.getScheme(), config.getHost(), config.getPort(), config.getBase(), config.getUser(), config.getPassword());

		LoginData loginData = client.login();

		for (Item item : config.getItems()) {
			log.add(String.format(item.title()));
			int maximum_number = Integer.parseInt(item.keep.maximum_number);

			for (String repositoryId : item.getRepositoryIds()) {

				log.add(String.format("    %-20s", repositoryId));

				VersionData versionData = client.getBrowseServiceVersionsList(loginData, repositoryId, item.getGroupId(), item.getArtifactId());

				List<ComparableVersion> versions = new ArrayList<ComparableVersion>();
				for (String version : versionData.versions) {
					versions.add(new ComparableVersion(version));
				}

				Collections.sort(versions, new SortByComparableVersionReverse());

				int count = 0;
				for (ComparableVersion version : versions) {
					count++;

					if (count > maximum_number) {
						deleteCount++;
						log.add(String.format("        deleting   %s", version.toString()));
						client.deleteRepositoriesServiceProjectVersion(loginData, repositoryId, item.getGroupId(), item.getArtifactId(), version.toString());
					}
//					else {
//						log.add(String.format("        keeping    %s", version.toString()));
//					}
				}
			}
		}

		if (deleteCount > 0) {
			for (String line : log) {
				System.out.println(line);
			}
		}
	}
}
