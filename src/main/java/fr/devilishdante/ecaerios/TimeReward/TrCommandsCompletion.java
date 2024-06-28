package fr.devilishdante.ecaerios.TimeReward;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

public class TrCommandsCompletion implements TabCompleter{
    @Override
    public List<String> onTabComplete (@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("TimeReward") && args.length >= 0){

                List<String> completion = new ArrayList<>();
                if(args.length == 1){
                    completion.add(args[0]="info");
                    completion.add(args[0]="help");
                }

                if(sender.hasPermission(TrCore.permAdmin)) {
                    if(args.length == 1){
                        completion.add(args[0]="reload");
                        completion.add(args[0]="debug");
                    }

                    if(args.length == 2) {
                        if(args[0].contains("debug")){
                            completion.add(args[1]= Objects.requireNonNull(Bukkit.getPlayer(args[1])).getName());
                        }
                    }

                    if(args.length == 3) {
                        if(args[0].contains("debug")){
                            completion.add(args[2]="journalier");
                            completion.add(args[2]="hebdomadaire");
                        }
                    }
                }
                return completion;
        }
        return null;
    }
}