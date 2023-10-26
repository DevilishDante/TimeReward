package fr.devilishdante.Ecaerios.TimeReward;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TrCommands implements CommandExecutor {
    private final TrPlayerManager tpm = TrCore.instance.tpm;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(sender instanceof Player player){
            if (args.length == 0) {
                if(player.hasPermission(TrCore.permAdmin)) {
                    new TrMenu().OpenMenu(player);
                } else {
                    String noperm = TrCore.msgConfig.getString("NOPERM");
                    player.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(Objects.requireNonNull(noperm)));
                }
                return true;
            }
            switch (args[0].toLowerCase()) {
                case "info" -> {
                    String version = TrCore.msgConfig.getString("VERSION");
                    player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(Objects.requireNonNull(version)));
                }
                case "reload" -> {
                    if (player.hasPermission(TrCore.permAdmin)) {
                        String reload = TrCore.msgConfig.getString("RELOAD");
                        TrCore.instance.reloadConfig();
                        TrCore.prefix = TrCore.instance.getConfig().getString("prefix");
                        TrCore.pnjname = TrCore.instance.getConfig().getString("pnj_name");
                        TrCore.instance.reloadCustomConfig();
                        TrCore.instance.reloadUsersConfig();
                        TrCore.drMaterial_close = Material.matchMaterial(Objects.requireNonNull(TrCore.instance.getConfig().getString("Material.lock")));
                        TrCore.drMaterial_open = Material.matchMaterial(Objects.requireNonNull(TrCore.instance.getConfig().getString("Material.unlock")));
                        TrCore.drMaterial_incoming = Material.matchMaterial(Objects.requireNonNull(TrCore.instance.getConfig().getString("Material.incoming")));
                        TrCore.drMaterial_noperm = Material.matchMaterial(Objects.requireNonNull(TrCore.instance.getConfig().getString("Material.noperm")));
                        player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(Objects.requireNonNull(reload)));
                    } else {
                        String noperm = TrCore.msgConfig.getString("NOPERM");
                        player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(Objects.requireNonNull(noperm)));
                    }
                }
                case "help" -> {
                    String help = TrCore.msgConfig.getString("HELP");
                    player.sendMessage(TrCore.convert(Objects.requireNonNull(help)));
                }
                case "debug" -> {
                    if (player.hasPermission(TrCore.permAdmin)) {
                        if (args.length == 3) {
                            Player onlinePlayer = Bukkit.getPlayerExact(args[1]);
                            String Typr = args[2];
                            UUID uuid = Objects.requireNonNull(onlinePlayer).getUniqueId();
                            if (((onlinePlayer != null) && (Typr.equalsIgnoreCase("journalier"))) || ((onlinePlayer != null) && (Typr.equalsIgnoreCase("hebdomadaire")))) {
                                if (tpm.getCooldownAsId(uuid, Typr)) {
                                    tpm.DelCooldown(uuid, Typr, false);
                                    String debug_success = Objects.requireNonNull(TrCore.msgConfig.getString("DEBUG_SUCCESS")).replaceAll("%PLAYER_ARG%", args[1]);
                                    String debug_success2 = debug_success.replaceAll("%REWARD_TYPE%", args[2]);
                                    player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(debug_success2));
                                } else {
                                    String debug_fail = Objects.requireNonNull(TrCore.msgConfig.getString("DEBUG_FAIL")).replaceAll("%PLAYER_ARG%", args[1]);
                                    player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(debug_fail));
                                }
                            } else {
                                String not_online = Objects.requireNonNull(TrCore.msgConfig.getString("NOT_ONLINE")).replaceAll("%PLAYER_ARG%", args[1]);
                                player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(not_online));
                            }
                        } else {
                            String badsyntax = TrCore.msgConfig.getString("BAD_SYNTAX");
                            player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(Objects.requireNonNull(badsyntax)));
                        }
                    } else {
                        String noperm = TrCore.msgConfig.getString("NOPERM");
                        player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(Objects.requireNonNull(noperm)));
                    }
                }
                default -> {
                    String badsyntax = TrCore.msgConfig.getString("BAD_SYNTAX");
                    player.sendMessage(TrCore.convert(TrCore.prefix) + TrCore.convert(Objects.requireNonNull(badsyntax)));
                }
            }
        } else {
            String console = TrCore.msgConfig.getString("CONSOLE_SENDER");
            sender.sendMessage(TrCore.convert(TrCore.prefix)+TrCore.convert(Objects.requireNonNull(console)));
        }
        return false;
    }
}