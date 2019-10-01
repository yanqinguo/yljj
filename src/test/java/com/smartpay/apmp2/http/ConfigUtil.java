package com.smartpay.apmp2.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class ConfigUtil {
	private static final String CONFIG_FILEPATH = ".\\src\\main\\resources\\Config.properties";

	private static Properties prop;
	static {
		prop = new Properties();
		try {
			InputStream in = new FileInputStream(CONFIG_FILEPATH);
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPropertie(String key) {
		if (null == key) {
			return "";
		}
		if (null == prop) {
			return "";
		}
		String value = prop.getProperty(key);
		if (null == value) {
			return "";
		}
		try {
			return new String(value.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/** 以下测试与生产环境参数由APMP统一分配 */
	/** APP的ID */
	public static String getAppKey() {
		return getPropertie("app.key");
	}

	/** 前置地址 */
	public static String getUrl() {
		return getPropertie("url");
	}

	/** 平台公钥配置 */
	public static String getHostPubKey() {
		return getPropertie("host.pub.key");
	}

	/** 商户号 */
	public static String getBankMchtId() {
		return getPropertie("bank.mcht.id");
	}

	/** 商户私钥配置 */
	public static String getPrivateKeyStr() {
		return getPropertie("mcht.private.key");
	}

	/** 商户公钥配置 */
	public static String getPublicKeyStr() {
		return getPropertie("mcht.pub.key");
	}

	/** 接入方私钥配置 */
	public static String getCoopPrivateKey() {
		return getPropertie("coop.private.key");
	}

}
