package com.wyattgahm.teleborder;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Location;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TeleBorder extends PluginBase implements Listener {

	//useful meathods
	//int minSize = this.getConfig().getInt("MinCount", 0);
	//this.getLogger().info("hello");
	//this.getLogger().info(TextFormat.DARK_GREEN + "Enabled!");
	//this.getLogger().info(TextFormat.DARK_RED + "Disabled!");
	//this.saveDefaultConfig();
	//this.getServer().getPluginManager().registerEvents(this, this);
	private int top;
	private int bottom;
	private int left;
	private int right;
	
	@Override
	public void onLoad() {
		this.getLogger().info(TextFormat.WHITE + "TeleBorder Loaded");
	}
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.top = this.getConfig().getInt("Top", 10000);
		this.bottom = this.getConfig().getInt("Bottom", -10000);
		this.left = this.getConfig().getInt("Left", 10000);
		this.right = this.getConfig().getInt("Right", -10000);
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info(TextFormat.DARK_GREEN + "TeleBorder Enabled");
	}

	@Override
	public void onDisable() {
		this.getLogger().info(TextFormat.DARK_RED + "TeleBorder Disabled");
	}
	@EventHandler
	public void movementCheck(PlayerMoveEvent event){
		Player player = event.getPlayer();
		Location playerLocation = player.getLocation();
		if(playerLocation.getX() <= right) {safeTP(player,left - 1,(int)playerLocation.getZ());} // off right
		if(playerLocation.getX() >= left) {safeTP(player,right + 1,(int)playerLocation.getZ());} // off left
		if(playerLocation.getZ() <= bottom) {safeTP(player,(int)playerLocation.getX(),top - 1);}//off bottom
		if(playerLocation.getZ() >= top) {safeTP(player,(int)playerLocation.getX(),bottom + 1);}//off top
	}
	
	private void safeTP(Player p,int x, int z){
		p.noDamageTicks = 30; //1.5 seconds of living
		p.teleport(new Location(x,p.getLevel().getHighestBlockAt(x,z) + 1,z,p.getLocation().getYaw(),p.getLocation().getPitch(),p.getLocation().getLevel()), TeleportCause.PLUGIN );
	}
}
