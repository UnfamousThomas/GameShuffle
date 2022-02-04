package us.unfamousthomas.gameshuffle.mongo.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.game.RoomType;

@Entity(value = "rooms", noClassnameStored = true)
public class Room {
    @Id
    private String id;

    private int earnablePoints;

    String world;
    private double xSpawn;
    private double zSpawn;
    private double ySpawn;
    private float pitchSpawn;
    private float yawSpawn;

    private RoomType type;
    private int time;

    public int getEarnablePoints() {
        return earnablePoints;
    }

    public Location getSpawnLocation() {

        return new Location(getWorld(), xSpawn, ySpawn, zSpawn, yawSpawn, pitchSpawn);
    }


    public void setWorld(String world) {
        this.world = world;
    }

    public String getId() {
        return id;
    }

    public RoomType getType() {
        return type;
    }

    public void setEarnablePoints(int earnablePoints) {
        this.earnablePoints = earnablePoints;
    }

    public World getWorld() {
        if(world == null) {
            return Bukkit.getServer().getWorlds().get(0);
        }

        return Bukkit.getWorld(world);
    }

    public void setSpawnLocation(Location spawnLocation) {
        xSpawn = spawnLocation.getX();
        ySpawn = spawnLocation.getY();
        zSpawn = spawnLocation.getZ();

        pitchSpawn = spawnLocation.getPitch();
        yawSpawn = spawnLocation.getYaw();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
