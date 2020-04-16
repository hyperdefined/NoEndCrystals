/**
 * NoEndCrystals.java
 * Created on 7/13/2019
 * - hyperdefined
 */

package lol.hyper.noendcrystals;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class NoEndCrystals extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new BlockChecker(), this);
        Bukkit.getLogger().info("[NoEndCrystals] Plugin created by hyperdefined.");
    }

    @Override
    public void onDisable() {

    }
}