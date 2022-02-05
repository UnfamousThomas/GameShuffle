package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.utils.ItemStackBuilder;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onItemDropEvent(PlayerInteractEvent e) {
        if(GameShuffle.getInstance().getGame() == null) return;
        if (e.getAction() != null && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            ItemStack is = e.getItem();
            if (is != null && !is.getType().equals(Material.AIR)) {
                if(is.getType() == Material.WHITE_WOOL) {
                    if(is.getItemMeta().getDisplayName().equalsIgnoreCase("Jäta Vahele")) {

                        GameShuffle.getInstance().getGame().skipLevel(e.getPlayer());
                        GameShuffle.getInstance().getAccountManager().getAccount(e.getPlayer().getUniqueId()).sendMessage(Message.SKIPPED_LEVEL);

                        ItemStack vili =new ItemStackBuilder(Material.WHITE_WOOL).withName("Jäta Vahele").buildStack();

                            e.getPlayer().getInventory().remove(vili);

                        e.getPlayer().updateInventory();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                e.getPlayer().getInventory().addItem(vili);
                                e.getPlayer().updateInventory();
                            }
                        }.runTaskLater(GameShuffle.getInstance(), 20L * 7);
                    }
            }
        }
    }

}
}
