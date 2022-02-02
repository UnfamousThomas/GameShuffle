package us.unfamousthomas.gameshuffle.managers.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import us.unfamousthomas.gameshuffle.GameShuffle;

import java.util.UUID;

public class GameScoreboard {

    public void createScoreboardForPlayer(Player player) {
        Scoreboard scores = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scores.registerNewObjective("Game", "dummy", "§e§lMäng");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        for (UUID uuid : GameShuffle.getInstance().getGame().getPlayersList()) {
           Team team = scores.registerNewTeam(Bukkit.getPlayer(uuid).getName());
           String prefix = "§c" + team.getName();
           team.setPrefix(prefix);
           objective.getScore(prefix).setScore(0);
           player.setScoreboard(scores);
        }
    }

    public void gainedPoint(Player player, int point) {
        for (UUID uuid : GameShuffle.getInstance().getGame().getPlayersList()) {
            if (Bukkit.getPlayer(uuid).getScoreboard().getObjective("Game") == null) {
                createScoreboardForPlayer(player);
            } else {
                Player p = Bukkit.getPlayer(uuid);
                Objective object = p.getScoreboard().getObjective("Game");

                Team team = p.getScoreboard().getTeam(player.getName());
                String prefix = "§c" + team.getName();
                object.getScore(prefix).setScore(point);
            }
        }
    }

    public void removeScoreboard() {
        for (UUID uuid : GameShuffle.getInstance().getGame().getPlayersList()) {
            Player player = Bukkit.getPlayer(uuid);

            if(player != null) {
                player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
        }
    }
}
