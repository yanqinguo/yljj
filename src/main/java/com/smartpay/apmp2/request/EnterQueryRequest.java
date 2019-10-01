package com.smartpay.apmp2.request;

public class EnterQueryRequest extends BaseRequest {

	private String coopMchtId;
	private String custId;
	private String acquirerType;

	public String getAcquirerType() {
		return acquirerType;
	}

	public void setAcquirerType(String acquirerType) {
		this.acquirerType = acquirerType;
	}

	public String getCoopMchtId() {
		return coopMchtId;
	}

	public void setCoopMchtId(String coopMchtId) {
		this.coopMchtId = coopMchtId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

}
