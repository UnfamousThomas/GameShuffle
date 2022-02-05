package us.unfamousthomas.gameshuffle.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.utils.Constant;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        GameShuffle.getInstance().getAccountManager().loadAccountOrInsertNew(e.getPlayer(), data -> {
            if(!data.getUsername().equals(e.getPlayer().getName())) {
                data.setUsername(e.getPlayer().getName());
            }


        });

        if (UUID.fromString("f7ef0cd2-708f-4ac7-8d4b-c007060d5cc0").equals(e.getPlayer().getUniqueId())) {
            GameShuffle.getInstance().getAccountManager().getAccount(e.getPlayer().getUniqueId()).setRank(Rank.DEV);
        }

        if (UUID.fromString("71773fe5-42d8-4ec1-81f5-c399ea3e16f5").equals(e.getPlayer().getUniqueId())) {
            GameShuffle.getInstance().getAccountManager().getAccount(e.getPlayer().getUniqueId()).setRank(Rank.DEV);
        }

        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 3, false, true));

        if(GameShuffle.getInstance().getGame() != null) {
            if(!GameShuffle.getInstance().getGame().getPlayersList().contains(e.getPlayer().getUniqueId())) {
                GameShuffle.getInstance().getGame().setupPlayer(e.getPlayer());
            }
        } else {
            e.getPlayer().teleport(Constant.SPAWN_LOC);
        }
        if(e.getPlayer().isOp()) return;

        e.getPlayer().setGameMode(GameMode.ADVENTURE);

    }
}
