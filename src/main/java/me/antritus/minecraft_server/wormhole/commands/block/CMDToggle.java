package me.antritus.minecraft_server.wormhole.commands.block;

import me.antritus.minecraft_server.wormhole.Wormhole;
import me.antritus.minecraft_server.wormhole.antsfactions.MessageManager;
import me.antritus.minecraft_server.wormhole.astrolminiapi.ColorUtils;
import me.antritus.minecraft_server.wormhole.astrolminiapi.CoreCommand;
import me.antritus.minecraft_server.wormhole.manager.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @since 1.0.0-snapshot
 * @author antritus
 */
public class CMDToggle extends CoreCommand {

	public CMDToggle(Wormhole wormhole) {
		super(wormhole, "tptoggle");
		setPermission("wormhole.toggle");
		setDescription(wormhole.getCommandConfig().getString("tptoggle.description", "tptoggle.description"));
		setUsage(wormhole.getConfig().getString("tptoggle.usage", "tptoggle.usage"));
		setAliases(wormhole.getConfig().getStringList("tptoggle.aliases"));
	}

	@Override
	public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
		MessageManager messageManager = wormhole.getMessageManager();
		if (!(commandSender instanceof Player player)){
			messageManager.message(commandSender, "command-parse.player-only");
			return true;
		}
		User user = wormhole.getUserDatabase().get(player);
		if (user == null){
			throw new RuntimeException("Could not get user of: "+ player.getName());
		}
		user.isAcceptingRequests = !user.isAcceptingRequests;
		messageManager.message(player, "toggle."+user.isAcceptingRequests, "%command%=tptoggle");
		return true;
	}


	@Override
	public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
		return Collections.singletonList("");
	}
}
