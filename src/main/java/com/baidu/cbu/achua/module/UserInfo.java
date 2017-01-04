package com.baidu.cbu.achua.module;

public class UserInfo {
	public String userid = "56df644bb29d4ec3856ca2d0aa3497cf";
	// achuacui0101
	// public static String accessKey = "6148e048017246cbb0fe05b297519a30";
	// public static String secretKey = "d9ae7b47634f4608891300437c68b053";

	// achuacui0101 BCC api key
	public String accessKey = "763232cca91c44ec8dd7f9ee5d8020f1";
	public String secretKey = "51d91b2fe266442da4af54c45c7a1f09";

	// achuacui
	// public static String accessKey = "ZIggCKKwQfel5Kambty4PQoP";
	// public static String secretKey = "jTc7vDlYi3FVwtu2AzLsbwXruVpFc5i9";

	public UserInfo() {
	}

	public UserInfo(String accessKey, String secretKey) {
		super();
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
