package fr.devilishdante.ecaerios.TimeReward;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;
import java.util.UUID;
import java.util.ArrayList;

public class TrMenu {
	private final TrPlayerManager tpm = TrCore.instance.tpm;
    public void OpenMenu(Player player) {
		
		String open_gui = TrCore.instance.getConfig().getString("sounds.open_gui");
		player.playSound(player.getLocation(),Sound.valueOf(open_gui), 1.0f,1.0f);

		Inventory menu = Bukkit.createInventory(player, 3*9, TrCore.convert(TrCore.pnjname));
		
		String deco1 = TrCore.instance.getConfig().getString("deco.dr_deco");
		String deco2 = TrCore.instance.getConfig().getString("deco.dr_deco2");

        assert deco1 != null;
        menu.setItem(0, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));
        menu.setItem(2, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));
        menu.setItem(4, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));
        menu.setItem(6, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));
        menu.setItem(8, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));

        menu.setItem(1, new ItemStack(Objects.requireNonNull(Material.matchMaterial(Objects.requireNonNull(deco2))),1));
        menu.setItem(3, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));
        menu.setItem(5, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));
        menu.setItem(7, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));
        menu.setItem(9, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));

        menu.setItem(17, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));

        menu.setItem(18, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));
        menu.setItem(20, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));
        menu.setItem(24, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));
        menu.setItem(26, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco1)),1));

        menu.setItem(19, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));
        menu.setItem(21, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));
        menu.setItem(23, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));
        menu.setItem(25, new ItemStack(Objects.requireNonNull(Material.matchMaterial(deco2)),1));

		setCustomItem(menu,11,true,"timereward.1", player, false, "Journalier", 1);

		setCustomItem(menu,13,true,"timereward.2", player, false, "Hebdomadaire", 2);

		setCustomItem(menu,15,true,"timereward.3", player, true, "incoming", 3);

		String bookname1 = TrCore.instance.getConfig().getString("menu.book_name_1");
		List<String> booklore1 = TrCore.instance.getConfig().getStringList("menu.book_lore_1");
		setCustomItem(menu,Material.BOOK,TrCore.convert(Objects.requireNonNull(bookname1)),4,true,true,TrCore.convertL(booklore1));

		String backn = TrCore.instance.getConfig().getString("menu.back_name");
		setCustomItem(menu,Material.SPRUCE_DOOR,TrCore.convert(Objects.requireNonNull(backn)),22,false,false,null);
		player.openInventory(menu);

	}

	private void setCustomItem (Inventory menu, Material block, String name, int slot, boolean enchant, boolean lorex,List<String> lore){
		ItemStack DrItem = new ItemStack(block,1);
		ItemMeta DrMeta = DrItem.getItemMeta();
		DrMeta.setDisplayName(name);
		if (lorex == true) {
			DrMeta.setLore(lore);
		}
		if(enchant == true) {
			DrMeta.addEnchant(Enchantment.DURABILITY,1, true);
			DrMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		DrItem.setItemMeta(DrMeta);
		menu.setItem(slot, DrItem);
	}

	private void setCustomItem (Inventory menu, int slot, boolean lorex, String perm, Player player, boolean income, String Typr, int Ordre){
		UUID uuid = player.getUniqueId();
		if (!income) {	
			if(player.hasPermission(perm)) {
				if (!tpm.getCooldownAsId(uuid, Typr)) {
						String drname = Objects.requireNonNull(TrCore.instance.getConfig().getString("menu.dr_name.unlocked")).replace("%TYPE%", Objects.requireNonNull(TrCore.instance.getConfig().getString("menu.dr_name.name" + Ordre)));
						String drtype = TrCore.instance.getConfig().getString("rewards_text.text_"+Ordre);
						String drtime = TrCore.instance.getConfig().getString("rewards_time.time_"+Ordre);
						List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.unlocked");
						ItemStack DrItem = new ItemStack(TrCore.drMaterial_open,1);
						ItemMeta DrMeta = DrItem.getItemMeta();
						DrMeta.setDisplayName(TrCore.convert(drname));
						// DrMeta.displayName().append(set().convert(drname));
						if (lorex) {
							List<String> drlore_result = new ArrayList<>();
							List<String> drlore_result2 = new ArrayList<>();
							for (String dr : drlore) {
								drlore_result.add(dr.replaceAll("%REWARD_TEXT%", Objects.requireNonNull(drtype)));
							}
							for (String dr : drlore_result) {
								drlore_result2.add(dr.replaceAll("%REWARD_TIME%", Objects.requireNonNull(drtime)));
							}
							DrMeta.setLore(TrCore.convertL(drlore_result2));
						}
						DrMeta.addEnchant(Enchantment.DURABILITY,1, true);
						DrMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						DrItem.setItemMeta(DrMeta);
						menu.setItem(slot, DrItem);
					
					
				} else if (tpm.getCooldownAsId(uuid, Typr)){
					if (tpm.getDiffTimesMillis(uuid, Typr)){
						String drname = Objects.requireNonNull(TrCore.instance.getConfig().getString("menu.dr_name.unlocked")).replace("%TYPE%", Objects.requireNonNull(TrCore.instance.getConfig().getString("menu.dr_name.name" + Ordre)));
						String drtype = TrCore.instance.getConfig().getString("rewards_text.text_"+Ordre);
						String drtime = TrCore.instance.getConfig().getString("rewards_time.time_"+Ordre);
						List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.unlocked");
						ItemStack DrItem = new ItemStack(TrCore.drMaterial_open,1);
						ItemMeta DrMeta = DrItem.getItemMeta();
						DrMeta.setDisplayName(TrCore.convert(drname));
						if (lorex) {
							List<String> drlore_result = new ArrayList<>();
							List<String> drlore_result2 = new ArrayList<>();
							for (String dr : drlore) {
								drlore_result.add(dr.replaceAll("%REWARD_TEXT%", Objects.requireNonNull(drtype)));
							}
							for (String dr : drlore_result) {
								drlore_result2.add(dr.replaceAll("%REWARD_TIME%", Objects.requireNonNull(drtime)));
							}
							DrMeta.setLore(TrCore.convertL(drlore_result2));
						}
						DrMeta.addEnchant(Enchantment.DURABILITY,1, true);
						DrMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						DrItem.setItemMeta(DrMeta);
						menu.setItem(slot, DrItem);
					} else {					
						String drname = TrCore.instance.getConfig().getString("menu.dr_name.locked");
						List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.locked");
						ItemStack DrItem = new ItemStack(TrCore.drMaterial_close,1);
						ItemMeta DrMeta = DrItem.getItemMeta();
						DrMeta.setDisplayName(TrCore.convert(Objects.requireNonNull(drname)));
						if (lorex) {
							List<String> drlore_result = new ArrayList<>();
							for (String dr : drlore) {
								drlore_result.add(dr.replaceAll("%TIME%", tpm.getDiffTimesMillisToDate(uuid, Typr).toString()));
							}	
							DrMeta.setLore(TrCore.convertL(drlore_result));
						}
						DrItem.setItemMeta(DrMeta);
						menu.setItem(slot, DrItem);
					}
				}
			} else {
				String drname = TrCore.instance.getConfig().getString("menu.dr_name.noperm");
				List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.noperm");
				ItemStack DrItem = new ItemStack(TrCore.drMaterial_noperm,1);
				ItemMeta DrMeta = DrItem.getItemMeta();
				DrMeta.setDisplayName(TrCore.convert(Objects.requireNonNull(drname)));
				DrMeta.setLore(TrCore.convertL(drlore));
				DrItem.setItemMeta(DrMeta);
				menu.setItem(slot, DrItem);
			}
		} else {
			String drname = TrCore.instance.getConfig().getString("menu.dr_name.incoming");
			List<String> drlore = TrCore.instance.getConfig().getStringList("menu.dr_lore.incoming");
			ItemStack DrItem = new ItemStack(TrCore.drMaterial_incoming,1);
			ItemMeta DrMeta = DrItem.getItemMeta();
			DrMeta.setDisplayName(TrCore.convert(Objects.requireNonNull(drname)));
			DrMeta.setLore(TrCore.convertL(drlore));
			DrItem.setItemMeta(DrMeta);
			menu.setItem(slot, DrItem);
		}
	}
}