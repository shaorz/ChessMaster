package com.gomaster.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gomaster.game.constants.Constants;
import com.gomaster.game.server.IServer;
import com.gomaster.game.server.tcp.TCPGameServer;
import com.gomaster.game.server.udp.UDPGameServer;

@SpringBootApplication
public class GoMasterApplication {

	public static void main(String[] args) {
		
		IServer tcpServer = new TCPGameServer(Constants.SERVER_TEST_PORT);
		tcpServer.start();
		
		SpringApplication.run(GoMasterApplication.class, args);
	}
}
