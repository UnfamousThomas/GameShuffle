package us.unfamousthomas.gameshuffle.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

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
    private Map<UUID, BukkitTask> bukkitTaskMap = new HashMap<>();
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

    public Room getCurrentRoom(Player player) {
        return this.arena.getCurrentRoom(player);
    }


    public void triggerRunnable(Player player, Room room, Account account) {
        if(bukkitTaskMap.containsKey(player.getUniqueId())) {
            bukkitTaskMap.get(player.getUniqueId()).cancel();
        }

        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(room.getSpawnLocation());
                triggerRunnable(player, room, account);
            }

        }.runTaskTimer(GameShuffle.getInstance(), 0L, room.getTime() * 60 * 20L);

        bukkitTaskMap.put(player.getUniqueId(), bukkitTask);
    }

}
