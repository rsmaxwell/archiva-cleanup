package com.rsmaxwell.archiva.cleanup;

import java.util.Comparator;

public class SortByComparableVersionReverse implements Comparator<ComparableVersion> {

	@Override
	public int compare(ComparableVersion a, ComparableVersion b) {
		return b.compareTo(a);
	}

}
