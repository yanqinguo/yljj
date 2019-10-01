package com.smartpay.apmp2.test;

import com.smartpay.apmp2.http.ConfigUtil;
import com.smartpay.apmp2.pay.ApmpClient;
import com.smartpay.apmp2.request.MerEnterRequest;
import com.smartpay.apmp2.response.MerEnterResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

/**
 * Created by james on 2017/7/5 0005.
 */
public class InfoEnterTest extends MyBaseTest{

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	MerEnterRequest request = new MerEnterRequest();
	MerEnterResponse response = new MerEnterResponse();


	@Before
	public void setup() {
		request.setAction("mcht/info/enter");
		request.setVersion("2.0");
		request.setExpanderCd("0148160070");//cebw:0303200000 //sh:0199993070 //test:0199980077  product:0848160060  yuan:0148160070
		request.setPublicKeyStr(ConfigUtil.getPublicKeyStr());//C公
		//request.setCoopMchtId("616009677");
		//request.setCoopMchtId("201902041313480001");
		request.setCoopMchtId("20190204131348mtwd2fjws4");
		
		request.setMchtName("闽台轮渡");
		request.setMchtShortName("闽台");
		request.setParentMchtId("");
		request.setBizLicense("205421341240021452");
		request.setLegalIdExpiredTime("2999-01-01");
		request.setIdNo("320281199111165014");
		request.setMchtAddr("上海市张江");
//		request.setProvince("32");//上海
//		request.setCity("3205");//上海市
//		request.setArea("320583");//浦东新区
		request.setProvince("35");//福建
		request.setCity("3502");//厦门
		request.setArea("350203");//思明区
		request.setAccountType("1");//0-公户，1-私户
		request.setAccount("2015091000056956");
		request.setAccountName("胖虎");
		request.setBankCode("305100000013");
		request.setBankName("光大银行");
		request.setOpenBranch("103504029330");
		request.setContactName("胖虎");
		request.setContactMobile("18817571111");
		request.setContactEmail("18817571111@163.com");
		request.setMchtLevel("2");//1-分店，2-商户
		request.setOpenType("1");//1-个人，2-企业
		//此notifyUrl和CSB的异步通知相同，本地测试的话先开启AppLauncher中的监听才会发通知
		//request.setNotifyUrl("http://192.168.88.123:8090/msc-receiver/its/notify");
		request.setNotifyUrl("http://192.168.1.119:8090/msc-receiver/its/notify");

		JSONArray array = new JSONArray();
		JSONObject temp1 = new JSONObject();
		temp1.put("acquirerType", "baidu");
		temp1.put("scale", "10.0");
		temp1.put("countRole", "0");
		temp1.put("tradeType", "4011");
		array.add(temp1);

		JSONObject temp2 = new JSONObject();
		temp2.put("acquirerType", "wechat");
		temp2.put("scale", "10.0");
		temp2.put("countRole", "0");
		temp2.put("tradeType", "203");
		array.add(temp2);

		JSONObject temp3 = new JSONObject();
		temp3.put("acquirerType", "qq");
		temp3.put("scale", "10.0");
		temp3.put("countRole", "0");
		temp3.put("tradeType", "Q000001");//Q000001
		array.add(temp3);

//		JSONObject temp4 = new JSONObject();
//		temp4.put("acquirerType", "jd");
//		temp4.put("scale", "10.0");
//		temp4.put("countRole", "0");
//		temp4.put("tradeType", "001001");//Q000001
//		array.add(temp4);

		JSONObject temp5 = new JSONObject();
		temp5.put("acquirerType", "alipay");
		temp5.put("scale", "10.0");
		temp5.put("countRole", "0");
		temp5.put("tradeType", "5411");//Q000001//5411//2015050700000000
		array.add(temp5);
		
//		JSONObject temp6 = new JSONObject();
//		temp6.put("acquirerType", "jdOnline");
//		temp6.put("scale", "10.0");
//		temp6.put("countRole", "0");
//		temp6.put("tradeType", "001001");//Q000001
//		array.add(temp6);

		request.setAcquirerTypes(array.toString());
	}

	@Test
	public void merEnterTest(){
		ApmpClient apmpClient = initApmpClient();
		MerEnterResponse merEnterResponse = apmpClient.merEnter(request);
		printObject(merEnterResponse);

	}


}
