/*
 * This file is part of NoEndCrystals.
 *
 * NoEndCrystals is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NoEndCrystals is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NoEndCrystals.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.noendcrystals;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;

public class CommandMain implements TabExecutor {

    private final NoEndCrystals noEndCrystals;

    public CommandMain(NoEndCrystals noEndCrystals) {
        this.noEndCrystals = noEndCrystals;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || sender instanceof ConsoleCommandSender) {
            sender.sendMessage(ChatColor.GREEN + "NoEndCrystals version "
                    + noEndCrystals.getDescription().getVersion() + ". Created by hyperdefined.");
            sender.sendMessage(ChatColor.GREEN + "Use /noendcrystals help for command help.");
            return true;
        }
        switch (args[0]) {
            case "reload": {
                noEndCrystals.loadConfig(noEndCrystals.configFile);
                sender.sendMessage(ChatColor.GREEN + "Configuration reloaded!");
                break;
            }
            case "worlds": {
                List<String> worlds = noEndCrystals.config.getStringList("disabled_worlds");
                sender.sendMessage(ChatColor.GREEN + "End Crystals cannot be placed in these world(s):");
                sender.sendMessage(ChatColor.BLUE + String.join(", ", worlds));
                break;
            }
            case "help": {
                sender.sendMessage(ChatColor.GOLD + "-----------------NoEndCrystals-----------------");
                sender.sendMessage(ChatColor.GOLD + "/noendcrystals help " + ChatColor.YELLOW + "- Shows this menu.");
                sender.sendMessage(ChatColor.GOLD + "/noendcrystals worlds " + ChatColor.YELLOW
                        + "- Shows which worlds are enabled/disabled.");
                sender.sendMessage(
                        ChatColor.GOLD + "/noendcrystals add " + ChatColor.YELLOW + "- Adds a world to the list.");
                sender.sendMessage(ChatColor.GOLD + "/noendcrystals remove " + ChatColor.YELLOW
                        + "- Removes a world from the list.");
                sender.sendMessage(
                        ChatColor.GOLD + "/noendcrystals mode " + ChatColor.YELLOW + "- Change which mode to use.");
                sender.sendMessage(
                        ChatColor.GOLD + "/noendcrystals reload " + ChatColor.YELLOW + "- Reloads the configuration.");
                sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
                break;
            }
            case "add": {
                if (args.length == 2) {
                    String world = args[1];
                    List<String> worlds = (List<String>) noEndCrystals.config.getList("worlds");
                    worlds.add(world);
                    noEndCrystals.config.set("worlds", worlds);
                    noEndCrystals.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + world + " has been added.");
                } else {
                    sender.sendMessage(ChatColor.RED + "You must specify which world to add.");
                }
                break;
            }
            case "remove": {
                if (args.length == 2) {
                    String world = args[1];
                    List<String> worlds = (List<String>) noEndCrystals.config.getList("worlds");
                    if (!worlds.contains(world)) {
                        sender.sendMessage(ChatColor.RED + "That world is not on the list.");
                        break;
                    }
                    worlds.remove(world);
                    noEndCrystals.config.set("worlds", worlds);
                    noEndCrystals.saveConfig();
                    sender.sendMessage(ChatColor.GREEN + world + " has been removed.");
                } else {
                    sender.sendMessage(ChatColor.RED + "You must specify which world to remove.");
                }
                break;
            }
            case "mode": {
                if (args.length == 2) {
                    switch (args[1]) {
                        case "whitelist": {
                            noEndCrystals.config.set("whitelist", true);
                            noEndCrystals.saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "Mode has been changed to whitelist.");
                            break;
                        }
                        case "blacklist": {
                            noEndCrystals.config.set("whitelist", false);
                            noEndCrystals.saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "Mode has been changed to blacklist.");
                            break;
                        }
                        default: {
                            sender.sendMessage(ChatColor.RED + "Invalid mode.");
                        }
                    }
                }
                String mode;
                if (noEndCrystals.config.getBoolean("whitelist")) {
                    mode = "whitelist";
                } else {
                    mode = "blacklist";
                }
                sender.sendMessage(ChatColor.GOLD + "-----------------NoEndCrystals-----------------");
                sender.sendMessage(ChatColor.GOLD + "Mode is currently set to " + mode + ".");
                if (mode.equals("whitelist")) {
                    sender.sendMessage(ChatColor.GOLD + "End Crystals will only work in the specified worlds.");
                } else {
                    sender.sendMessage(
                            ChatColor.GOLD + "End Crystals will work in all worlds besides the one(s) on the list.");
                }
                sender.sendMessage(ChatColor.GOLD
                        + "If you want to change the mode, simply do /noendcrystals mode <whitelist/blacklist>");
                sender.sendMessage(ChatColor.GOLD + "--------------------------------------------");
                break;
            }
            default: {
                sender.sendMessage(
                        ChatColor.RED + "Unknown option. Please see /noendcrystals help for all valid options.");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Arrays.asList("reload", "worlds", "add", "remove", "help");
    }
}
