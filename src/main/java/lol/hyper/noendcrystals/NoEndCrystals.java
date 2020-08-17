/**
 * NoEndCrystals.java
 * Created on 7/13/2019
 * - hyperdefined
 */

package lol.hyper.noendcrystals;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoEndCrystals extends JavaPlugin implements Listener {

    private static NoEndCrystals instance;
    public FileConfiguration config;
    public File configFile = new File(this.getDataFolder(), "config.yml");

    public static NoEndCrystals getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        if (!configFile.exists()) {
            this.saveResource("config.yml", true);
        }
        loadConfig(configFile);
        Bukkit.getServer().getPluginManager().registerEvents(new EndCrystalChecker(), this);
        this.getCommand("noendcrystals").setExecutor(new CommandReload());

        Bukkit.getLogger().info("[NoEndCrystals] Plugin created by hyperdefined.");

        MetricsLite metricsLite = new MetricsLite(this, 7230);
    }

    @Override
    public void onDisable() {

    }

    public void loadConfig(File file) {
        config = YamlConfiguration.loadConfiguration(file);
    }
}