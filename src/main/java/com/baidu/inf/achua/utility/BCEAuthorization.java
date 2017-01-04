package com.baidu.inf.achua.utility;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.baidu.inf.achua.module.HeaderToSign;
import com.baidu.inf.achua.module.queryStringToSign;

public class BCEAuthorization {
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);

	private static String joinN(String split, String[] strings) {
		String ret = strings[0];
		for (int i = 1; i < strings.length; i++)
			ret = ret + split + strings[i];
		return ret;
	}

	private static <E> String joinN(String split, List<E> list) {
		String ret = list.get(0).toString();
		for (int i = 1; i < list.size(); i++)
			ret = ret + split + list.get(i);
		return ret;
	}

	private static String sha256Hex(String signingKey, String stringToSign) {
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
			return Hex.encodeHex(mac.doFinal(stringToSign.getBytes(UTF8)));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String UriEncode(String data) throws UnsupportedEncodingException {
		return URLEncoder.encode(data, "UTF-8");
	}

	public static String canonicalQueryString(String queryString) {
		if ("".equals(queryString))
			return "";
		String[] queryStrings = queryString.split("&");
		List<queryStringToSign> queryStringToSigns = new ArrayList<queryStringToSign>();
		for (String str : queryStrings) {
			String[] temp = str.split("=", 2);
			if ("authorization".equalsIgnoreCase(temp[0]))
				continue;
			queryStringToSign q = new queryStringToSign(temp[0], temp.length == 1 ? "" : temp[1]);
			queryStringToSigns.add(q);
		}
		Collections.sort(queryStringToSigns);

		String result = "";
		for (queryStringToSign q : queryStringToSigns)
			result = result + q.toString() + "&";
		return joinN("&", queryStringToSigns);
	}

	public static String sign(String httpMethod, String host, String URI, String queryString,
			List<HeaderToSign> headersToSign, int expiresInSeconds, String timeStamp, String accessKey,
			String secretKey, PrintWriter out) {
		String CanonicalURI;
		try {
			CanonicalURI = UriEncode(URI).replace("%2F", "/");
			Collections.sort(headersToSign);
			String canonicalHeaders = joinN("\n", headersToSign);
			List<String> signheaderList = new ArrayList<String>();
			for (HeaderToSign h : headersToSign)
				signheaderList.add(h.getName());
			String signHeader = joinN(";", signheaderList);
			// String signHeader = joinN(",", signheaderList);
			String canonicalQueryString = canonicalQueryString(queryString);
			String CanonicalRequest = joinN("\n",
					new String[] { httpMethod, CanonicalURI, canonicalQueryString, canonicalHeaders });
			out.println("CanonicalRequest: " + CanonicalRequest);
			out.println("===============================================");
			String signKeyInfo = "bce-auth-v1" + "/" + accessKey + "/" + timeStamp + "/" + expiresInSeconds;
			out.println("SignKeyInfo: " + signKeyInfo);
			String signingKey = sha256Hex(secretKey, signKeyInfo);
			out.println("SigningKey: " + signingKey);
			String signature = sha256Hex(signingKey, CanonicalRequest);
			out.println("signature: " + signature);
			String authorization = joinN("/", new String[] { "bce-auth-v1", accessKey, timeStamp,
					String.valueOf(expiresInSeconds), signHeader, signature });
			out.println("===============================================");
			out.println("request: " + "http://" + host + UriEncode(URI).replaceAll("%2F", "/")
					+ ("".equals(canonicalQueryString) ? "" : "?" + canonicalQueryString));
			for (HeaderToSign hs : headersToSign)
				System.out.println(hs.getName() + ": " + hs.getValue());
			out.println();
			out.println("authorization: " + authorization);
			out.println("===============================================");
			return authorization;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
