package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.game.RoomType;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;
import us.unfamousthomas.gameshuffle.utils.Constant;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();


        if(e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            return;
        }

        e.setCancelled(true);
        if (e.getDamage() == 3) {
            if (GameShuffle.getInstance().getGame() != null) {
                Room room = GameShuffle.getInstance().getGame().getCurrentRoom(player);
                if (room != null) {
                    if (room.getType() == RoomType.ELYTRA) {
                        player.teleport(room.getSpawnLocation());
                    }
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if (GameShuffle.getInstance().getGame() != null) {
                    Room room = GameShuffle.getInstance().getGame().getCurrentRoom(player);
                    if (room != null) {
                        player.teleport(room.getSpawnLocation());
                    }
                } else {
                    player.teleport(Constant.SPAWN_LOC);
                }
            }
        }
    }
}
