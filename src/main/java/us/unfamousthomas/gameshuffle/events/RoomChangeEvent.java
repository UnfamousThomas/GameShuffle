package us.unfamousthomas.gameshuffle.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

public class RoomChangeEvent extends Event {
    private Room newRoom;
    private Player player;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public RoomChangeEvent(Room newRoom, Player player) {
        this.newRoom = newRoom;
        this.player = player;

    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public Room getNewRoom() {
        return newRoom;
    }


}
