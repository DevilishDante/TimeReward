package fr.devilishdante.Ecaerios.TimeReward;

import java.util.*;
// import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class TrPlayerManager {
    public static final String PlayerManager = null;
    private final Map<UUID, Calendar> Hebdomadaire;
    private final Map<UUID, Calendar> Journalier;
    private final String header = "Users.";

    private long seconds;
    private long minutes;
    private long hours; 
    private long days; 

    public TrPlayerManager(){
        Hebdomadaire = new HashMap<>();
        Journalier = new HashMap<>();
    }

    public Calendar DateSetup() {
        return Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
    }

    private String nameByUUID(UUID id) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(id);
        if(player == null) return null;
        return player.getName();
    }

    public void setCooldown(UUID player, String Type, Boolean conf, Calendar cal){
        String Pseudo = nameByUUID(player);
        TrCore.UsersConfig.set(header+player.toString()+".Pseudo", Objects.requireNonNull(Pseudo).toString());
        if (Type.equalsIgnoreCase("Journalier")) {
            Journalier.put(player, cal);
            if (conf) {
                TrCore.UsersConfig.set(header+player.toString()+".Journalier.date", cal.getTimeInMillis());
                TrCore.instance.saveUsersConfig();
            }
        } else if (Type.equalsIgnoreCase("Hebdomadaire")) {
            Hebdomadaire.put(player, cal);
            if (conf) {
                TrCore.UsersConfig.set(header+player.toString()+".Hebdomadaire.date", cal.getTimeInMillis());
                TrCore.instance.saveUsersConfig();
            }
        }
    }

    public void DelCooldown(UUID player, String Type, Boolean conf){
        String Pseudo = nameByUUID(player);
        TrCore.UsersConfig.set(header+player.toString()+".Pseudo", Objects.requireNonNull(Pseudo).toString());
        if (Type.equalsIgnoreCase("journalier")) {
            Journalier.remove(player);
            if (conf) {
                TrCore.UsersConfig.set(header+player.toString()+".Journalier.date", null);
                TrCore.instance.saveUsersConfig();
            }
        } else if (Type.equalsIgnoreCase("hebdomadaire")) {
            Hebdomadaire.remove(player);
            if (conf) {
                TrCore.UsersConfig.set(header+player.toString()+".Hebdomadaire.date", null);
                TrCore.instance.saveUsersConfig();
            }
        }
    }

    public Calendar getCooldownConfigFile(UUID player, String Type){
        if (Type.equalsIgnoreCase("Journalier")){
            Long time = TrCore.UsersConfig.getLong(header+player+".Journalier.date");
            Calendar Date = DateSetup();
            Date.setTimeInMillis(time);
            return Date;
        } else if (Type.equalsIgnoreCase("Hebdomadaire")) {
            Long time = TrCore.UsersConfig.getLong(header+player+".Hebdomadaire.date");
            Calendar Date = DateSetup();
            Date.setTimeInMillis(time);
            return Date;
        } else {
            return null;
        }
    }

    public Calendar getCooldown(UUID player, String Type){
        if (Type.equalsIgnoreCase("Journalier")){
            return Journalier.get(player);
        } else if (Type.equalsIgnoreCase("Hebdomadaire")) {
            return Hebdomadaire.get(player);
        } else {
            return null;
        }
    }

    public boolean getCooldownAsId(UUID player, String Type){
        if (Type.equalsIgnoreCase("Journalier")){         
            return Journalier.containsKey(player);
        } else if (Type.equalsIgnoreCase("Hebdomadaire")) {
            return Hebdomadaire.containsKey(player); 
        } else {
            return false;
        }
    
    }

    public boolean IsEmpty (UUID player, String Type){
        if (Type.equalsIgnoreCase("Journalier")){
            return Journalier.isEmpty();
        } else if (Type.equalsIgnoreCase("Hebdomadaire")) {
            return Hebdomadaire.isEmpty();
        } else {
            return false;
        }
    }

    private Long calcDiffTime(Calendar Reward_millis,UUID player, Long timer){
        return Reward_millis.getTimeInMillis()+timer;
    }

    public Boolean getDiffTimesMillis(UUID player, String Type) {
        if (Type.equalsIgnoreCase("Journalier")) {
            String t1 = TrCore.instance.getConfig().getString("rewards_time.time_1");
            Calendar Reward_millis = Journalier.get(player);
            long timerM = (long) Integer.parseInt(Objects.requireNonNull(t1)) *60*60*1000;
            Long result = calcDiffTime(Reward_millis, player, timerM);
            return result <= DateSetup().getTimeInMillis();
        } else if (Type.equalsIgnoreCase("Hebdomadaire")) {
            String t2 = TrCore.instance.getConfig().getString("rewards_time.time_2");
            Calendar Reward_millis = Hebdomadaire.get(player);    
            long timerM = (long) Integer.parseInt(Objects.requireNonNull(t2)) *60*60*1000;
            Long result = calcDiffTime(Reward_millis, player, timerM);
            return result <= DateSetup().getTimeInMillis();
        } else {
            return false;
        }
    }

    private String calcDiffTimeTxt(Calendar Reward_millis,UUID player, Long timer) {
        if (Reward_millis != null){
            long result_m = (Reward_millis.getTimeInMillis()+timer)-DateSetup().getTimeInMillis();
            return PastTime(result_m);
        } else {
            return null;
        }
    }
    
    private String PastTime (long timeInMillis) {
        long lTime =  timeInMillis/1000;

        days = lTime / (24*60*60);
        hours = (lTime-(days*86400))/3600;
        minutes = (lTime-((days*86400)+(hours*3600)))/60;
        seconds = (lTime-((days*86400)+(hours*3600)+(minutes*60)));

        if (timeInMillis <= 86400000L){
            return (hours+" heure(s) "+minutes+" minute(s) "+seconds+" sec");
        } else {
            return (days+"jour(s) "+hours+"heure(s) "+minutes+" min "+seconds+" sec");
        }
    }

    public String getDiffTimesMillisToDate(UUID player, String Type) {
        if (Type.equalsIgnoreCase("Journalier")) {
            String t1 = TrCore.instance.getConfig().getString("rewards_time.time_1");
            Calendar Reward_millis = Journalier.get(player);
            long timerM = (long) Integer.parseInt(Objects.requireNonNull(t1)) *60*60*1000;
            return calcDiffTimeTxt(Reward_millis, player, timerM);
        } else if (Type.equalsIgnoreCase("Hebdomadaire")) {
            String t2 = TrCore.instance.getConfig().getString("rewards_time.time_2");
            Calendar Reward_millis = Hebdomadaire.get(player);    
            long timerM = (long) Integer.parseInt(Objects.requireNonNull(t2)) *60*60*1000;
            return calcDiffTimeTxt(Reward_millis, player, timerM);
        } else {
            return null;
        }
    }
}