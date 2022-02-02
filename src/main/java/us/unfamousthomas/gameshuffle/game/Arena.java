package us.unfamousthomas.gameshuffle.game;

import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

import java.util.*;

public class Arena {

    public Arena() {
        loadRooms();
    }
    private List<Room> roomList = new ArrayList<>();
    private Map<UUID, Room> currentRoomMap = new HashMap<>();
    private Map<UUID, Integer> pointsMap = new HashMap<>();


    public void teleportToNewRoom(Player player) {
        Room room;
        if(currentRoomMap.containsKey(player.getUniqueId())) {
            int points = pointsMap.get(player.getUniqueId());
            Room currentRoom = currentRoomMap.get(player.getUniqueId());
            points = points + currentRoom.getEarnablePoints();

            pointsMap.put(player.getUniqueId(), points);
            player.setLevel(points);
            room = getNewRoomOldPlayer(player);
            //todo scoreboard handling
        } else {
            room = getRandomRoom();
        }

        currentRoomMap.put(player.getUniqueId(), room);
        player.teleport(room.getSpawnLocation());
        player.setBedSpawnLocation(room.getSpawnLocation(), true);

    }

    private Room getNewRoomOldPlayer(Player player) {
        Room room = getRandomRoom();
        Room currentRoom = currentRoomMap.get(player.getUniqueId());
        if(room == currentRoom) {
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

    public Map<UUID, Integer> getPointsMap() {
        return pointsMap;
    }
}
