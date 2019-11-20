package com.rsmaxwell.archiva;

public class Keep {

	private static final String nl = System.getProperty("line.separator");

	public String maximum_number;

	public String getMaximumNumber() {
		return maximum_number;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("        maximum_number:" + maximum_number + nl);
		return sb.toString();
	}
}
