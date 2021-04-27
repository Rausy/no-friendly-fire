package com.raus.noFriendlyFire;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabHelper implements TabCompleter {
    noFriendlyFire plugin;
    public TabHelper(noFriendlyFire plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        ArrayList<String> fill = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("noff"))
            if (args.length == 1){
                fill.add("info");
                fill.add("toggle");
            }
        return fill;
    }
}
