package com.gomaster.game.client;

import com.gomaster.game.generated.GameData;

public interface IClient {
	
	public void start() ;
	
	public void stop() ;
	
	public void restart() ;
	
	public void send(GameData data);
}
