package me.antritus.minecraft_server.wormhole.events.database;

import me.antritus.minecraft_server.wormhole.manager.User;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class UserLoadEvent extends UserEvent {

	public UserLoadEvent(User user) {
		super(user);
	}

	private static final HandlerList HANDLER_LIST = new HandlerList();
	public static HandlerList getHandlerList(){
		return HANDLER_LIST;
	}
	@Override
	public @NotNull HandlerList getHandlers() {
		return getHandlerList();
	}
}
