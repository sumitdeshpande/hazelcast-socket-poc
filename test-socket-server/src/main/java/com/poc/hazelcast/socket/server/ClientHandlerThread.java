package com.poc.hazelcast.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandlerThread implements Runnable {
	private Socket clientSocket;

	public ClientHandlerThread(Socket clientSocket) {
		this.clientSocket = clientSocket;

	}

	public void run() {
		System.out.println("Waiting for input.....");

		PrintWriter outputWriter;
		try {
			outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String inputLine;

			String commandId = null;
			String commandDetails = null;
			String waitTime = null;
			
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Server: " + inputLine);
				String inputArray[] = inputLine.split("\\|");
				if (inputArray.length > 0) {
					commandId = inputArray[0];
					commandDetails = inputArray[1];
					waitTime = inputArray[2];
				}

				switch (commandId) {
				case "100":
					handleTakePayment(commandId, commandDetails, waitTime, outputWriter);
					break;
				case "999":
					handleCancelPayment(commandId, commandDetails, waitTime, outputWriter);
					break;
				default:
					break;
				}

				break;

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void handleTakePayment(String commandId, String commandDetails, String waitTime, PrintWriter outputWriter) {

		String output = "";
		output= output.concat("101").concat("|").concat(commandDetails).concat("\n");

		if (null != waitTime && Long.valueOf(waitTime) > 0) {
			try {
				Thread.sleep(Long.valueOf(waitTime));

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Client thread output " + output);
		outputWriter.println(output);

	}

	private void handleCancelPayment(String commandId, String commandDetails, String waitTime , PrintWriter outputWriter) {

		String output =  "";
		output= output.concat("9991").concat("|").concat(commandDetails).concat("\n");

		if (null != waitTime && Long.valueOf(waitTime) > 0) {
			try {
				Thread.sleep(Long.valueOf(waitTime));

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Cancel Client thread output 1 " + output);
		outputWriter.println(output);
		
		output = "";
		output =output.concat("101").concat("|").concat(commandDetails).concat("response----").concat("\n");
		System.out.println("Cancel Client thread output 2 " + output);
		outputWriter.println(output);

	}

}
