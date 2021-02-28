package com.rsmaxwell.archiva.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rsmaxwell.archiva.cleanup.AppException;

public class ArchivaClient {

	private static final String REPOSITORYID = "repositoryId";

	private static final String restServices = "restServices";
	private static final String redbackServices = "redbackServices";
	private static final String loginService = "loginService";
	private static final String loginEndpoint = restServices + "/" + redbackServices + "/" + loginService + "/logIn";

	private static final String archivaServices = "archivaServices";

	private static final String managedRepositoriesService = "managedRepositoriesService";
	private static final String addManagedRepositoryEndpoint = restServices + "/" + archivaServices + managedRepositoriesService + "/addManagedRepository";
	private static final String getManagedRepositoriesEndpoint = restServices + "/" + archivaServices + managedRepositoriesService + "/getManagedRepositories";
	private static final String getManagedRepositoryEndpoint = restServices + "/" + archivaServices + managedRepositoriesService + "/getManagedRepository";
	private static final String deleteManagedRepositoryEndpoint = restServices + "/" + archivaServices + managedRepositoriesService + "/deleteManagedRepository";

	private static final String browseService = "browseService";
	private static final String versionsListEndpoint = restServices + "/" + archivaServices + "/" + browseService + "/versionsList";

	private static final String archivaAdministrationService = "archivaAdministrationService";
	private static final String enabledKnownContentConsumersEndpoint = restServices + "/" + archivaServices + archivaAdministrationService + "/enabledKnownContentConsumers";
	private static final String disabledKnownContentConsumerEndpoint = restServices + "/" + archivaServices + archivaAdministrationService + "/disabledKnownContentConsumer";

	private static final String repositoriesService = "repositoriesService";
	private static final String projectVersionEndpoint = restServices + "/" + archivaServices + "/" + repositoriesService + "/projectVersion";

	private String scheme;
	private String host;
	private int port;
	private String base;
	private String username;
	private String password;
	private HttpClientContext context;

	public ArchivaClient(String scheme, String host, int port, String base, String username, String password) {

		this.scheme = scheme;
		this.host = host;
		this.port = port;
		this.base = base;
		this.username = username;
		this.password = password;
	}

	public LoginData login() throws Exception {

		// Body
		Map<String, String> myMap = new HashMap<String, String>();
		myMap.put("username", username);
		myMap.put("password", password);

		Gson gson = new GsonBuilder().create();
		String requestBody = gson.toJson(myMap);
		HttpEntity requestBodyEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);

		// URI
		String path = base + loginEndpoint;
		URI uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path).build();
		URI origin = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).build();

		// Request
		// @formatter:off
        HttpUriRequest request = RequestBuilder.post()
                .setUri(uri)
                .setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString())
                .setHeader("Origin", origin.toString())
                .setEntity(requestBodyEntity)
                .build();
        // @formatter:on

		LoginData loginData = new LoginData();
		UserData userData = null;
		// HttpClientContext context = HttpClientContext.create();
		// context.setAttribute(HttpClientContext.COOKIE_STORE, loginData.cookieStore);

		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(loginData.cookieStore).build()) {

			try (CloseableHttpResponse response = client.execute(request)) {

				String responseString = "";
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseString = EntityUtils.toString(entity);
				}

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					throw new AppException(response.getStatusLine().toString() + ", " + uri.toString() + ", " + responseString);
				}

				userData = gson.fromJson(responseString, UserData.class);
			}
		}

		loginData.validationToken = userData.validationToken;
		return loginData;
	}

	public VersionData getBrowseServiceVersionsList(LoginData loginData, String repositoryId, String groupId, String artifactId) throws Exception {

		// URI
		String path = base + versionsListEndpoint + "/" + groupId + "/" + artifactId;
		URI uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path).addParameter("repositoryId", repositoryId).build();
		URI origin = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).build();

		// Request
		// @formatter:off
        HttpUriRequest request = RequestBuilder.get()
                .setUri(uri)
                .setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString())
                .setHeader("Origin", origin.toString())
                .setHeader("X-XSRF-TOKEN", loginData.validationToken)
                .build();
        // @formatter:on

		VersionData versionData = null;
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(loginData.cookieStore).build()) {

			try (CloseableHttpResponse response = client.execute(request)) {

				String responseString = "";
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseString = EntityUtils.toString(entity);
				}

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					throw new AppException(response.getStatusLine().toString() + ", " + uri.toString() + ", " + responseString);
				}

				Gson gson = new GsonBuilder().create();
				versionData = gson.fromJson(responseString, VersionData.class);
			}
		}

		return versionData;
	}

	public String deleteRepositoriesServiceProjectVersion(LoginData loginData, String repositoryId, String groupId, String artifactId, String version) throws Exception {

		// URI
		String path = base + projectVersionEndpoint + "/" + repositoryId + "/" + groupId + "/" + artifactId + "/" + version;
		URI uri = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).setPath(path).build();
		URI origin = new URIBuilder().setScheme(scheme).setHost(host).setPort(port).build();

		// Request
		// @formatter:off
        HttpUriRequest request = RequestBuilder.delete()
                .setUri(uri)
                .setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString())
                .setHeader("Origin", origin.toString())
                .setHeader("X-XSRF-TOKEN", loginData.validationToken)
                .build();
        // @formatter:on

		String responseString = null;
		try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(loginData.cookieStore).build()) {

			try (CloseableHttpResponse response = client.execute(request)) {

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseString = EntityUtils.toString(entity);
				}

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					throw new AppException(response.getStatusLine().toString() + ", " + uri.toString() + ", " + responseString);
				}
			}
		}

		return responseString;
	}
}
