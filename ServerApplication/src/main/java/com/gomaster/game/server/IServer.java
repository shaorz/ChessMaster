package com.gomaster.game.server;

public interface IServer {
	
	public void start() ;
	
	public void stop() ;
	
	public void restart() ;
	
	public boolean send() ;

}
