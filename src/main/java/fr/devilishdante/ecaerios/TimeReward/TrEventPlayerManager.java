package fr.devilishdante.ecaerios.TimeReward;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

// Permet de récuperer les cd relative aux récompences dans le menu après un reboot éventuellement
public class TrEventPlayerManager implements Listener {
    private final TrPlayerManager tpm = TrCore.instance.tpm;
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        String header = "Users.";
        String yml_check = TrCore.UsersConfig.getString(header +uuid.toString());
        if (yml_check != null) {
            if (yml_check.contains(uuid.toString())) {
                if (!tpm.getCooldownAsId(uuid, "Journalier")) {
                    tpm.setCooldown(uuid, "Journalier", false, tpm.getCooldownConfigFile(uuid, "Journalier"));
                }
                if (!tpm.getCooldownAsId(uuid, "Hebdomadaire")) {
                    tpm.setCooldown(uuid, "Hebdomadaire", false, tpm.getCooldownConfigFile(uuid, "Hebdomadaire"));
                }
            }
        }
    }
}