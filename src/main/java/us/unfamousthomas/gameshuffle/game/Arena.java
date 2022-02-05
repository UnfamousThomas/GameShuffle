package us.unfamousthomas.gameshuffle.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.events.RoomChangeEvent;
import us.unfamousthomas.gameshuffle.managers.scoreboard.GameScoreboard;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

import java.util.*;

public class Arena {

    private Game game;
    public Arena(Game game) {
        this.game = game;

        loadRooms();
        loadScoreboard();
        teleportPlayers();
    }
    private List<Room> roomList = new ArrayList<>();
    private Map<UUID, Room> currentRoomMap = new HashMap<>();
    private Map<UUID, Integer> pointsMap = new HashMap<>();
    private GameScoreboard gameScoreboard = new GameScoreboard();

    public void setupPlayer(Player player) {
        teleportToFirstRoom(player);
        gameScoreboard.createScoreboardForPlayer(player, getGame());
        getGame().getPlayersList().forEach(id -> {
            Player p = Bukkit.getPlayer(id);

            if(p != null && p.isOnline()) {
                gameScoreboard.gainedPoint(p, pointsMap.get(p), getGame());
            }
        });
    }
    public void teleportToNewRoom(Player player) {
        Room room;
        Room currentRoom;
        if(currentRoomMap.containsKey(player.getUniqueId())) {
            int points = 0;
            if(pointsMap.containsKey(player.getUniqueId())) {
                points = pointsMap.get(player.getUniqueId());
            }
            currentRoom = currentRoomMap.get(player.getUniqueId());
            points = points + currentRoom.getEarnablePoints();

            pointsMap.put(player.getUniqueId(), points);
            player.setLevel(points);
            gameScoreboard.gainedPoint(player, points, getGame());
            room = getNewRoomOldPlayer(player);
        } else {
            room = getRandomRoom();
            player.setLevel(1);
            gameScoreboard.gainedPoint(player, 1, getGame());
        }

        currentRoomMap.put(player.getUniqueId(), room);
        player.teleport(room.getSpawnLocation());
        Bukkit.getPluginManager().callEvent(new RoomChangeEvent(room, player));
        player.setBedSpawnLocation(room.getSpawnLocation(), true);

    }

    public void teleportToFirstRoom(Player player) {
        Room room = getRandomRoom();
        currentRoomMap.put(player.getUniqueId(), room);
        player.setBedSpawnLocation(room.getSpawnLocation(), true);
        player.setLevel(0);
        gameScoreboard.gainedPoint(player, 0, getGame());
        player.teleport(room.getSpawnLocation());

    }

    public void skipLevel(Player player) {
        Room room = getRandomRoom();
        currentRoomMap.put(player.getUniqueId(), room);
        player.setBedSpawnLocation(room.getSpawnLocation(), true);
        player.teleport(room.getSpawnLocation());
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 7 * 20, 500, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 7 * 20, 500, false, true));

    }
    public Game getGame() {
        return game;
    }

    private Room getNewRoomOldPlayer(Player player) {
        Room room = getRandomRoom();
        Room currentRoom = currentRoomMap.get(player.getUniqueId());
        if(room.getId().equals(currentRoom.getId())) {
            getNewRoomOldPlayer(player);
        }

        return room;
        }

    public Room getRandomRoom() {
        Random rand = new Random();
        return roomList.get(rand.nextInt(roomList.size()));
    }

    public Room getCurrentRoom(UUID uuid) {
        return currentRoomMap.get(uuid);
    }

    public Room getCurrentRoom(Player player) {
        return currentRoomMap.get(player.getUniqueId());
    }

    public void addRoom(Room room) {
        roomList.add(room);
    }

    private void loadRooms() {
        GameShuffle.getInstance().getMongoManager().getRoomDao().find().asList().forEach(room -> {
            roomList.add(room);
        });
    }

    private void loadScoreboard() {
        for (Player p : Bukkit.getOnlinePlayers()) {
           gameScoreboard.createScoreboardForPlayer(p, getGame());
        }
    }

    public Map<UUID, Integer> getPointsMap() {
        return pointsMap;
    }

    public void teleportPlayers() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player != null && player.isOnline()) {
                teleportToFirstRoom(player);
            }
        });
    }
    public void endGame() {
        gameScoreboard.removeScoreboard(getGame());
    }
}
