package com.rsmaxwell.archiva;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivaClient {

	private static final String REPOSITORYID = "repositoryId";

	private String scheme;
	private String host;
	private int port;
	private String base;
	private HttpClientContext context;
	private HttpClient client;

	public ArchivaClient(String aScheme, String aHost, int aPort, String aBase, String user, String password) {

		scheme = aScheme;
		host = aHost;
		port = aPort;
		base = aBase;

		HttpHost targetHost = new HttpHost(host, port, scheme);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(user, password));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);

		client = HttpClients.createDefault();
	}

	public String getPingServicePing() throws Exception {

		String path = base + "/pingService/ping";
		URI uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path).build();

		HttpGet httpget = new HttpGet(uri);
		// System.out.println("Executing request " + httpget.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};

		String responseBody = client.execute(httpget, responseHandler, context);
		// System.out.println("----------------------------------------");
		// System.out.println(responseBody);
		return responseBody;
	}

	public String getPingServicePingWithAuthz() throws Exception {

		String path = base + "/pingService/pingWithAuthz";
		URI uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path).build();

		HttpGet httpget = new HttpGet(uri);
		// System.out.println("Executing request " + httpget.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};

		String responseBody = client.execute(httpget, responseHandler, context);
		// System.out.println("----------------------------------------");
		// System.out.println(responseBody);
		return responseBody;
	}

	public BrowseServiceVersionsList getBrowseServiceVersionsList(String repositoryId, String groupId,
			String artifactId) throws Exception {

		String path = base + "/browseService/versionsList" + "/" + groupId + "/" + artifactId;
		URI uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path)
				.setParameter(REPOSITORYID, repositoryId).build();

		HttpGet httpget = new HttpGet(uri);
		// System.out.println("Executing request " + httpget.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};

		String responseBody = client.execute(httpget, responseHandler, context);
		// System.out.println("----------------------------------------");
		// System.out.println(responseBody);

		Gson gson = new GsonBuilder().create();
		return gson.fromJson(responseBody, BrowseServiceVersionsList.class);
	}

	public String deleteRepositoriesServiceProjectVersion(String repositoryId, String groupId, String artifactId,
			String version) throws Exception {

		String path = base + "/repositoriesService/projectVersion" + "/" + repositoryId + "/" + groupId + "/"
				+ artifactId + "/" + version;

		URI uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path).build();

		HttpDelete httpdelete = new HttpDelete(uri);
		// System.out.println("Executing request " + httpdelete.getRequestLine());

		// Create a custom response handler
		ResponseHandler<String> responseHandler = response -> {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		};

		String responseBody = client.execute(httpdelete, responseHandler, context);
		// System.out.println("----------------------------------------");
		// System.out.println(responseBody);
		return responseBody;
	}
}
