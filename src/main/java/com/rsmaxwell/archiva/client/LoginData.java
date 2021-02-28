package com.rsmaxwell.archiva.client;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

public class LoginData {
	public String validationToken;
	public CookieStore cookieStore = new BasicCookieStore();
}
