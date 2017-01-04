package com.baidu.cbu.achua.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.baidu.cbu.achua.module.HeaderToSign;

public class HttpUtil {

	public static String invoke(String HTTPMethod, List<HeaderToSign> headersToSign, List<HeaderToSign> otherHeaders,
			String URI, byte[] body) {

		if ("GET/DELETE/HEAD".contains(HTTPMethod)) {
			HttpRequestBase request;
			if ("GET".equalsIgnoreCase(HTTPMethod)) {
				request = new HttpGet(URI);
			} else if ("DELETE".equalsIgnoreCase(HTTPMethod)) {
				request = new HttpDelete(URI);
			} else if ("HEAD".equalsIgnoreCase(HTTPMethod)) {
				request = new HttpHead(URI);
			} else {
				return "Wrong HTTP Method";
			}
			for (HeaderToSign header : headersToSign) {
				if ("content-length".equals(header.getName()))
					continue;
				request.setHeader(header.getName(), header.getValue());
			}
			for (HeaderToSign header : otherHeaders) {
				if ("content-length".equalsIgnoreCase(header.getName()))
					continue;
				request.setHeader(header.getName(), header.getValue());
			}

			CloseableHttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(request);
				String result = "HTTP return code: " + response.getStatusLine().toString();
				System.err.println(result);

				StringBuilder responseHeader = new StringBuilder();
				Header headers[] = response.getAllHeaders();
				int i = 0;
				while (i < headers.length) {
					responseHeader.append("  " + headers[i].getName() + ":" + headers[i].getValue() + "\n");
					i++;
				}

				StringBuilder responseContent = new StringBuilder();
				if (!"DELETE".equalsIgnoreCase(HTTPMethod) && !"HEAD".equalsIgnoreCase(HTTPMethod)) {
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = "";
					while ((line = rd.readLine()) != null) {
						responseContent.append(line);
					}
					System.err.println(responseContent);
				}
				HttpEntity entity = response.getEntity();
				EntityUtils.consume(entity);
				return result + "\nresponseHeader:\n" + responseHeader.toString() + "responseContent:\n" + "  "
						+ responseContent.toString();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			HttpEntityEnclosingRequestBase request;
			if ("POST".equalsIgnoreCase(HTTPMethod)) {
				request = new HttpPost(URI);
			} else if ("PUT".equalsIgnoreCase(HTTPMethod)) {
				request = new HttpPut(URI);
			} else {
				return "Wrong HTTP Method";
			}
			for (HeaderToSign header : headersToSign) {
				if ("content-length".equalsIgnoreCase(header.getName()))
					continue;
				request.setHeader(header.getName(), header.getValue());
			}
			for (HeaderToSign header : otherHeaders) {
				if ("content-length".equalsIgnoreCase(header.getName()))
					continue;
				request.setHeader(header.getName(), header.getValue());
			}
			if (body != null)
				request.setEntity(new ByteArrayEntity(body));
			CloseableHttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(request);
				String result = "HTTP return code: " + response.getStatusLine().toString();
				System.err.println(result);

				StringBuilder responseHeader = new StringBuilder();
				Header headers[] = response.getAllHeaders();
				int i = 0;
				while (i < headers.length) {
					responseHeader.append("  " + headers[i].getName() + ":" + headers[i].getValue() + "\n");
					i++;
				}

				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuilder responseContent = new StringBuilder();
				String line = "";
				while ((line = rd.readLine()) != null) {
					responseContent.append(line);
				}
				System.err.println(responseContent);
				HttpEntity entity = response.getEntity();
				EntityUtils.consume(entity);
				return result + "\nresponseHeader:\n" + responseHeader.toString() + "responseContent:\n" + "  "
						+ responseContent.toString();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "-1";
	}

}
