package me.antritus.minecraft_server.wormhole.events.block;

import me.antritus.minecraft_server.wormhole.Wormhole;
import me.antritus.minecraft_server.wormhole.events.WormholeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TpToggleEvent extends WormholeEvent {
	private final Player player;
	public TpToggleEvent(@NotNull Wormhole wormhole, @NotNull Player player) {
		super(wormhole);
		this.player = player;
	}
	public Player getPlayer(){
		return player;
	}
	private static final HandlerList HANDLERS = new HandlerList();
	public static HandlerList getHandlerList(){
		return HANDLERS;
	}
	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLERS;
	}
}
