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

package lol.hyper.noendcrystals.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import lol.hyper.noendcrystals.NoEndCrystals;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.util.*;

public class CommandNoEndCrystals implements BasicCommand {

    private final NoEndCrystals noEndCrystals;

    public CommandNoEndCrystals(NoEndCrystals noEndCrystals) {
        this.noEndCrystals = noEndCrystals;
    }

    @Override
    public void execute(@NonNull CommandSourceStack source, String @NonNull [] args) {
        CommandSender sender = source.getSender();
        if (args.length == 0 || sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Component.text("NoEndCrystals version " + noEndCrystals.getPluginMeta().getVersion() + ". Created by hyperdefined.", NamedTextColor.GREEN));
            return;
        }
        switch (args[0]) {
            case "reload": {
                if (!sender.hasPermission("noendcrystals.reload")) {
                    sender.sendMessage(Component.text("You do not have permission for this command.", NamedTextColor.RED));
                    return;
                }
                noEndCrystals.loadConfig(noEndCrystals.configFile);
                sender.sendMessage(Component.text("Configuration reloaded!", NamedTextColor.GREEN));
                break;
            }
            case "worlds": {
                if (!sender.hasPermission("noendcrystals.worlds")) {
                    sender.sendMessage(Component.text("You do not have permission for this command.", NamedTextColor.RED));
                    return;
                }
                List<String> worlds = noEndCrystals.config.getStringList("worlds");
                if (worlds.isEmpty()) {
                    sender.sendMessage(Component.text("The worlds list is currently empty.", NamedTextColor.RED));
                    break;
                }
                boolean mode = noEndCrystals.config.getBoolean("whitelist");
                sender.sendMessage(Component.text("-----------------NoEndCrystals-----------------", NamedTextColor.GOLD));
                // whitelist is on if mode = true
                if (mode) {
                    sender.sendMessage(Component.text("End Crystals will only work in these specified world(s):", NamedTextColor.YELLOW));
                } else {
                    sender.sendMessage(Component.text("End Crystals cannot be placed in these world(s):", NamedTextColor.YELLOW));
                }
                sender.sendMessage(Component.text(String.join(", ", worlds), NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("--------------------------------------------", NamedTextColor.GOLD));
                break;
            }
            case "help": {
                // I don't feel like doing these properly
                sender.sendMessage(Component.text("-----------------NoEndCrystals-----------------", NamedTextColor.GOLD));
                sender.sendMessage(noEndCrystals.textUtils.format("<gold>/noendcrystals help</gold> <yellow>- Shows this menu.</yellow>"));
                sender.sendMessage(noEndCrystals.textUtils.format("<gold>/noendcrystals worlds</gold> <yellow>- Shows which worlds are enabled/disabled.</yellow>"));
                sender.sendMessage(noEndCrystals.textUtils.format("<gold>/noendcrystals add</gold> <yellow>- Adds a world to the list.</yellow>"));
                sender.sendMessage(noEndCrystals.textUtils.format("<gold>/noendcrystals remove</gold> <yellow>- Removes a world from the list.</yellow>"));
                sender.sendMessage(noEndCrystals.textUtils.format("<gold>/noendcrystals mode</gold> <yellow>- Change which mode to use.</yellow>"));
                sender.sendMessage(noEndCrystals.textUtils.format("<gold>/noendcrystals reload</gold> <yellow>- Reloads the configuration.</yellow>"));
                sender.sendMessage(Component.text("--------------------------------------------", NamedTextColor.GOLD));
                break;
            }
            case "add": {
                if (!sender.hasPermission("noendcrystals.add")) {
                    sender.sendMessage(Component.text("You do not have permission for this command.", NamedTextColor.RED));
                    return;
                }
                if (args.length == 2) {
                    String world = args[1].toLowerCase(Locale.ROOT);
                    List<String> worlds = noEndCrystals.config.getStringList("worlds");
                    if (worlds.contains(world.toLowerCase(Locale.ROOT))) {
                        sender.sendMessage(Component.text("This world is already on the list.", NamedTextColor.RED));
                        break;
                    }
                    worlds.add(world);
                    noEndCrystals.config.set("worlds", worlds);
                    try {
                        noEndCrystals.config.save(noEndCrystals.configFile);
                    } catch (IOException exception) {
                        noEndCrystals.logger.error("Unable to save config!", exception);
                    }
                    sender.sendMessage(Component.text(world + " has been added.", NamedTextColor.GREEN));
                } else {
                    sender.sendMessage(Component.text("You must specify which world.", NamedTextColor.RED));
                }
                break;
            }
            case "remove": {
                if (!sender.hasPermission("noendcrystals.remove")) {
                    sender.sendMessage(Component.text("You do not have permission for this command.", NamedTextColor.RED));
                    return;
                }
                if (args.length == 2) {
                    String world = args[1].toLowerCase(Locale.ROOT);
                    List<String> worlds = noEndCrystals.config.getStringList("worlds");
                    if (!worlds.contains(world.toLowerCase(Locale.ROOT))) {
                        sender.sendMessage(Component.text("This world is not on the list.", NamedTextColor.RED));
                        break;
                    }
                    worlds.remove(world);
                    noEndCrystals.config.set("worlds", worlds);
                    try {
                        noEndCrystals.config.save(noEndCrystals.configFile);
                    } catch (IOException exception) {
                        noEndCrystals.logger.error("Unable to save config!", exception);
                    }
                    sender.sendMessage(Component.text(world + " has been removed.", NamedTextColor.GREEN));
                } else {
                    sender.sendMessage(Component.text("You must specify which world.", NamedTextColor.RED));
                }
                break;
            }
            case "mode": {
                if (!sender.hasPermission("noendcrystals.mode")) {
                    sender.sendMessage(Component.text("You do not have permission for this command.", NamedTextColor.RED));
                    return;
                }
                if (args.length == 2) {
                    switch (args[1]) {
                        case "whitelist": {
                            noEndCrystals.config.set("whitelist", true);
                            try {
                                noEndCrystals.config.save(noEndCrystals.configFile);
                            } catch (IOException exception) {
                                noEndCrystals.logger.error("Unable to save config!", exception);
                            }
                            sender.sendMessage(Component.text("Mode has been changed to whitelist.", NamedTextColor.GREEN));
                            break;
                        }
                        case "blacklist": {
                            noEndCrystals.config.set("whitelist", false);
                            try {
                                noEndCrystals.config.save(noEndCrystals.configFile);
                            } catch (IOException exception) {
                                noEndCrystals.logger.error("Unable to save config!", exception);
                            }
                            sender.sendMessage(Component.text("Mode has been changed to blacklist.", NamedTextColor.GREEN));
                            break;
                        }
                        default: {
                            sender.sendMessage(Component.text("Invalid mode.", NamedTextColor.RED));
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
                sender.sendMessage(Component.text("-----------------NoEndCrystals-----------------", NamedTextColor.GOLD));
                sender.sendMessage(Component.text("Mode is currently set to " + mode + ".", NamedTextColor.YELLOW));
                if (mode.equals("whitelist")) {
                    sender.sendMessage(Component.text("End Crystals will only work in the specified worlds (see /noendcrystals /worlds).", NamedTextColor.YELLOW));
                } else {
                    sender.sendMessage(Component.text("End Crystals will work in all worlds besides the one(s) on the list (see /noendcrystals /worlds).", NamedTextColor.YELLOW));
                }
                sender.sendMessage(Component.text("If you want to change the mode, simply do /noendcrystals mode <whitelist/blacklist>", NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("--------------------------------------------", NamedTextColor.GOLD));
                break;
            }
            default: {
                sender.sendMessage(Component.text("Unknown option. Please see /noendcrystals help for all valid options.", NamedTextColor.RED));
            }
        }
    }

    @Override
    public String permission() {
        return "noendcrystals.command";
    }

    @Override
    public @NonNull Collection<String> suggest(@NonNull CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();
        // suggest basic sub commands
        if (args.length == 0) {
            List<String> suggestions = new ArrayList<>();
            if (sender.hasPermission("noendcrystals.reload")) {
                suggestions.add("reload");
            }
            if (sender.hasPermission("noendcrystals.worlds")) {
                suggestions.add("worlds");
            }
            if (sender.hasPermission("noendcrystals.add")) {
                suggestions.add("add");
            }
            if (sender.hasPermission("noendcrystals.remove")) {
                suggestions.add("remove");
            }
            if (sender.hasPermission("noendcrystals.mode")) {
                suggestions.add("mode");
            }
            suggestions.add("help");
            return suggestions;
        }

        // suggest whitelist/blacklist for mode
        if (args.length == 2 && args[0].equalsIgnoreCase("mode") && sender.hasPermission("noendcrystals.mode")) {
            return Arrays.asList("whitelist", "blacklist");
        }

        // suggest whitelist/blacklist for mode
        if (args.length == 2 && args[0].equalsIgnoreCase("add") && sender.hasPermission("noendcrystals.add")) {
            return Bukkit.getWorlds().stream().map(World::getName).toList();
        }

        // suggest whitelist/blacklist for mode
        if (args.length == 2 && args[0].equalsIgnoreCase("remove") && sender.hasPermission("noendcrystals.remove")) {
            return noEndCrystals.config.getStringList("worlds");
        }
        return Collections.emptyList();
    }
}
