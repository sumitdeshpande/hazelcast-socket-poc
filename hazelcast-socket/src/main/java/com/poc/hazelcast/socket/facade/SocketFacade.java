package com.poc.hazelcast.socket.facade;

import org.springframework.stereotype.Service;

import com.poc.hazelcast.socket.domain.TestResponse;

@Service
public class SocketFacade {
	
	public TestResponse takePayment(String id, String timeout) {
		TestResponse c = new TestResponse();
		c.setResponseTest(" Test Response for "+id);
		return c;
	}
	
	public TestResponse cancelPayment( String id, String timeout) {
		TestResponse c = new TestResponse();
		c.setResponseTest(" Test Response for "+id);
		return c;
	}

}
