package lol.hyper.noendcrystals;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

public class CommandReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(ChatColor.BLUE + "NoEndCystals was created by hyperdefined.");
        } else if (strings[0].equalsIgnoreCase("reload")) {
            if (commandSender.hasPermission("noendcrystals.reload")) {
                NoEndCrystals.getInstance().config = YamlConfiguration.loadConfiguration(NoEndCrystals.getInstance().configFile);
                commandSender.sendMessage(ChatColor.GREEN + "Configuration reloaded!");
            } else {
                commandSender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "Invalid sub-command.");
        }
        return true;
    }
}
