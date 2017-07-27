package com.poc.hazelcast.socket.domain;

import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement(name = "TestResponse")
public class TestResponse {
	
	
	private String responseTest;
	
	private String errorCode;

	public String getResponseTest() {
		return responseTest;
	}

	public void setResponseTest(String responseTest) {
		this.responseTest = responseTest;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	

}
