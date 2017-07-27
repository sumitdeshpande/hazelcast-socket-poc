package com.poc.hazelcast.socket.server;

import java.io.IOException;
import java.net.ServerSocket;

public class TestSocketServer  {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(10008);
			//new Thread(null, new SocketServerListenerThread()).start();
			SocketServerListenerThread listenerThread= new SocketServerListenerThread(serverSocket);
			new Thread(listenerThread).start();;
		} catch (IOException e) {
			System.err.println("Could not listen on port: 10007.");
			System.exit(1);
		}

	}

	

}
