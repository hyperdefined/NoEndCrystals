/**
 * BlockChecker.java
 * Created on 7/13/2019
 * - hyperdefined
 */

package lol.hyper.noendcrystals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class BlockChecker implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Action.RIGHT_CLICK_BLOCK == event.getAction()) {
            if (event.getClickedBlock().getType().equals(Material.OBSIDIAN) || event.getClickedBlock().getType().equals(Material.BEDROCK)) {
                if (Material.END_CRYSTAL == event.getMaterial()) {
                    if (!player.getWorld().getName().equals("world_the_end")) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "End Crystals are only enabled in The End.");
                    }
                }
            }
        }
    }
}