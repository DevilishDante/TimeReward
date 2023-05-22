package fr.devilishdante.Ecaerios.TimeReward;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrCommands implements CommandExecutor {
    private final TrPlayerManager tpm = TrCore.instance.tpm;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
            if (args.length == 0) {
				if(player.hasPermission(TrCore.permAdmin)) {
					new TrMenu().OpenMenu(player);
                } else {
					String noperm = TrCore.msgConfig.getString("NOPERM");
					player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(noperm));
                }
                return true;
            }
            switch (args[0].toLowerCase()) {
            case "info":
                String version = TrCore.msgConfig.getString("VERSION");
                player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(version));
                break;
            case "reload":
				if(player.hasPermission(TrCore.permAdmin)) {
                    String reload = TrCore.msgConfig.getString("RELOAD");
                    TrCore.instance.reloadConfig();
                    TrCore.prefix = TrCore.instance.getConfig().getString("prefix");
                    TrCore.pnjname = TrCore.instance.getConfig().getString("pnj_name");
                    TrCore.instance.reloadCustomConfig();
                    TrCore.instance.reloadUsersConfig();
                    TrCore.drMaterial_close = Material.matchMaterial(TrCore.instance.getConfig().getString("Material.lock"));
                    TrCore.drMaterial_open = Material.matchMaterial(TrCore.instance.getConfig().getString("Material.unlock"));
                    TrCore.drMaterial_incoming = Material.matchMaterial(TrCore.instance.getConfig().getString("Material.incoming"));
                    TrCore.drMaterial_noperm= Material.matchMaterial(TrCore.instance.getConfig().getString("Material.noperm"));
                    player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(reload));
				} else {
					String noperm = TrCore.msgConfig.getString("NOPERM");
                    player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(noperm));
				}
                break;
            case "help":
                String help = TrCore.msgConfig.getString("HELP");
                player.sendMessage(TrCore.convert(help));
                break;
            case "debug":
                if(player.hasPermission(TrCore.permAdmin)) {
                    if(args.length == 3) {
                        Player onlinePlayer = Bukkit.getPlayerExact(args[1]);
                        String Typr = args[2].toString();
                        UUID uuid = onlinePlayer.getUniqueId();
                        if(((onlinePlayer != null) && (Typr.toLowerCase().equals("journalier"))) || ((onlinePlayer != null) && (Typr.toLowerCase().equals("hebdomadaire"))) ){
                            if(tpm.getCooldownAsId(uuid, Typr)){
                                tpm.DelCooldown(uuid, Typr, false);
                                String debug_success = TrCore.msgConfig.getString("DEBUG_SUCCESS").replaceAll("%PLAYER_ARG%", args[1].toString());
                                String debug_success2 = debug_success.replaceAll("%REWARD_TYPE%", args[2].toString());
                                player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(debug_success2));
                            } else {
                                String debug_fail = TrCore.msgConfig.getString("DEBUG_FAIL").replaceAll("%PLAYER_ARG%", args[1]);
                                player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(debug_fail));
                            }
                        } else {
                            String not_online = TrCore.msgConfig.getString("NOT_ONLINE").replaceAll("%PLAYER_ARG%", args[1]);
                            player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(not_online));
                        }
                    } else {
                        String badsyntax = TrCore.msgConfig.getString("BAD_SYNTAX");
                        player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(badsyntax));
                    }
                } else {
                    String noperm = TrCore.msgConfig.getString("NOPERM");
                    player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(noperm));
                }
                break;
            default:
                String badsyntax = TrCore.msgConfig.getString("BAD_SYNTAX");
                player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(badsyntax));
                break;
			}
		} else {
            String console = TrCore.msgConfig.getString("CONSOLE_SENDER");
            sender.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(console));
        }
	return false;
    }
}