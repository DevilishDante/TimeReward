package fr.devilishdante.Ecaerios.TimeReward;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class TrEventPNJ implements Listener{
    @EventHandler
    public void DrRightClick(NPCRightClickEvent event){
        Player player = (Player)event.getClicker();
        String npc = event.getNPC().getRawName();
        if (npc.equals(TrCore.convert(TrCore.pnjname))) {
            new TrMenu().OpenMenu(player);
        }
    }
    @EventHandler
    public void DrLeftClick(NPCLeftClickEvent event){
        Player player = (Player)event.getClicker();
        String npc = event.getNPC().getRawName();
        if (npc.equals(TrCore.convert(TrCore.pnjname))) {
            new TrMenu().OpenMenu(player);
        }
    }
}