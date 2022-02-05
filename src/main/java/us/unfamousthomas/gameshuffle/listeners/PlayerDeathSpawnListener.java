package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.game.RoomType;
import us.unfamousthomas.gameshuffle.utils.ItemStackBuilder;

public class PlayerDeathSpawnListener implements Listener {

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent e) {
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 3, false, true));
        if (GameShuffle.getInstance().getGame() == null) return;

        if (!GameShuffle.getInstance().getGame().getPlayersList().contains(e.getPlayer())) return;
        ItemStack vili =new ItemStackBuilder(Material.WHITE_WOOL).withName("Jäta Vahele").buildStack();
        e.getPlayer().getInventory().addItem(vili);

        RoomType type = GameShuffle.getInstance().getGame().getCurrentRoom(e.getPlayer()).getType();

        if (type == RoomType.ELYTRA) {

            e.getPlayer().getInventory().remove(Material.ELYTRA);
            e.getPlayer().getInventory().remove(Material.FIREWORK_ROCKET);
            ItemStack elytra = new ItemStackBuilder(Material.ELYTRA).withName(ChatColor.translateAlternateColorCodes('&', "&cTiivad")).makeUnbreakable().withItemFlags(ItemFlag.HIDE_UNBREAKABLE).buildStack();
            ItemStack raket = new ItemStackBuilder(Material.FIREWORK_ROCKET).withName(ChatColor.translateAlternateColorCodes('&', "&cRakett")).withAmount(20).buildStack();
            e.getPlayer().getInventory().setChestplate(elytra);
            e.getPlayer().getInventory().setItemInOffHand(raket);
            e.getPlayer().updateInventory();

        }

        if(e.getPlayer().isOp()) return;
        e.getPlayer().setGameMode(GameMode.ADVENTURE);

    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }
}