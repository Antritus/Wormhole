package me.antritus.minecraft_server.wormhole.manager;

import me.antritus.minecraft_server.wormhole.Wormhole;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @since 1.0.0-snapshot
 * @author antritus
 */
public class UserManager implements Listener {
	private final Wormhole main;
	private BukkitTask task;
	public UserManager(Wormhole main) {
		this.main = main;
	}
	public void onEnable(){
		if (Bukkit.getOnlinePlayers().size()>0){
			Bukkit.getOnlinePlayers().forEach((player)->{
				main.getUserDatabase().load(player.getUniqueId());
				User user = main.getUserDatabase().getKnownNonNull(player);
				user.lastOnline = -1;
				user.online = true;
				main.getUserDatabase().save(user);
			});
		}
		task = new BukkitRunnable() {
			@Override
			public void run() {
			}
			// 30 minutes = 20 * 60 * 30 = 36_000
		}.runTaskTimerAsynchronously(main, 0, 36_000);
	}
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach(player->{
			User user = main.getUserDatabase().getKnownNonNull(player);
			user.lastOnline = System.currentTimeMillis();
			user.online = false;
			main.getUserDatabase().save(user);
		});
		task.cancel();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		main.getUserDatabase().load(event.getPlayer().getUniqueId());
		User user = main.getUserDatabase().getKnownNonNull(event.getPlayer());
		user.lastOnline = -1;
		user.online = true;
		main.getUserDatabase().save(user);
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		if (main.getUserDatabase().get(event.getPlayer()) == null){
			return;
		}
		User user = main.getUserDatabase().getKnownNonNull(event.getPlayer());
		user.lastOnline = System.currentTimeMillis();
		user.online = false;
		main.getUserDatabase().save(user);
		main.getUserDatabase().unload(event.getPlayer());
	}
}
