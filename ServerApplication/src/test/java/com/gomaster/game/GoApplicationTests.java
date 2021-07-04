package com.gomaster.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.gomaster.game.client.tcp.TCPClient;
import com.gomaster.game.constants.Constants;

@RunWith(JUnit4.class)
class GoApplicationTests {
	
	private static final String LOCALHOST = "127.0.0.1";
	private static final String DUMMY_MSG = "hello server";
	private TCPClient client;
	
	@Before
	public void setup() {
	    client = new TCPClient();
	    client.startConnection(LOCALHOST, Constants.SERVER_TEST_PORT);
	}
	
	@Test
	public void TCPClientConnection() {
	    String resp1 = client.sendMessage("hello");
	    String resp2 = client.sendMessage("world");
	    String resp3 = client.sendMessage("!");
	    String resp4 = client.sendMessage(".");
	    String resp5 = client.sendMessage(DUMMY_MSG);
	    
	    assertEquals("hello", resp1);
	    assertEquals("world", resp2);
	    assertEquals("!", resp3);
	    assertEquals("good bye", resp4);
	    assertEquals("hello client", resp5);

	}
	
	@Test
	public void givenClient1_whenServerResponds_thenCorrect() {
		TCPClient client1 = new TCPClient();
	    client.startConnection(LOCALHOST, Constants.SERVER_TEST_PORT);
	    String msg1 = client1.sendMessage("hello");
	    String msg2 = client1.sendMessage("world");
	    String terminate = client1.sendMessage(".");
	    
	    assertEquals(msg1, "hello");
	    assertEquals(msg2, "world");
	    assertEquals(terminate, "bye");
	}

	@Test
	public void givenClient2_whenServerResponds_thenCorrect() {
		TCPClient client2 = new TCPClient();
	    client.startConnection(LOCALHOST, Constants.SERVER_TEST_PORT);
	    String msg1 = client2.sendMessage("hello");
	    String msg2 = client2.sendMessage("world");
	    String terminate = client2.sendMessage(".");
	    
	    assertEquals(msg1, "hello");
	    assertEquals(msg2, "world");
	    assertEquals(terminate, "bye");
	}
	
	@After
	public void tearDown() {
	    client.stopConnection();
	}

}
