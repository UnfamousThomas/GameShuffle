package us.unfamousthomas.gameshuffle.commands.impl;

import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.game.Game;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;

import java.util.List;

public class StartGame extends Command {
    public StartGame() {
        super("start", Rank.DEV);
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        GameShuffle instance = GameShuffle.getInstance();

        if(instance.getGame() == null){
            instance.setGame(new Game());
        } else {
            account.sendMessage(Message.ERROR);
        }
    }
}
