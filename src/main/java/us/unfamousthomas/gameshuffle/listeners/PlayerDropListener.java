package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onItemDropEvent(PlayerDropItemEvent e) {
        if(e.getPlayer().isOp()) return;

        e.setCancelled(true);
    }
}
