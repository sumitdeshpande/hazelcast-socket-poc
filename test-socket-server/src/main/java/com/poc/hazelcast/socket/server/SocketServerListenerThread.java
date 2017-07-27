package com.poc.hazelcast.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerListenerThread implements Runnable {

	private ServerSocket serverSocket;

	public SocketServerListenerThread(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void run() {
		System.out.println(" SocketServerListenerThread Started....");

		Socket clientSocket = null;

		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}

			System.out.println("New Client Connection successful" + clientSocket.getInetAddress());

			ClientHandlerThread clientThread = new ClientHandlerThread(clientSocket);
			new Thread(clientThread).start();
		}
	}

}
