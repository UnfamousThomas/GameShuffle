package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Message;

public class PlayerDestroyPlaceListener implements Listener {

    @EventHandler
    public void onBlockDestory(BlockBreakEvent e) {
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
        GameShuffle.getInstance().getAccountManager().getAccount(e.getPlayer().getUniqueId()).sendMessage(Message.CANT_BREAK_PLACE);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getPlayer().isOp()) return;
        e.setCancelled(true);
        GameShuffle.getInstance().getAccountManager().getAccount(e.getPlayer().getUniqueId()).sendMessage(Message.CANT_BREAK_PLACE);
    }
}
