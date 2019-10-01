package com.smartpay.apmp2.response;

public class BillDownLoadResponse extends BaseResponse{
	private String billUrl;

	public String getBillUrl() {
		return billUrl;
	}

	public void setBillUrl(String billUrl) {
		this.billUrl = billUrl;
	}
	
}
