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

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CommandMain implements TabExecutor {

    private final NoEndCrystals noEndCrystals;
    private final BukkitAudiences audiences;
    private final MiniMessage miniMessage;

    public CommandMain(NoEndCrystals noEndCrystals) {
        this.noEndCrystals = noEndCrystals;
        this.audiences = noEndCrystals.getAdventure();
        this.miniMessage = noEndCrystals.miniMessage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0 || sender instanceof ConsoleCommandSender) {
            audiences.sender(sender).sendMessage(Component.text("NoEndCrystals version " + noEndCrystals.getDescription().getVersion() + ". Created by hyperdefined.", NamedTextColor.GREEN));
            return true;
        }
        switch (args[0]) {
            case "reload": {
                noEndCrystals.loadConfig(noEndCrystals.configFile);
                audiences.sender(sender).sendMessage(Component.text("Configuration reloaded!", NamedTextColor.GREEN));
                break;
            }
            case "worlds": {
                List<String> worlds = noEndCrystals.config.getStringList("worlds");
                if (worlds.isEmpty()) {
                    audiences.sender(sender).sendMessage(Component.text("The worlds list is currently empty.", NamedTextColor.RED));
                    break;
                }
                boolean mode = noEndCrystals.config.getBoolean("whitelist");
                audiences.sender(sender).sendMessage(Component.text("-----------------NoEndCrystals-----------------", NamedTextColor.GOLD));
                // whitelist is on if mode = true
                if (mode) {
                    audiences.sender(sender).sendMessage(Component.text("End Crystals will only work in these specified world(s):", NamedTextColor.YELLOW));
                } else {
                    audiences.sender(sender).sendMessage(Component.text("End Crystals cannot be placed in these world(s):", NamedTextColor.YELLOW));
                }
                audiences.sender(sender).sendMessage(Component.text(String.join(", ", worlds), NamedTextColor.YELLOW));
                audiences.sender(sender).sendMessage(Component.text("--------------------------------------------", NamedTextColor.GOLD));
                break;
            }
            case "help": {
                // I don't feel like doing these properly
                audiences.sender(sender).sendMessage(Component.text("-----------------NoEndCrystals-----------------", NamedTextColor.GOLD));
                audiences.sender(sender).sendMessage(miniMessage.deserialize("<gold>/noendcrystals help</gold> <yellow>- Shows this menu.</yellow>"));
                audiences.sender(sender).sendMessage(miniMessage.deserialize("<gold>/noendcrystals worlds</gold> <yellow>- Shows which worlds are enabled/disabled.</yellow>"));
                audiences.sender(sender).sendMessage(miniMessage.deserialize("<gold>/noendcrystals add</gold> <yellow>- Adds a world to the list.</yellow>"));
                audiences.sender(sender).sendMessage(miniMessage.deserialize("<gold>/noendcrystals remove</gold> <yellow>- Removes a world from the list.</yellow>"));
                audiences.sender(sender).sendMessage(miniMessage.deserialize("<gold>/noendcrystals mode</gold> <yellow>- Change which mode to use.</yellow>"));
                audiences.sender(sender).sendMessage(miniMessage.deserialize("<gold>/noendcrystals reload</gold> <yellow>- Reloads the configuration.</yellow>"));
                audiences.sender(sender).sendMessage(Component.text("--------------------------------------------", NamedTextColor.GOLD));
                break;
            }
            case "add": {
                if (args.length == 2) {
                    String world = args[1].toLowerCase(Locale.ROOT);
                    List<String> worlds = noEndCrystals.config.getStringList("worlds");
                    if (worlds.contains(world.toLowerCase(Locale.ROOT))) {
                        audiences.sender(sender).sendMessage(Component.text("This world is already on the list.", NamedTextColor.RED));
                        break;
                    }
                    worlds.add(world);
                    noEndCrystals.config.set("worlds", worlds);
                    try {
                        noEndCrystals.config.save(noEndCrystals.configFile);
                    } catch (IOException e) {
                        noEndCrystals.logger.severe("Unable to save config!");
                        e.printStackTrace();
                    }
                    audiences.sender(sender).sendMessage(Component.text(world + " has been added.", NamedTextColor.GREEN));
                } else {
                    audiences.sender(sender).sendMessage(Component.text("You must specify which world.", NamedTextColor.RED));
                }
                break;
            }
            case "remove": {
                if (args.length == 2) {
                    String world = args[1].toLowerCase(Locale.ROOT);
                    List<String> worlds = noEndCrystals.config.getStringList("worlds");
                    if (!worlds.contains(world.toLowerCase(Locale.ROOT))) {
                        audiences.sender(sender).sendMessage(Component.text("This world is not on the list.", NamedTextColor.RED));
                        break;
                    }
                    worlds.remove(world);
                    noEndCrystals.config.set("worlds", worlds);
                    try {
                        noEndCrystals.config.save(noEndCrystals.configFile);
                    } catch (IOException e) {
                        noEndCrystals.logger.severe("Unable to save config!");
                        e.printStackTrace();
                    }
                    audiences.sender(sender).sendMessage(Component.text(world + " has been removed.", NamedTextColor.GREEN));
                } else {
                    audiences.sender(sender).sendMessage(Component.text("You must specify which world.", NamedTextColor.RED));
                }
                break;
            }
            case "mode": {
                if (args.length == 2) {
                    switch (args[1]) {
                        case "whitelist": {
                            noEndCrystals.config.set("whitelist", true);
                            try {
                                noEndCrystals.config.save(noEndCrystals.configFile);
                            } catch (IOException e) {
                                noEndCrystals.logger.severe("Unable to save config!");
                                e.printStackTrace();
                            }
                            audiences.sender(sender).sendMessage(Component.text("Mode has been changed to whitelist.", NamedTextColor.GREEN));
                            break;
                        }
                        case "blacklist": {
                            noEndCrystals.config.set("whitelist", false);
                            try {
                                noEndCrystals.config.save(noEndCrystals.configFile);
                            } catch (IOException e) {
                                noEndCrystals.logger.severe("Unable to save config!");
                                e.printStackTrace();
                            }
                            audiences.sender(sender).sendMessage(Component.text("Mode has been changed to blacklist.", NamedTextColor.GREEN));
                            break;
                        }
                        default: {
                            audiences.sender(sender).sendMessage(Component.text("Invalid mode.", NamedTextColor.RED));
                        }
                    }
                    break;
                }
                String mode;
                if (noEndCrystals.config.getBoolean("whitelist")) {
                    mode = "whitelist";
                } else {
                    mode = "blacklist";
                }
                audiences.sender(sender).sendMessage(Component.text("-----------------NoEndCrystals-----------------", NamedTextColor.GOLD));
                audiences.sender(sender).sendMessage(Component.text("Mode is currently set to " + mode + ".", NamedTextColor.YELLOW));
                if (mode.equals("whitelist")) {
                    audiences.sender(sender).sendMessage(Component.text("End Crystals will only work in the specified worlds (see /noendcrystals /worlds).", NamedTextColor.YELLOW));
                } else {
                    audiences.sender(sender).sendMessage(Component.text("End Crystals will work in all worlds besides the one(s) on the list (see /noendcrystals /worlds).", NamedTextColor.YELLOW));
                }
                audiences.sender(sender).sendMessage(Component.text("If you want to change the mode, simply do /noendcrystals mode <whitelist/blacklist>", NamedTextColor.YELLOW));
                audiences.sender(sender).sendMessage(Component.text("--------------------------------------------", NamedTextColor.GOLD));
                break;
            }
            default: {
                audiences.sender(sender).sendMessage(Component.text("Unknown option. Please see /noendcrystals help for all valid options.", NamedTextColor.RED));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length >= 1) {
            String arg = args[0];
            if (arg.equalsIgnoreCase("remove")) {
                return noEndCrystals.config.getStringList("worlds");
            }
            if (arg.equalsIgnoreCase("mode")) {
                return Arrays.asList("whitelist", "blacklist");
            }
            if (arg.equalsIgnoreCase("add") || arg.equalsIgnoreCase("help") || arg.equalsIgnoreCase("reload") || arg.equalsIgnoreCase("worlds")) {
                return null;
            }
        }
        return Arrays.asList("reload", "worlds", "add", "remove", "help", "mode");
    }
}
