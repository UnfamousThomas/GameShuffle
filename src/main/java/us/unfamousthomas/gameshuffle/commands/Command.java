package us.unfamousthomas.gameshuffle.commands;

import com.google.common.collect.Maps;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;

import java.util.List;
import java.util.Map;

public abstract class Command {

    final String name;
    private final Rank rank;
    private final Map<String, Command> subcommands = Maps.newHashMap();
    protected String[] aliases = {};
    protected String description = "No description set.";
    protected int minArgs = 0;
    protected int maxArgs = Integer.MAX_VALUE;

    public Command(String name, Rank rank) {
        this.name = name;
        this.rank = rank;
    }

    protected abstract void run(Player sender, Account account, List<String> args);


    void execute(Player player, List<String> args) {

        if (args.size() > 0) {
            Command subcommand = subcommands.get(args.get(0).toLowerCase());
            if (subcommand != null) {
                args.remove(0);
                subcommand.execute(player, args);
                return;
            }
        }

        GameShuffle.getInstance().getAccountManager().getAccount(player.getUniqueId(), account -> {

            if (args.size() < minArgs) {
                account.sendMessage(Message.INCORRECT_USAGE);
                return;
            }

            if (args.size() > maxArgs) {
                account.sendMessage(Message.INCORRECT_USAGE);
                return;
            }

            if (!account.getRank().isHigherOrEquals(rank)) {
                player.sendMessage(Message.NO_PERMS.replace(account.getLang(), "{required}", rank.toString()));
                return;
            }

            run(player, account, args);
        });
    }

    void processConsole(ConsoleCommandSender sender, List<String> args) {

        if (args.size() > 0) {
            Command subcommand = subcommands.get(args.get(0));
            if (subcommand != null) {
                args.remove(0);
                subcommand.processConsole(sender, args);
                return;
            }
        }

        ((ConsoleCommand) this).consoleExecute(sender, args);

    }

    protected void addSubcommands(Command... commands) {
        for (Command command : commands) {
            subcommands.put(command.name, command);

            for (String alias : command.aliases)
                subcommands.put(alias, command);

        }
    }

    Rank getRank() {
        return rank;
    }

    protected String[] alias(String... aliases) {
        return aliases;
    }
}