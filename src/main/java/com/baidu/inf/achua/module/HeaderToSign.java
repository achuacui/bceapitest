package com.baidu.inf.achua.module;

import java.io.UnsupportedEncodingException;

import com.baidu.inf.achua.utility.BCEAuthorization;

public class HeaderToSign implements Comparable<HeaderToSign> {
	private String name;
	private String value;

	public HeaderToSign(String name, String value) {
		this.name = name.toLowerCase();
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

	@Override
	public int compareTo(HeaderToSign o) {
		return this.name.compareTo(o.name);
	}

	@Override
	public String toString() {
		try {
			return this.getName() + ":"
					+ BCEAuthorization.UriEncode(this.getValue());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}