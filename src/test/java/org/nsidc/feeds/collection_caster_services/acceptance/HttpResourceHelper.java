package org.nsidc.feeds.collection_caster_services.acceptance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

public class HttpResourceHelper {

	private String serverRoot;
	private DefaultHttpClient httpClient;
	private HttpResponse response;
	private String body;
	private final Logger logger = Logger.getLogger(HttpResourceHelper.class);
	private Integer status;
	
	public HttpResourceHelper() {
		httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 60000);
		HttpConnectionParams.setSoTimeout(httpParams, 60000);
	}

	public void setServerRoot(String serverRoot) {
		this.serverRoot = serverRoot;
	}

	public void accessResource(String resource) throws URISyntaxException, IOException, ClientProtocolException {
		URI uri = new URI(serverRoot + resource);
		logger.info("Connecting to " + uri);
		resetRequestValues();
		HttpGet connection = new HttpGet(uri);
		
		this.response = httpClient.execute(connection);
		this.body = convertInputStreamToString(this.response.getEntity().getContent());
		this.status = response.getStatusLine().getStatusCode();
	}
	
	public void post(String resource, String content, String format) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URI(serverRoot + resource);
		logger.info("Connecting to " + uri);
		resetRequestValues();
		HttpPost httppost = new HttpPost(uri);
		StringEntity se = new StringEntity(content);

		se.setContentType(format);  
		httppost.setHeader("Content-Type",format);
		httppost.setHeader("Accept", "*/*");
		httppost.setEntity(se);  
		
		this.response = httpClient.execute(httppost);
		this.body = convertInputStreamToString(this.response.getEntity().getContent());
		this.status = response.getStatusLine().getStatusCode();
	}
	
	private void resetRequestValues() {
		this.response = null;
		this.body = null;
		this.status = null;
	}

	public Integer getStatus() {
		return status;
	}

	public String getBody() {
		return body;
	}
	
	private String convertInputStreamToString(InputStream ists)
			throws IOException, UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		String line;

		try {
			BufferedReader r1 = new BufferedReader(new InputStreamReader(ists,
					"UTF-8"));
			while ((line = r1.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} finally {
			ists.close();
		}
		String body = sb.toString();
		return body;
	}

	

}
