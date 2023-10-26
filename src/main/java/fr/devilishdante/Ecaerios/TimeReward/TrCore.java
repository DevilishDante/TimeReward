package fr.devilishdante.Ecaerios.TimeReward;
import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class TrCore extends JavaPlugin { 
    public TrPlayerManager tpm;
    public static TrCore instance;
    public static String prefix;
    public static String pnjname;
    public static String permAdmin = "timereward.admin";
    public static FileConfiguration msgConfig;
    public static FileConfiguration UsersConfig;
    private File customConfigFile;
    private File usersConfigFile;

    public static Material drMaterial_close;
    public static Material drMaterial_open;
    public static Material drMaterial_incoming;
    public static Material drMaterial_noperm;
    private void loader(String Ver,Boolean status){
        String separator = "───────────────────────────────────────────────────";
        String creator = "Créateur ➜ DevilishDante";
        String activationMessage = "✔ Activation du système du pnj de reward ✔";
        String desactivationMessage = "✖ Désactivation du système du pnj de reward ✖";
        String emptyLine = "";
        String[] graphicLogo = {
                " _____ _              _____                     _ ",
                "|_   _|_|_____ ___   | __  |___ _ _ _ ___ ___ _| |",
                "  | | | |     | -_|  |    -| -_| | | | .'|  _| . |",
                "  |_| |_|_|_|_|___|  |__|__|___|_____|__,|_| |___|"
        };

        Bukkit.getLogger().log(Level.INFO, separator);
        Bukkit.getLogger().log(Level.INFO, emptyLine);
        for (String line : graphicLogo) {
            Bukkit.getLogger().log(Level.INFO, line);
        }
        Bukkit.getLogger().log(Level.INFO, emptyLine);
        if (status) {
            Bukkit.getLogger().log(Level.INFO, creator);
            Bukkit.getLogger().log(Level.INFO, Ver);
            Bukkit.getLogger().log(Level.INFO, activationMessage);
        } else {
            Bukkit.getLogger().log(Level.INFO, desactivationMessage);
        }
        Bukkit.getLogger().log(Level.INFO, emptyLine);
        Bukkit.getLogger().log(Level.INFO, separator);
    }
    @Override
    public void onEnable() {
        instance = this;
        prefix = instance.getConfig().getString("prefix");
        pnjname = instance.getConfig().getString("pnj_name");
        tpm = new TrPlayerManager();
        String version = instance.getConfig().getString("VERSION");
        String versionInfo = "Version ➜ " + version;
        loader(versionInfo,true);
        drMaterial_close = Material.matchMaterial(Objects.requireNonNull(instance.getConfig().getString("Material.lock")));
        drMaterial_open = Material.matchMaterial(Objects.requireNonNull(instance.getConfig().getString("Material.unlock")));
        drMaterial_incoming = Material.matchMaterial(Objects.requireNonNull(instance.getConfig().getString("Material.incoming")));
        drMaterial_noperm= Material.matchMaterial(Objects.requireNonNull(instance.getConfig().getString("Material.noperm")));

        PluginManager pm = this.getServer().getPluginManager();
        saveDefaultConfig();
        createCustomConfig();
        createUsersConfig();
        pm.registerEvents(new TrEventMenu(),this);
        pm.registerEvents(new TrEventPNJ(),this);
        pm.registerEvents(new TrEventPlayerManager(),this);
        Objects.requireNonNull(getCommand("TimeReward")).setExecutor(new TrCommands());
        Objects.requireNonNull(getCommand("TimeReward")).setTabCompleter(new TrCommandsCompletion());
    }
    @Override
    public void onDisable() {
        loader("",false);
    }
    //Partie pour charger messages.yml
    public FileConfiguration getCustomConfig() {
        return msgConfig;
    }
    public void reloadCustomConfig() {
        customConfigFile = new File(getDataFolder(), "messages.yml");
        try {
            msgConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        } catch (Exception event) {
            event.printStackTrace();
        }
    }
    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "messages.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdir();
            saveResource("messages.yml", false);
        }
        msgConfig = new YamlConfiguration();
        try {
            msgConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException event) {
            event.printStackTrace();
        }
    }
    //Partie pour charger users.yml
    public FileConfiguration getUsersConfig() {
        return UsersConfig;
    }
    public void reloadUsersConfig() {
        usersConfigFile = new File(getDataFolder(), "users.yml");
        try {
            UsersConfig = YamlConfiguration.loadConfiguration(usersConfigFile);
        } catch (Exception event) {
            event.printStackTrace();
        }
    }
    public void saveUsersConfig() {
        usersConfigFile = new File(getDataFolder(), "users.yml");
        try {
            UsersConfig.save(usersConfigFile);
        } catch (Exception event) {
            event.printStackTrace();
        }
    }
    private void createUsersConfig() {
        usersConfigFile = new File(getDataFolder(), "users.yml");
        if (!usersConfigFile.exists()) {
            usersConfigFile.getParentFile().mkdir();
            saveResource("users.yml", false);
        }
        UsersConfig = new YamlConfiguration();
        try {
            UsersConfig.load(usersConfigFile);
        } catch (IOException | InvalidConfigurationException event) {
            event.printStackTrace();
        }
    }
    //Partie pour convertir les symboles § en symboles normalisé
    public static String convert(String into){
        String msg = into.replace("&", "§");
        return msg;
    }
    public static List<String> convertL(List<String> into) {
        List<String> finish = new ArrayList<>();
        for (String msg : into){
            finish.add(convert(msg));
        }
        return finish;
    }
}