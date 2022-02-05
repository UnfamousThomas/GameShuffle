package us.unfamousthomas.gameshuffle.managers.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.game.Game;

import java.util.UUID;

public class GameScoreboard {

    public void createScoreboardForPlayer(Player player, Game game) {
        Scoreboard scores = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scores.registerNewObjective("Game", "dummy", "§e§lMäng");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        for (UUID uuid : game.getPlayersList()) {
           Team team = scores.registerNewTeam(Bukkit.getPlayer(uuid).getName());
           String prefix = "§c" + team.getName();
           team.setPrefix(prefix);
           objective.getScore(prefix).setScore(0);
           player.setScoreboard(scores);
           player.getScoreboard();
        }
    }

    public void gainedPoint(Player player, int point, Game game) {
        for (UUID uuid : game.getPlayersList()) {
            // if(Bukkit.getPlayer(uuid).getScoreboard() == null)
            if (Bukkit.getPlayer(uuid) != null) {
                if (Bukkit.getPlayer(uuid).getScoreboard().getObjective("Game") == null) {
                    createScoreboardForPlayer(player, game);
                } else {
                    Player p = Bukkit.getPlayer(uuid);
                    Objective object = p.getScoreboard().getObjective("Game");

                    Team team = p.getScoreboard().getTeam(player.getName());
                    String prefix = "§c" + team.getName();
                    object.getScore(prefix).setScore(point);
                }
            }
        }
    }

    public void removeScoreboard(Game game) {
        for (UUID uuid : game.getPlayersList()) {
            Player player = Bukkit.getPlayer(uuid);

            if(player != null && player.isOnline()) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(GameShuffle.getInstance(), new Runnable() {
                    public void run() {
                        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    }
                }, 15L);
            }
        }
    }
}
