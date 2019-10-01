package com.smartpay.apmp2.request;

public class BaseRequest {

	private String action;
	private String version;

	public String getAction() {
		return action;
	}

	public String getVersion() {
		return version == null ? "2.0" : version;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
