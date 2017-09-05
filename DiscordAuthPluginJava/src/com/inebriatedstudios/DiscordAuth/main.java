package com.inebriatedstudios.DiscordAuth;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	// Fired when plugin is first enabled
	@Override
	public void onEnable(){
		try {
			socketServer.startSocket();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getCommand("discord").setExecutor(new discord());
	}
		
	// Fired when plugin is disabled
	@Override
	public void onDisable(){
			socketServer.closeSocket();
	}
}