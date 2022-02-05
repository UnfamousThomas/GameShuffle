package us.unfamousthomas.gameshuffle.commands.impl;

import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.game.Game;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.utils.LogLevel;
import us.unfamousthomas.gameshuffle.utils.Logger;

import java.util.List;

public class EndGame extends Command {
    public EndGame() {
        super("end", Rank.DEV);
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        GameShuffle instance = GameShuffle.getInstance();

        if(instance.getGame() != null){
            instance.getGame().endGame();
            Logger.log(LogLevel.INFO, "Game ended through command.");
        } else {
            account.sendMessage(Message.ERROR);
        }
    }
}
