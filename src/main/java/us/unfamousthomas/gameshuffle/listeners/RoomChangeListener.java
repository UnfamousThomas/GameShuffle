package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.events.RoomChangeEvent;
import us.unfamousthomas.gameshuffle.utils.ItemStackBuilder;

public class RoomChangeListener implements Listener {

    @EventHandler
    public void onRoomChange(RoomChangeEvent e) {
        ItemStack elytra = new ItemStackBuilder(Material.ELYTRA).withName(ChatColor.translateAlternateColorCodes('&', "&cTiivad")).makeUnbreakable().withItemFlags(ItemFlag.HIDE_UNBREAKABLE).buildStack();
        ItemStack raket = new ItemStackBuilder(Material.FIREWORK_ROCKET).withName(ChatColor.translateAlternateColorCodes('&', "&cRakett")).withAmount(20).buildStack();

        e.getPlayer().getInventory().remove(elytra);
        e.getPlayer().getInventory().remove(raket);


        e.getPlayer().getInventory().remove(Material.FIREWORK_ROCKET);
        switch (e.getNewRoom().getType()) {
            case ELYTRA -> {
                e.getPlayer().getInventory().setChestplate(elytra);
                e.getPlayer().getInventory().addItem(raket);
            }
            case PARKOUR_TIMED -> GameShuffle.getInstance().getAccountManager().getAccount(e.getPlayer().getUniqueId(), data -> GameShuffle.getInstance().getGame().triggerRunnable(e.getPlayer(), e.getNewRoom(), data));
        }
        e.getPlayer().updateInventory();
    }
}
