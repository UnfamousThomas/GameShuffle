package us.unfamousthomas.gameshuffle.commands.impl;

import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.commands.CommandManager;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;

import java.util.List;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", Rank.MEMBER);

        description = "Displays a list of commands.";
    }

    @Override
    public void run(Player sender, Account account, List<String> args) {
        if (account.getRank().isHigherOrEquals(Rank.DEV)) {
            if (args.size() == 0) {
                sender.sendMessage(CommandManager.getInstance().getHelpManager().getHelpPage(1));
                return;
            }

            try {
                sender.sendMessage(CommandManager.getInstance().getHelpManager().getHelpPage(Integer.parseInt(args.get(0))));
            } catch (NumberFormatException exception) {
                sender.sendMessage(CommandManager.getInstance().getHelpManager().getCommandHelp(args.get(0)));
            }

            return;
        } else {
            account.sendMessage(Message.HELP);
        }
    }

}
