package com.baidu.cbu.achua.module;

import java.io.UnsupportedEncodingException;

import com.baidu.cbu.achua.utility.BCEAuthorization;

public class queryStringToSign implements Comparable<queryStringToSign> {

	private String name;
	private String value = "";

	public queryStringToSign(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int compareTo(queryStringToSign o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		try {
			// if (value == null || "".equals(value)) {
			// return this.getName();
			// } else {
			return BCEAuthorization.UriEncode(this.getName()) + "=" + BCEAuthorization.UriEncode(value);
			// }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
