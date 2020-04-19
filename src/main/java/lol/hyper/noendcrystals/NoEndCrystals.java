/**
 * NoEndCrystals.java
 * Created on 7/13/2019
 * - hyperdefined
 */

package lol.hyper.noendcrystals;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NoEndCrystals extends JavaPlugin implements Listener {

    private static NoEndCrystals instance;
    FileConfiguration config = this.getConfig();
    File configFile = new File("plugins/NoEndCrystals/config.yml");
    List<String> defaultWorlds = new ArrayList<>();

    public static NoEndCrystals getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        MetricsLite metricsLite = new MetricsLite(this, 7230);
        Bukkit.getServer().getPluginManager().registerEvents(new EndCrystalChecker(), this);
        this.getCommand("noendcrystals").setExecutor(new CommandReload());
        Bukkit.getLogger().info("[NoEndCrystals] Plugin created by hyperdefined.");
        defaultWorlds.add("world1");
        defaultWorlds.add("world2");
        defaultWorlds.add("world3");
        config.addDefault("disabled_worlds", defaultWorlds.toArray());
        config.addDefault("message", "&cEnd Crystals are disabled in this world.");
        config.options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onDisable() {

    }
}