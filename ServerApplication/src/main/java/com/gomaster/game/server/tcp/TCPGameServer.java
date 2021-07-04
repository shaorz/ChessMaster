package com.gomaster.game.server.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gomaster.game.server.IServer;

/*
 * UDP vs TCP
 * 
 * TCP is built on top of UDP, and TCP makes sure all the packages are transferred in sequence and get delivered to the receiver
 * TCP : structured connection
 * 
 * UDP is more suitable for real time data, there's no guarantee that the data is really delivered to the receiver
 * UDP : mail system, only send data to a port on an address
 * 
 * 
 * TCP : chatroom msg, files
 * UDP : positions
 */

/*
 * Socket: mailbox of the server
 */


public class TCPGameServer implements IServer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TCPGameServer.class);
	
	private int port;
	private Thread listen_thread;
	private volatile boolean is_listening = false;
	
	// TCP Infrastructure
	private ServerSocket serverSocket; // socket object for TCP on the Serverside
	private Socket clientSocket; // socket object for TCP
	private PrintWriter out;
    private BufferedReader in;
    
	public TCPGameServer(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public void start() {
		LOGGER.info("Starting TCP Game Server at {}", port);
		is_listening = true;
		

		try {
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        String greeting = in.readLine();
	            if ("hello server".equals(greeting)) {
	                out.println("hello client");
	            }
	            else {
	                out.println("unrecognised greeting");
	            }
		} catch (IOException e) {
			LOGGER.error("Server socket initialization failed with {}", e);
		}
		
		listen_thread = new Thread(()-> listen(), "ListenerThread");
		listen_thread.start();
	}
	
	@Override
	public void stop() {
		try {
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			LOGGER.error("Server socket couldn't stop due to {}", e);
		}
	}
	
	@Override
	public void restart() {
		start();
		stop();
	}
	
	private void listen() {
		while(is_listening) {

		}
	}
	
	private void process(byte[] packet) {


	}
	
	public void sendTCP(byte[] post_processed, InetAddress address, int port) {
		assert(clientSocket.isConnected());
		 
	}

	@Override
	public boolean send() {
		// TODO Auto-generated method stub
		return false;
	}
}
