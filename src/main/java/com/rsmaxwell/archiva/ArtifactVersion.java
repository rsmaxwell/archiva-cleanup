package com.rsmaxwell.archiva;

public class ArtifactVersion {

	private static final String nl = System.getProperty("line.separator");

	public String context;
	public String url;
	public String groupId;
	public String artifactId;
	public String repositoryId;
	public String version;
	public String prefix;
	public String goals;
	public String bundleVersion;
	public String bundleSymbolicName;
	public String bundleExportPackage;
	public String bundleExportService;
	public String bundleDescription;
	public String bundleName;
	public String bundleLicense;
	public String bundleDocUrl;
	public String bundleImportPackage;
	public String bundleRequireBundle;
	public String classifier;
	public String packaging;
	public String fileExtension;
	public String size;
	public String type;
	public String path;
	public String id;
	public String scope;

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("context: " + context + nl);
		sb.append("url:" + url + nl);
		sb.append("groupId:" + groupId + nl);
		sb.append("artifactId:" + artifactId + nl);
		sb.append("repositoryId:" + repositoryId + nl);
		sb.append("version:" + version + nl);
		sb.append("prefix:" + prefix + nl);
		sb.append("goals:" + goals + nl);
		sb.append("bundleVersion:" + bundleVersion + nl);
		sb.append("bundleSymbolicName:" + bundleSymbolicName + nl);
		sb.append("bundleExportPackage:" + bundleExportPackage + nl);
		sb.append("bundleExportService:" + bundleExportService + nl);
		sb.append("bundleDescription:" + bundleDescription + nl);
		sb.append("bundleName:" + bundleName + nl);
		sb.append("bundleLicense:" + bundleLicense + nl);
		sb.append("bundleDocUrl:" + bundleDocUrl + nl);
		sb.append("bundleImportPackage:" + bundleImportPackage + nl);
		sb.append("bundleRequireBundle:" + bundleRequireBundle + nl);
		sb.append("classifier:" + classifier + nl);
		sb.append("packaging:" + packaging + nl);
		sb.append("fileExtension:" + fileExtension + nl);
		sb.append("size:" + size + nl);
		sb.append("type:" + type + nl);
		sb.append("path:" + path + nl);
		sb.append("id:" + id + nl);
		sb.append("scope:" + scope + nl);

		return sb.toString();
	}
}