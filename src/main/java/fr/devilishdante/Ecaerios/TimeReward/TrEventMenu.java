package fr.devilishdante.Ecaerios.TimeReward;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TrEventMenu implements Listener {
    private final TrPlayerManager tpm = TrCore.instance.tpm;
    private void checkPerm (InventoryClickEvent event, String perm, int Slot, int Tr, Player player, Integer time, String Typr){
        if (!Objects.equals(Typr, "incoming")){
            if (event.getRawSlot() == Slot ){
                if(player.hasPermission(perm)) {
                    UUID uuid = player.getUniqueId();
                    
                    
                    //if (tpm.getCooldownAsId(uuid, Typr) && tpm.getDiffTimesMillis(uuid, Typr) || ) {
                        
                    //} else if () {
                        
                    //}

                    if (!tpm.getCooldownAsId(uuid, Typr)) {
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                        tpm.setCooldown(uuid, Typr, true, cal);
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String reward = TrCore.instance.getConfig().getString("sounds.reward_yes");
                        player.playSound(player.getLocation(),Sound.valueOf(reward), 1.0f,1.0f);
                        String cmd = TrCore.instance.getConfig().getString("rewards.cmd_"+Tr).replace("%PLAYER%", player.getName()); 
                        Bukkit.dispatchCommand(console, cmd);
                        Inventory menu = event.getInventory();
                        String name_inv = event.getView().getTitle();

                        if (!name_inv.contains(TrCore.pnjname)){
                            String drname = TrCore.instance.getConfig().getString("menu.dr_name.locked");
                            List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.locked");
                            List<String> drlore_result = new ArrayList<>();
                            for (String dr : drlore) {
                                drlore_result.add(dr.replaceAll("%TIME%", tpm.getDiffTimesMillisToDate(uuid, Typr).toString()));
                            }
                            ItemStack DrItem = new ItemStack(TrCore.drMaterial_close,1);
                            ItemMeta DrMeta = DrItem.getItemMeta();
                            
                            DrMeta.setDisplayName(TrCore.convert(Objects.requireNonNull(drname)));
                            DrMeta.setLore(TrCore.convertL(drlore_result));
                            DrItem.setItemMeta(DrMeta);
                            menu.setItem(Slot, DrItem);
                        }
                    } else if (tpm.getCooldownAsId(uuid, Typr)){
                        if (tpm.getDiffTimesMillis(uuid, Typr)){
                            
                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                            tpm.setCooldown(uuid, Typr, true, cal);
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            String reward = TrCore.instance.getConfig().getString("sounds.reward_yes");
                            player.playSound(player.getLocation(),Sound.valueOf(reward), 1.0f,1.0f);
                            String cmd = TrCore.instance.getConfig().getString("rewards.cmd_"+Tr).replace("%PLAYER%", player.getName()); 
                            Bukkit.dispatchCommand(console, cmd);
                            Inventory menu = event.getInventory();
                            String name_inv = event.getView().getTitle();

                            if (!name_inv.contains(TrCore.pnjname)){
                                String drname = TrCore.instance.getConfig().getString("menu.dr_name.locked");
                                List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.locked");
                                List<String> drlore_result = new ArrayList<>();
                                for (String dr : drlore) {
                                    drlore_result.add(dr.replaceAll("%TIME%", tpm.getDiffTimesMillisToDate(uuid, Typr).toString()));
                                }
                                ItemStack DrItem = new ItemStack(TrCore.drMaterial_close,1);
                                ItemMeta DrMeta = DrItem.getItemMeta();
                                
                                DrMeta.setDisplayName(TrCore.convert(Objects.requireNonNull(drname)));
                                DrMeta.setLore(TrCore.convertL(drlore_result));
                                DrItem.setItemMeta(DrMeta);
                                menu.setItem(Slot, DrItem);
                            }
                        } else if (!tpm.getDiffTimesMillis(uuid, Typr)){
                            // si le gars à un cooldown, et clique ça actualise le temps dans le lore
                        
                            Inventory menu = event.getInventory();
                            String name_inv = event.getView().getTitle();
                            if (!name_inv.contains(TrCore.pnjname)){
                                String drname = TrCore.instance.getConfig().getString("menu.dr_name.locked");
                                List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.locked");
                                ItemStack DrItem = new ItemStack(TrCore.drMaterial_close,1);
                                ItemMeta DrMeta = DrItem.getItemMeta();
                                List<String> drlore_result = new ArrayList<>();
                                for (String dr : drlore) {
                                    drlore_result.add(dr.replaceAll("%TIME%", tpm.getDiffTimesMillisToDate(uuid, Typr).toString()));
                                }
                                DrMeta.setDisplayName(TrCore.convert(Objects.requireNonNull(drname)));
                                DrMeta.setLore(TrCore.convertL(drlore_result));
                                DrItem.setItemMeta(DrMeta);
                                menu.setItem(Slot, DrItem);
                        }
                        String reward_no = TrCore.instance.getConfig().getString("sounds.reward_no");
                        player.playSound(player.getLocation(),Sound.valueOf(reward_no), 1.0f,1.0f);
                        }
                    } else {
                        // si le gars à un cooldown, et clique ça actualise le temps dans le lore
                        Inventory menu = event.getInventory();
                        String name_inv = event.getView().getTitle();
                        if (!name_inv.contains(TrCore.pnjname)){
                            String drname = TrCore.instance.getConfig().getString("menu.dr_name.locked");
                            List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.locked");
                            ItemStack DrItem = new ItemStack(TrCore.drMaterial_close,1);
                            ItemMeta DrMeta = DrItem.getItemMeta();
                            List<String> drlore_result = new ArrayList<>();
                            for (String dr : drlore) {
                                drlore_result.add(dr.replaceAll("%TIME%", tpm.getDiffTimesMillisToDate(uuid, Typr).toString()));
                            }
                            DrMeta.setDisplayName(TrCore.convert(Objects.requireNonNull(drname)));
                            DrMeta.setLore(TrCore.convertL(drlore_result));
                            DrItem.setItemMeta(DrMeta);
                            menu.setItem(Slot, DrItem);
                        }
                        String reward_no = TrCore.instance.getConfig().getString("sounds.reward_no");
                        player.playSound(player.getLocation(),Sound.valueOf(reward_no), 1.0f,1.0f);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().toString().equals(TrCore.convert(TrCore.pnjname))){
            if (event.getRawSlot() <= 44){
                event.setCancelled(true);
            }
            String t1 = TrCore.instance.getConfig().getString("rewards_time.time_1");
            String t2 = TrCore.instance.getConfig().getString("rewards_time.time_2");
            String t3 = TrCore.instance.getConfig().getString("rewards_time.time_3");
            
            checkPerm(event, "timereward.1", 11, 1, player, Integer.parseInt(Objects.requireNonNull(t1)), "journalier");
            checkPerm(event, "timereward.2", 13, 2, player, Integer.parseInt(Objects.requireNonNull(t2)), "Hebdomadaire");
            checkPerm(event, "timereward.3", 15, 3, player, Integer.parseInt(Objects.requireNonNull(t3)), "incoming");

            if (event.getRawSlot() == 22 ){
                event.getWhoClicked().closeInventory();
                String closegui = TrCore.instance.getConfig().getString("sounds.close_gui");
                player.playSound(player.getLocation(),Sound.valueOf(closegui), 1.0f,1.0f);
            }
        }
    }
}