package com.gomaster.game.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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


public class UDPGameServer implements IServer{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UDPGameServer.class);
	
	private int port;
	private Thread listen_thread;
	private volatile boolean is_listening = false;
	
	// TCP Infrastructure
	// private ServerSocket serverSocket; // socket object for TCP on the Serverside
	// private Socket clientSocket; // socket object for TCP
	
	// UDP Infrastructure
	private DatagramSocket udp_socket; // socket object for UDP
	private byte[] mutableDatagramPacketBuffer = new byte[MAX_PACKET_SIZE * 10];
	private static int MAX_PACKET_SIZE = 1024; 
	
	
	public UDPGameServer(int port) {
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
		LOGGER.info("Starting Game Server at {}", port);
		is_listening = true;

		try {
			// serverSocket = new ServerSocket(port);
			// clientSocket = serverSocket.accept();
			udp_socket = new DatagramSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Server socket initialization failed with {}", e);
		}
		
		listen_thread = new Thread(()-> listen(), "ListenerThread");
		listen_thread.start();
	}
	
	@Override
	public void stop() {
		udp_socket.close();
	}
	
	@Override
	public void restart() {
		start();
		stop();
	}
	
	private void listen() {
		while(is_listening) {
			try {
				DatagramPacket mutableDatagramPacket = new DatagramPacket(mutableDatagramPacketBuffer, MAX_PACKET_SIZE);
				udp_socket.receive(mutableDatagramPacket);
				process(mutableDatagramPacket.getData());
			} catch (IOException e) {
				LOGGER.error("UDP Socket failed to cache received DatagramPacket due to {}", e);
			}

		}
	}
	
	private void process(byte[] packet) {

	}
	
	public void sendUDP(byte[] post_processed, InetAddress address, int port) {
		assert(udp_socket.isConnected());
		 
		try {
			DatagramPacket packet = new DatagramPacket(post_processed, post_processed.length, address, port); // might need to break it down to small chuncks
			udp_socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error("UDP packet sending failed due to {}", e);
		}
	}

	@Override
	public boolean send() {
		// TODO Auto-generated method stub
		return false;
	}
}
