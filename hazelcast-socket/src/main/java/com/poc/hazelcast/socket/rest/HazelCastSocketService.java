package com.poc.hazelcast.socket.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.poc.hazelcast.socket.controller.HazelCastSocketPOCcontroller;
import com.poc.hazelcast.socket.domain.TestResponse;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HazelCastSocketService {

	HazelCastSocketPOCcontroller hazelCastSocketPOCcontroller = new  HazelCastSocketPOCcontroller();

	@GET
	@Path("/payment/{reservationId}/{timeout}")
	public TestResponse takePayment(@PathParam("reservationId") String id, @PathParam("timeout")String timeout) {
		TestResponse response = null;
		try {
			response= hazelCastSocketPOCcontroller.takePayment(id, timeout);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setResponseTest(e.getMessage());
		}
		return response;
	}

	@GET
	@Path("/cancel/{reservationId}/{timeout}")
	public TestResponse cancelPayment(@PathParam("reservationId") String id,  @PathParam("timeout")String timeout) {
		
		TestResponse response = null;
		try {
			response=  hazelCastSocketPOCcontroller.cancelPayment(id, timeout);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
