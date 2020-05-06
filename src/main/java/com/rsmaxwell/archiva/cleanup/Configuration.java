package com.rsmaxwell.archiva.cleanup;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Configuration {

	private static final String nl = System.getProperty("line.separator");

	public String scheme;
	public String host;
	public int port;
	public String base;
	public String user;
	public String password;
	public Item[] items;

	public static Configuration readConfiguration() throws FileNotFoundException {

		String filename = System.getProperty("user.home") + "/archiva-cleanup.json";
		JsonReader reader = new JsonReader(new FileReader(filename));

		Gson gson = new GsonBuilder().create();
		return gson.fromJson(reader, Configuration.class);
	}

	public String getScheme() {
		return scheme;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getBase() {
		return base;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public Item[] getItems() {
		return items;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("scheme: " + scheme + nl);
		sb.append("host:" + host + nl);
		sb.append("port:" + port + nl);
		sb.append("base:" + base + nl);
		sb.append("user:" + user + nl);
		sb.append("password:" + password + nl);
		sb.append("items:" + nl);

		for (Item item : items) {
			sb.append("-----------------------" + nl);
			sb.append(item);
		}
		sb.append("-----------------------" + nl);

		return sb.toString();
	}
}
