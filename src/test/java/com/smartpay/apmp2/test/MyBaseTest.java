package com.smartpay.apmp2.test;

import com.smartpay.apmp2.http.ConfigUtil;
import com.smartpay.apmp2.pay.ApmpClient;

import java.lang.reflect.Field;

/**
 * Created by james on 2017/7/5 0005.
 */
public class MyBaseTest {

	public static ApmpClient initApmpClient() {
		ApmpClient apmpClient = new ApmpClient();
		apmpClient.setAppKey(ConfigUtil.getAppKey());
		apmpClient.setPrivateKey(ConfigUtil.getCoopPrivateKey());//B私
		apmpClient.setPublicKey(ConfigUtil.getHostPubKey());//A公
		apmpClient.setServerUrl(ConfigUtil.getUrl());
		return apmpClient;
	}

	@SuppressWarnings("rawtypes")
	protected static void printObject(Object e) {
		try {
			Class cls = e.getClass();
			Field[] fields = cls.getDeclaredFields();

			Class pClass = e.getClass().getSuperclass();
			Field[] pFields = pClass.getDeclaredFields();

			StringBuffer sb = new StringBuffer(e.getClass().getName());
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				f.setAccessible(true);
				if (f.get(e) != null)
					sb.append("\n\t" + f.getName() + ":\t" + f.get(e).toString());
			}
			for (int i = 0; i < pFields.length; i++) {
				Field f = pFields[i];
				f.setAccessible(true);
				if (f.get(e) != null)
					sb.append("\n\t" + f.getName() + ":\t" + f.get(e).toString());
			}
			System.out.println(sb);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
	}


}
