package com.inebriatedstudios.DiscordAuth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class discord implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;
			String pName = player.getDisplayName();
			Thread t = new Thread(new socketClient(pName));
			t.start();
		}
		return true;
	}
}
