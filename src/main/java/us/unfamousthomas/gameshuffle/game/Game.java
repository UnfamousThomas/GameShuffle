package us.unfamousthomas.gameshuffle.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;
import us.unfamousthomas.gameshuffle.utils.Constant;
import us.unfamousthomas.gameshuffle.utils.ItemStackBuilder;
import us.unfamousthomas.gameshuffle.utils.LogLevel;
import us.unfamousthomas.gameshuffle.utils.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {

    public Game() {
        this.timeFinishedAt = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        checkEnd();
        Logger.log(LogLevel.INFO, "Game started.");
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getInventory().clear();
            player.updateInventory();
            playersList.add(player.getUniqueId());
            ItemStack vili = new ItemStackBuilder(Material.WHITE_WOOL).withName("Jäta Vahele").buildStack();
            player.getInventory().addItem(vili);
            player.updateInventory();
            player.setLevel(0);
        });
        arena = new Arena(this);
        checkNetherite();
    }

    private Arena arena;
    private Map<UUID, BukkitTask> bukkitTaskMap = new HashMap<>();
    private BukkitTask blockCheck;
    private BukkitTask endCheck;

    private List<UUID> playersList = new ArrayList<>();
    private List<UUID> spectatorList = new ArrayList<>();
    private long timeFinishedAt;

    public List<UUID> getPlayersList() {
        return playersList;
    }

    public List<UUID> getSpectatorList() {
        return spectatorList;
    }

    private boolean checkIfMaxZero() {
        AtomicBoolean check = new AtomicBoolean(true);
        arena.getPointsMap().forEach((uuid, integer) -> {
            if (integer > 0) {
                check.set(false);
            }
        });

        return check.get();
    }

    public void endGame() {
        blockCheck.cancel();
        if(!endCheck.isCancelled()) endCheck.cancel();

        arena.endGame();
        if (!checkIfMaxZero()) {
            UUID winner = Collections.max(arena.getPointsMap().entrySet(), Map.Entry.comparingByValue()).getKey();
            String username = Bukkit.getPlayer(winner).getName();
            int points = arena.getPointsMap().get(winner);

            if (checkforExtraWinners(winner)) {
                String newMessage = Message.WON_MULTIPLE.replace("%points%", String.valueOf(points)).replace("%users%", String.join(", ", getAllWinners(points)));
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&eMäng Läbi!"), "");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', newMessage));
                    player.setLevel(0);
                    player.teleport(Constant.SPAWN_LOC);
                    player.setGameMode(GameMode.ADVENTURE);
                });
            } else {
                String newMessage = Message.WON_SINGLE.replace("%user%", username).replace("%points%", String.valueOf(points));
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&eMäng Läbi!"), "");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', newMessage));
                    player.setLevel(0);
                    player.teleport(Constant.SPAWN_LOC);
                    player.setGameMode(GameMode.ADVENTURE);
                });
            }
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                GameShuffle.getInstance().getAccountManager().getAccount(player.getUniqueId()).sendMessage(Message.NOBODY_WON);
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&eMäng Läbi!"), "");
                player.setLevel(0);
                player.teleport(Constant.SPAWN_LOC);
                player.setGameMode(GameMode.ADVENTURE);
            });
        }


        Logger.log(LogLevel.INFO, "Game ended.");

        GameShuffle.getInstance().setGame(null);
    }

    private boolean checkforExtraWinners(UUID winner) {
        AtomicBoolean extraWinners = new AtomicBoolean(false);
        int points = arena.getPointsMap().get(winner);
        arena.getPointsMap().forEach((uuid, integer) -> {
            if (uuid != winner && points == integer) {
                extraWinners.set(true);
            }
        });

        return extraWinners.get();
    }

    private List<String> getAllWinners(int points) {
        List<String> winners = new ArrayList<>();
        arena.getPointsMap().forEach((uuid, integer) -> {
            if (integer == points) {
                winners.add(Bukkit.getPlayer(uuid).getName());
            }
        });

        return winners;
    }

    private void checkEnd() {
        endCheck = new BukkitRunnable() {
            @Override
            public void run() {
                //todo tundub et timer ei tööta... Vaata üle!
                if (System.currentTimeMillis() >= timeFinishedAt) {
                    broadcast("&cMäng lõppes!");
                    endGame();
                    cancel();
                }


            }
        }.runTaskTimer(GameShuffle.getInstance(), 0, 1);
    }


    public void skipLevel(Player player) {
        arena.skipLevel(player);
    }


    private void broadcast(String msg) {
        getPlayersList().forEach(playerId -> {
            System.out.println("ID: " + playerId);
            Player player = Bukkit.getPlayer(playerId);

            if (player != null && player.isOnline()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        });
    }

    public void setupPlayer(Player player) {
        arena.setupPlayer(player);
    }

    private void checkNetherite() {
        blockCheck = new BukkitRunnable() {
            @Override
            public void run() {
                playersList.forEach(uuid -> {
                    Player p = Bukkit.getPlayer(uuid);
                    if (p != null) {
                        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
                        if (block.getType() == Material.NETHERITE_BLOCK) {
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
        if (bukkitTaskMap.containsKey(player.getUniqueId())) {
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
