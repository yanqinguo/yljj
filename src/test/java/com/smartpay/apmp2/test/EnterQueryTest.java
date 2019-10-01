package com.smartpay.apmp2.test;

import com.smartpay.apmp2.pay.ApmpClient;
import com.smartpay.apmp2.request.EnterQueryRequest;
import com.smartpay.apmp2.response.EnterQueryResponse;
import org.junit.Before;
import org.junit.Test;

public class EnterQueryTest extends MyBaseTest{
	EnterQueryRequest request = new EnterQueryRequest();
	@Before
    public void setup() {
		request.setAction("mcht/info/query");
		request.setVersion("2.0");
		//request.setCoopMchtId("616009673");
		//request.setCoopMchtId("201902041313480001");
		//request.setCoopMchtId("130200980030571");
		request.setCoopMchtId("20190204131348mtwd2fjws4");
		
		
		request.setAcquirerType("wechat");
    }
	
	@Test
	public void enterQuery() {
		ApmpClient apmpClient = initApmpClient();
		EnterQueryResponse enterQueryResponse = apmpClient.enterQuery(request);
		printObject(enterQueryResponse);
	}
	
}
