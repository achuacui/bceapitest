package com.baidu.inf.achua.utility;

public class ClientToken {
	public static String generate() {
		return RandomString.getHex(8) + "-" + RandomString.getHex(4) + "-" + RandomString.getHex(4) + "-"
				+ RandomString.getHex(4) + "-" + RandomString.getHex(12);
	}
}
