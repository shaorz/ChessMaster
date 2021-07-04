package com.gomaster.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gomaster.game.server.IServer;
import com.gomaster.game.server.tcp.TCPGameServer;
import com.gomaster.game.server.udp.UDPGameServer;

@SpringBootApplication
public class GoMasterApplication {

	public static void main(String[] args) {
		
		IServer udpServer = new UDPGameServer(8132);
		IServer tcpServer = new TCPGameServer(8133);
		udpServer.start();
		tcpServer.start();
		
		SpringApplication.run(GoMasterApplication.class, args);
	}
}
