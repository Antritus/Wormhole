package me.antritus.minecraft_server.wormhole.events.request;

import me.antritus.minecraft_server.wormhole.astrolminiapi.NotNull;
import me.antritus.minecraft_server.wormhole.manager.TeleportRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * This event is fired when player accepts teleport
 */
public class TpRequestAcceptEvent extends TpRequestEvent implements Cancellable {
	private static final HandlerList HANDLERS = new HandlerList();

	public static HandlerList getHandlerList(){
		return HANDLERS;
	}
	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLERS;
	}
	private final TeleportRequest request;
	private boolean isCancelled = false;
	/**
	 * @param who       who requested
	 * @param requested requested player
	 */
	public TpRequestAcceptEvent(@NotNull Player who, @NotNull Player requested, @NotNull TeleportRequest request) {
		super(who, requested);
		this.request = request;
	}

	/**
	 * Returns the teleport request instance.
	 * @return request instance
	 */
	public TeleportRequest getRequest() {
		return request;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		isCancelled = b;
	}
}
