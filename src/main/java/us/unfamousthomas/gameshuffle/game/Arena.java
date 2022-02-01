package us.unfamousthomas.gameshuffle.game;

import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

import java.util.*;

public class Arena {

    List<Room> roomList = new ArrayList<>();
    Map<UUID, Room> currentRoomMap = new HashMap<>();

    public void teleportToNewRoom(Player player) {
        Room room;
        if(currentRoomMap.containsKey(player.getUniqueId())) {
            room = getNewRoomOldPlayer(player);
        } else {
            room = getRandomRoom();
        }

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
}
