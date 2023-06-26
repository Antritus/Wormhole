package me.antritus.minecraft_server.wormhole.commands.request;

import me.antritus.minecraft_server.wormhole.Wormhole;
import me.antritus.minecraft_server.wormhole.astrolminiapi.ColorUtils;
import me.antritus.minecraft_server.wormhole.astrolminiapi.NotNull;
import me.antritus.minecraft_server.wormhole.astrolminiapi.Nullable;
import me.antritus.minecraft_server.wormhole.commands.CoreCommand;
import me.antritus.minecraft_server.wormhole.events.PlayerTabCompleteRequestEvent;
import me.antritus.minecraft_server.wormhole.events.TpRequestEventFactory;
import me.antritus.minecraft_server.wormhole.events.request.TpRequestPlayerPrepareParseEvent;
import me.antritus.minecraft_server.wormhole.manager.TeleportRequest;
import me.antritus.minecraft_server.wormhole.manager.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @since 1.0.0-snapshot
 * @author antritus, lunarate
 */
public class CMDRequests extends CoreCommand {
	public CMDRequests(){
		super("tprequests");
		setDescription(Wormhole.configuration.getString("commands.tprequests.description", "Allows player to see teleport requests."));
		setUsage(Wormhole.configuration.getString("commands.tprequests.usage", "/tprequests"));
		setAliases(Wormhole.configuration.getStringList("commands.tprequests.aliases"));
	}
	@Override
	public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args){
		if (!(commandSender instanceof Player player)){
			playerOnly();
			return true;
		}
		User user = Wormhole.manager.getUser(player);
		Player other = null;
		if (args.length > 0){
			other = Bukkit.getPlayer(args[0]);
			if (other == null){
				player.sendMessage(ColorUtils.translateComp(Wormhole.configuration.getString("commands.tprequests.unknown-player")));
				return true;
			}
			TpRequestPlayerPrepareParseEvent parseEvent = TpRequestEventFactory.createSendPrepareEvent("tprequests", player, other);
			TpRequestEventFactory.trigger(parseEvent);
			if (parseEvent.isCancelled()){
				return true;
			}
			user = Wormhole.manager.getUser(other);
			if (user == null){
				throw new RuntimeException("Could not get user of: "+ other.getName());
			}
		}
		if (user == null){
			throw new RuntimeException("Could not get user of: "+ player.getName());
		}
		StringBuilder sent = new StringBuilder();
		StringBuilder requested = new StringBuilder();
		for (TeleportRequest request : user.requests()) {
			if (request.isValid()){
				if (sent.length() != 0){
					sent.append(", ");
				}
				sent.append(Bukkit.getPlayer(request.getRequested()).getName());
			}
		}
		for (TeleportRequest request : user.others()) {
			if (request.isValid()){
				if (requested.length() != 0){
					requested.append(", ");
				}
				requested.append(Bukkit.getPlayer(request.getWhoRequested()).getName());
			}
		}
		if (sent.isEmpty()){
			sent.append("None");
		}
		if (requested.isEmpty()){
			requested.append("None");
		}
		String msgFormat = Wormhole.configuration.getString("commands.tprequests.format-self", "commands.tprequests.format-self").replace("%sent%", sent.toString()).replace("%received%", requested.toString());
		if (other != null){
			msgFormat = Wormhole.configuration.getString("commands.tprequests.format-other", "commands.tprequests.format-other").replace("%sent%", sent.toString()).replace("%received%", requested.toString()).replace("%who%", other.getName());
		}
		commandSender.sendMessage(ColorUtils.translateComp(msgFormat));
		return true;
	}

	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender commandSender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
		if (args.length==1) {
			Player sender = (Player) commandSender;
			List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
			PlayerTabCompleteRequestEvent e = new PlayerTabCompleteRequestEvent(sender, players);
			Bukkit.getServer().getPluginManager().callEvent(e);
			List<String> finalList = new ArrayList<>();
			for (Player player : players) {
				finalList.add(player.getName());
			}
			return finalList;
		}
		return Collections.singletonList("");
	}

}