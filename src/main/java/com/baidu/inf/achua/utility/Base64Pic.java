package com.baidu.inf.achua.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class Base64Pic {
	private static Base64 encoder = new Base64();

	// 图片转化成base64字符串
	public static String PictoBase64String(String filename) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(filename);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		return new String(encoder.encode(data));// 返回Base64编码过的字节数组字符串
	}

	// base64字符串转化成图片
	public static boolean StringtoBase64Pic(String filename, String content) { // 对字节数组字符串进行Base64解码并生成图片
		if (content == null) // 图像数据为空
			return false;
		try {
			// Base64解码
			byte[] b = encoder.decode(content);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(filename);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//	public static void main(String[] args) {
//		System.out.println(Base64Pic.PictoBase64String("C:/Users/cuipeng03/Desktop/tmp/Palo.jpg"));
//		System.out.println(Base64Pic.StringtoBase64Pic("C:/Users/cuipeng03/Desktop/tmp/Paloxx.jpg",
//				Base64Pic.PictoBase64String("C:/Users/cuipeng03/Desktop/tmp/Palo.jpg")));
//	}
}
