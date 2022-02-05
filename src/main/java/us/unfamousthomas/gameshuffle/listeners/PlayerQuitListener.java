package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import us.unfamousthomas.gameshuffle.GameShuffle;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage("");
        GameShuffle shuffle = GameShuffle.getInstance();
        shuffle.getAccountManager().saveAccount(player);
        shuffle.getAccountManager().unloadAccount(player);
    }
}
