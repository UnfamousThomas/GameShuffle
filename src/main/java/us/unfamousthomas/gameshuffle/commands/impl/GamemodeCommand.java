package us.unfamousthomas.gameshuffle.commands.impl;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;

import java.util.List;

public class GamemodeCommand extends Command {
    public GamemodeCommand() {
        super("gamemode", Rank.DEV);
        aliases = alias("gm");
        maxArgs = 2;
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        if(args.size() == 0) {
            GameMode gameMode = null;
            switch (sender.getGameMode()) {
                case CREATIVE -> {
                    gameMode = GameMode.SPECTATOR;
                    break;
                }
                case SPECTATOR -> {
                    gameMode = GameMode.ADVENTURE;
                    break;
                }

                case ADVENTURE -> {
                    gameMode = GameMode.SURVIVAL;
                    break;
                }

                case SURVIVAL -> {
                    gameMode = GameMode.CREATIVE;
                    break;
                }

            }

            if(gameMode == null) {
                account.sendMessage(Message.INCORRECT_USAGE);
            } else {
                sender.setGameMode(gameMode);
                account.sendMessage(Message.SUCCESS);
            }
        } else if (args.size() == 1) {
            String gamemode = args.get(0).toUpperCase();
            GameMode gameMode = GameMode.valueOf(gamemode);

            sender.setGameMode(gameMode);
            account.sendMessage(Message.SUCCESS);
        } else {
            account.sendMessage(Message.INVALID_ARGUMENTS);
        }
    }
}
