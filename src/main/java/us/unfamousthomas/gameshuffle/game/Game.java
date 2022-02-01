package us.unfamousthomas.gameshuffle.game;

import org.bukkit.scheduler.BukkitRunnable;
import us.unfamousthomas.gameshuffle.GameShuffle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Game {

    public Game() {
        this.timeStartedAt = System.currentTimeMillis();
        this.timeFinishedAt = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        checkEnd();
    }
    List<UUID> playersList = new ArrayList<>();
    List<UUID> spectatorList = new ArrayList<>();
    long timeStartedAt;
    long timeFinishedAt;

    public List<UUID> getPlayersList() {
        return playersList;
    }

    public List<UUID> getSpectatorList() {
        return spectatorList;
    }

    private void endGame() {

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
}
