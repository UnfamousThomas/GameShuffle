package us.unfamousthomas.gameshuffle.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.ScoreboardManager;
import us.unfamousthomas.gameshuffle.GameShuffle;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

    public Game() {
        this.timeFinishedAt = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        arena = new Arena();
        checkEnd();
        checkNetherite();

    }
    private Arena arena;

    private BukkitTask blockCheck;

    private List<UUID> playersList = new ArrayList<>();
    private List<UUID> spectatorList = new ArrayList<>();
    private long timeFinishedAt;

    public List<UUID> getPlayersList() {
        return playersList;
    }

    public List<UUID> getSpectatorList() {
        return spectatorList;
    }

    private void endGame() {
        blockCheck.cancel();
        UUID winner = Collections.max(arena.getPointsMap().entrySet(), Map.Entry.comparingByValue()).getKey();

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(Bukkit.getPlayer(winner).getName() + " has won the game.");
        });
    }

    private void checkEnd() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(timeFinishedAt <= System.currentTimeMillis()) {
                    endGame();
                }
            }
        }.runTaskTimer(GameShuffle.getInstance(), 0, 1);
    }


    private void checkNetherite() {
        blockCheck = new BukkitRunnable() {
            @Override
            public void run() {
                playersList.forEach(uuid -> {
                    Player p = Bukkit.getPlayer(uuid);
                    if(p != null) {
                        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                        if(block.getType() == Material.NETHERITE_BLOCK) {
                            arena.teleportToNewRoom(p);
                        }
                    }
                });
            }
        }.runTaskTimer(GameShuffle.getInstance(), 10, 15);
    }


}
