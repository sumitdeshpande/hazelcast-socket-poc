package com.poc.hazelcast.socket.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.UUID;

import com.poc.hazelcast.socket.domain.TestResponse;

public class HazelCastSocketPOCcontroller {

	public TestResponse takePayment(String reservationId, String timeout) throws Exception {
		Socket socket = openSocket("localhost", 10007);
		String requestGuid = UUID.randomUUID().toString();
		String commandStr = prpeareCommand(reservationId, timeout, "100", requestGuid);
		sendCommand(socket, commandStr);
		String response = recevieTakePaymentResponse(socket, requestGuid);

		TestResponse c = new TestResponse();
		c.setResponseTest(" Test Response for " + response);

		return c;

	}

	public TestResponse cancelPayment(String reservationId, String timeout) throws Exception {
		Socket socket = openSocket("localhost", 10007);
		String requestGuid = UUID.randomUUID().toString();
		String commandStr = prpeareCommand(reservationId, timeout, "999", requestGuid);
		sendCommand(socket, commandStr);
		String response = recevieCancelPaymentResponse(socket);

		TestResponse c = new TestResponse();
		c.setResponseTest(" Test Response for " + response);

		return c;
	}

	private String prpeareCommand(String reservationId, String timeout, String commandId, String requestGuid) {

		return new String("").concat(commandId).concat("|").concat(reservationId).concat("|").concat(timeout)
				.concat("|guid-").concat(requestGuid);

	}

	private Socket openSocket(String server, int port) throws Exception {
		Socket socket;

		// create a socket with a timeout
		try {
			InetAddress inteAddress = InetAddress.getByName(server);
			SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);

			// create a socket
			socket = new Socket();

			// this method will block no more than timeout ms.
			int timeoutInMs = 10 * 1000; // 10 seconds
			socket.connect(socketAddress, timeoutInMs);

			return socket;
		} catch (SocketTimeoutException ste) {
			System.err.println("Timed out waiting for the socket.");
			ste.printStackTrace();
			throw ste;
		}
	}

	private void sendCommand(Socket socket, String writeTo) throws Exception {

		try {
			// write text to the socket
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(writeTo.concat("\n"));
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private String recevieTakePaymentResponse(Socket socket, String requestGuid) throws Exception {
		try {
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder sb = new StringBuilder();

			while (true) {
				if (checkinCache(requestGuid, sb)) {
					break;
				} else if (bufferedReader.ready()) {
					String str = bufferedReader.readLine();
					if (str.contains("101") ) {
						sb.append(str);
						break;
					}
				} else {
					Thread.sleep(200);
					// sleep thread for 200 ms
				}

			}
			// close the reader, and return the results as a String
			bufferedReader.close();
			socket.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private boolean checkinCache(String requestGuid, StringBuilder sb) {
		//lookup in cache for given guid 
		System.out.println(" checkinCache -" + requestGuid);
		//append cache response to sb
		return false;
	}

	private String recevieCancelPaymentResponse(Socket socket) throws Exception {
		try {
			// read text from the socket
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String str;
			boolean inflightTransactionRespRcvd = false;
			boolean cancelTransactionRespRcvd = false;

			while ((str = bufferedReader.readLine()) != null) {
				// all transaction responses like balance inquiry, refund etc.
				if (str.contains("101")) {
					sb.append(str);
					inflightTransactionRespRcvd = true;
				}
				if (str.contains("9991")) {
					sb.append(str);
					cancelTransactionRespRcvd = true;
				}
				if (inflightTransactionRespRcvd && cancelTransactionRespRcvd) {
					break;
				}
			}
			// close the reader, and return the results as a String
			bufferedReader.close();
			socket.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
