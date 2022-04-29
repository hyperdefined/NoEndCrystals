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

package lol.hyper.noendcrystals.tools;

import lol.hyper.noendcrystals.NoEndCrystals;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EndCrystalChecker implements Listener {

    private final NoEndCrystals noEndCrystals;

    public EndCrystalChecker(NoEndCrystals noEndCrystals) {
        this.noEndCrystals = noEndCrystals;
    }

    private boolean allow(World world) {
        boolean allow = noEndCrystals.config.getStringList("worlds").contains(world.getName());
        return noEndCrystals.config.getBoolean("whitelist", true) == allow;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Action.RIGHT_CLICK_BLOCK == event.getAction()) {
            if (event.getMaterial() != Material.END_CRYSTAL) {
                return;
            }
            if (event.getClickedBlock() == null) return;
            if (event.getClickedBlock().getType() == Material.OBSIDIAN || event.getClickedBlock().getType() == Material.BEDROCK) {
                if (!allow(player.getWorld())) {
                    event.setCancelled(true);
                    String message = noEndCrystals.config.getString("message");
                    if (message == null || message.isEmpty()) {
                        return;
                    }
                    noEndCrystals.getAdventure().player(player).sendMessage(noEndCrystals.miniMessage.deserialize(message));
                }
            }
        }
    }
}