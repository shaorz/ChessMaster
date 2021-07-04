package com.gomaster.game.client.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TCPClient.class);
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			LOGGER.error("Client can't find the target server {}:{}", ip, port);
			LOGGER.error("Exception:{}", e);
		} catch (IOException e) {
			LOGGER.error("Client can't connect to the target server {}:{}", ip, port);
			LOGGER.error("Exception:{}", e);
		}
    }

    public String sendMessage(String msg) {
    	String resp = "";
		try {
			out.println(msg);	        
			resp = in.readLine();
		} catch (IOException e) {
			LOGGER.error("Client can't send {} to the target server due to {}", msg, e);
		}
        return resp;
    }

    public void stopConnection() {
        try {
			in.close();
			out.close();
	        clientSocket.close();
		} catch (IOException e) {
			LOGGER.error("Client can't stop the connection to the target server due to {}", e);
		}
        
    }
}
