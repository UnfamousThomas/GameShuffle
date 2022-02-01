package us.unfamousthomas.gameshuffle.mongo.objects;

import org.bukkit.Location;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "rooms", noClassnameStored = true)
public class Room {
    @Id
    private String id;

    private int earnablePoints;
    private Location spawnLocation;
    private Location finishLocation;

    public int getEarnablePoints() {
        return earnablePoints;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public Location getFinishLocation() {
        return finishLocation;
    }

    public String getId() {
        return id;
    }

    public void setEarnablePoints(int earnablePoints) {
        this.earnablePoints = earnablePoints;
    }

    public void setFinishLocation(Location finishLocation) {
        this.finishLocation = finishLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public void setId(String id) {
        this.id = id;
    }
}
