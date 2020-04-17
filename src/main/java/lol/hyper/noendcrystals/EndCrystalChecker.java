package lol.hyper.noendcrystals;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EndCrystalChecker implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Action.RIGHT_CLICK_BLOCK == event.getAction()) {
            if (event.getClickedBlock().getType().equals(Material.OBSIDIAN) || event.getClickedBlock().getType().equals(Material.BEDROCK)) {
                if (Material.END_CRYSTAL == event.getMaterial()) {
                    if (NoEndCrystals.getInstance().config.getStringList("disabled_worlds").contains(player.getWorld().getName())) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', NoEndCrystals.getInstance().config.get("message").toString()));
                    }
                }
            }
        }
    }
}
