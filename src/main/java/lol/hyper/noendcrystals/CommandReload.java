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
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;

public class CommandReload implements TabExecutor {

    private final NoEndCrystals noEndCrystals;

    public CommandReload(NoEndCrystals noEndCrystals) {
        this.noEndCrystals = noEndCrystals;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(ChatColor.GREEN + "NoEndCrystals version " + noEndCrystals.getDescription().getVersion() + ". Created by hyperdefined.");
        } else if (strings[0].equalsIgnoreCase("reload")) {
            if (commandSender.hasPermission("noendcrystals.reload")) {
                noEndCrystals.loadConfig(noEndCrystals.configFile);
                commandSender.sendMessage(ChatColor.GREEN + "Configuration reloaded!");
            } else {
                commandSender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
            }
        } else if (strings[0].equalsIgnoreCase("worlds")) {
            List < String > worlds = noEndCrystals.config.getStringList("disabled_worlds");
            commandSender.sendMessage(ChatColor.GREEN + "End Crystals cannot be placed in these world(s):");
            commandSender.sendMessage(ChatColor.BLUE + String.join(", ", worlds));
        } else {
            commandSender.sendMessage(ChatColor.RED + "Invalid sub-command.");
        }
        return true;
    }

    @Override
    public List < String > onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Arrays.asList("reload", "worlds");
    }
}