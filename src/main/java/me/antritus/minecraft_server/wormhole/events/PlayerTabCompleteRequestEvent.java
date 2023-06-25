package me.antritus.minecraft_server.wormhole.events;

import me.antritus.minecraft_server.wormhole.astrolminiapi.NotNull;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.List;

public class PlayerTabCompleteRequestEvent extends PlayerEvent  {
	private static final HandlerList HANDLERS = new HandlerList();
	private final List<Player> players;
	public PlayerTabCompleteRequestEvent(@NotNull Player who, List<Player> players) {
		super(who);
		this.players = players;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public static HandlerList getHandlerList(){
		return HANDLERS;
	}
	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLERS;
	}
}
