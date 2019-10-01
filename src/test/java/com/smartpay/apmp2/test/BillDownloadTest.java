package com.smartpay.apmp2.test;

import com.smartpay.apmp2.pay.ApmpClient;
import com.smartpay.apmp2.request.BillDownLoadRequest;
import com.smartpay.apmp2.response.BillDownLoadResponse;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

public class BillDownloadTest extends MyBaseTest{
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	BillDownLoadRequest request = new BillDownLoadRequest();
	BillDownLoadResponse response = null;
	@Before
	public void setup() {
		//设置请求环境参数
		request.setAction("mcht/bill/download");
		request.setVersion("2.0");
		request.setCoopId("JB17002");
//		request.setBillDate("20170718");
		request.setBillDate("20190924");
	}
		

	@Test
	public void billDownloadTest(){
		ApmpClient apmpClient = initApmpClient();
		BillDownLoadResponse billDownLoadResponse = apmpClient.billDownload(request);
		printObject(billDownLoadResponse);
	}

}
