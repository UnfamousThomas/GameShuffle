package us.unfamousthomas.gameshuffle.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;

import java.util.List;

public class TeleportCommand  extends Command {
    public TeleportCommand() {
        super("teleport", Rank.DEV);
        aliases = alias("tp");
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        if (args.size() == 1) {
            String targetName = args.get(0);
            Player player = Bukkit.getPlayerExact(targetName);
            if(!player.isOnline()) {
                account.sendMessage(Message.PLAYER_OFFLINE);
                return;
            }

            Location targetLocation = player.getLocation();

            sender.teleport(targetLocation);
            account.sendMessage(Message.SUCCESS);
        } else if (args.size() == 2) {
            String toTeleport = args.get(0);
            String teleportTo = args.get(1);
            Player toTeleportPlayer = Bukkit.getPlayerExact(toTeleport);
            Player teleportToPlayer = Bukkit.getPlayerExact(teleportTo);

            if(!toTeleportPlayer.isOnline() || !teleportToPlayer.isOnline()) {
                account.sendMessage(Message.PLAYER_OFFLINE);
                return;
            }

            toTeleportPlayer.teleport(teleportToPlayer.getLocation());
            account.sendMessage(Message.SUCCESS);

        } else if (args.size() == 3) {
            String xString = args.get(0);
            String yString = args.get(1);
            String zString = args.get(2);

            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(xString);
                y = Double.parseDouble(yString);
                z = Double.parseDouble(zString);
            } catch (Exception e) {
                account.sendMessage(Message.INVALID_ARGUMENTS);
                return;
            }

            sender.teleport(new Location(sender.getWorld(), x, y ,z));
            account.sendMessage(Message.SUCCESS);

        } else if (args.size() == 4) {
            String teleportWho = args.get(0);
            String xString = args.get(1);
            String yString = args.get(2);
            String zString = args.get(3);

            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(xString);
                y = Double.parseDouble(yString);
                z = Double.parseDouble(zString);
            } catch (Exception e) {
                account.sendMessage(Message.INVALID_ARGUMENTS);
                return;
            }

            Player player = Bukkit.getPlayerExact(teleportWho);
            if(!player.isOnline()) {
                account.sendMessage(Message.PLAYER_OFFLINE);
                return;
            }

            player.teleport(new Location(sender.getWorld(), x, y, z));
            account.sendMessage(Message.SUCCESS);

        } else {
            account.sendMessage(Message.INCORRECT_USAGE);
        }
    }
}
