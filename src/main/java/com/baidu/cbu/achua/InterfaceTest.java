package com.baidu.cbu.achua;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baidu.cbu.achua.module.HeaderToSign;
import com.baidu.cbu.achua.module.UserInfo;
import com.baidu.cbu.achua.utility.BCEAuthorization;
import com.baidu.cbu.achua.utility.HttpUtil;
import com.baidu.cbu.achua.utility.TimeUtility;

public class InterfaceTest {

	public static void interfaceInvoke(String httpMethod, String host, String URI, String queryString, String body,
			String accessKey, String secretKey, int signExpiresInSeconds, Date signDate, PrintWriter out, boolean run) {
		String timeStamp = TimeUtility.getUTCTime(signDate);

		UserInfo userInfo = new UserInfo(accessKey, secretKey);
		String canonicalQueryString = BCEAuthorization.canonicalQueryString(queryString);
		HeaderToSign _host = new HeaderToSign("host", host);
		HeaderToSign x_bce_date = new HeaderToSign("x-bce-date", timeStamp);
		List<HeaderToSign> headersToSign = new ArrayList<HeaderToSign>();
		headersToSign.add(_host);
		headersToSign.add(x_bce_date);
		String authorization = BCEAuthorization.sign(httpMethod, host, URI, queryString, headersToSign,
				signExpiresInSeconds, timeStamp, userInfo.getAccessKey(), userInfo.getSecretKey(), out);
		List<HeaderToSign> otherHeaders = new ArrayList<HeaderToSign>();
		otherHeaders.add(new HeaderToSign("authorization", authorization));
		String URL = "http://" + host + URI + ("".equals(canonicalQueryString) ? "" : "?" + canonicalQueryString);
		out.println(URL);

		if (run) {
			if (body != null) {
				out.println(HttpUtil.invoke(httpMethod, headersToSign, otherHeaders, URL, body.getBytes()));
			} else {
				out.println(HttpUtil.invoke(httpMethod, headersToSign, otherHeaders, URL, null));
			}
		}
	}

	public static void interfaceInvoke2(String httpMethod, String host, String URI, String headers, String queryString,
			String body, String accessKey, String secretKey, int signExpiresInSeconds, Date signDate, PrintWriter out,
			boolean run) {
		String timeStamp = TimeUtility.getUTCTime(signDate);

		UserInfo userInfo = new UserInfo(accessKey, secretKey);
		String canonicalQueryString = BCEAuthorization.canonicalQueryString(queryString);
		HeaderToSign _host = new HeaderToSign("host", host);
		HeaderToSign x_bce_date = new HeaderToSign("x-bce-date", timeStamp);
		List<HeaderToSign> headersToSign = new ArrayList<HeaderToSign>();
		headersToSign.add(_host);
		headersToSign.add(x_bce_date);
		formatHeaders(headers,headersToSign);
		String authorization = BCEAuthorization.sign(httpMethod, host, URI, queryString, headersToSign,
				signExpiresInSeconds, timeStamp, userInfo.getAccessKey(), userInfo.getSecretKey(), out);
		List<HeaderToSign> otherHeaders = new ArrayList<HeaderToSign>();
		otherHeaders.add(new HeaderToSign("authorization", authorization));
		String URL = "http://" + host + URI + ("".equals(canonicalQueryString) ? "" : "?" + canonicalQueryString);
		out.println(URL);

		if (run) {
			if (body != null) {
				out.println(HttpUtil.invoke(httpMethod, headersToSign, otherHeaders, URL, body.getBytes()));
			} else {
				out.println(HttpUtil.invoke(httpMethod, headersToSign, otherHeaders, URL, null));
			}
		}
	}

	private static void formatHeaders(String headers, List<HeaderToSign> headersToSign) {

		if ("".equals(headers) || headers == null)
			return;
		String[] headerString = headers.split("&");
		for (String str : headerString) {
			String[] temp = str.split("=", 2);
			if ("authorization".equalsIgnoreCase(temp[0]))
				continue;
			HeaderToSign h = new HeaderToSign(temp[0], temp.length == 1 ? "" : temp[1]);
			headersToSign.add(h);
		}
		return;
	}
}
