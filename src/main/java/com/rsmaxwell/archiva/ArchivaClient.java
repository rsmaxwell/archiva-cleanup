package com.rsmaxwell.archiva;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivaClient {

	private static final String SCHEME = "http";
	private static final String HOST = "localhost";
	private static final int PORT = 8081;
	private static final String PATH = "archiva/restServices/archivaServices/searchService/getArtifactVersions";
	private static final String GROUPID = "groupId";
	private static final String ARTIFACTID = "artifactId";
	private static final String PACKAGING = "packaging";

	private HttpClient client;

	public ArchivaClient(String user, String password) {

		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user, password);
		provider.setCredentials(AuthScope.ANY, credentials);

		client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}

	public ArtifactVersions GetArtifactVersions(String groupId, String artifactId, String packaging)
			throws IOException, InterruptedException, URISyntaxException {

		URI uri = new URIBuilder().setScheme(SCHEME).setHost(HOST).setPort(PORT).setPath(PATH)
				.setParameter(GROUPID, groupId).setParameter(ARTIFACTID, artifactId).setParameter(PACKAGING, packaging)
				.build();

		HttpGet httpget = new HttpGet(uri);

		CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpget);

		System.out.println(response.getProtocolVersion());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(response.getStatusLine().getReasonPhrase());
		System.out.println(response.getStatusLine().toString());

		ResponseHandler<ArtifactVersions> rh = new ResponseHandler<ArtifactVersions>() {

			public ArtifactVersions handleResponse(final HttpResponse response) throws IOException {
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				}
				if (entity == null) {
					throw new ClientProtocolException("Response contains no content");
				}
				Gson gson = new GsonBuilder().create();
				ContentType contentType = ContentType.getOrDefault(entity);
				Charset charset = contentType.getCharset();
				Reader reader = new InputStreamReader(entity.getContent(), charset);
				return gson.fromJson(reader, ArtifactVersions.class);
			}
		};
		return client.execute(httpget, rh);
	}
}
