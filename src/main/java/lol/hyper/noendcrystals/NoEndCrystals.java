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

import lol.hyper.noendcrystals.tools.EndCrystalChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class NoEndCrystals extends JavaPlugin implements Listener {

    public FileConfiguration config;
    public File configFile = new File(this.getDataFolder(), "config.yml");

    public CommandMain commandReload;
    public EndCrystalChecker endCrystalChecker;

    @Override
    public void onEnable() {
        commandReload = new CommandMain(this);
        endCrystalChecker = new EndCrystalChecker(this);
        if (!configFile.exists()) {
            this.saveResource("config.yml", true);
        }
        loadConfig(configFile);

        Bukkit.getServer().getPluginManager().registerEvents(endCrystalChecker, this);
        this.getCommand("noendcrystals").setExecutor(commandReload);

        Metrics metricsLite = new Metrics(this, 7230);
    }

    public void loadConfig(File file) {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
