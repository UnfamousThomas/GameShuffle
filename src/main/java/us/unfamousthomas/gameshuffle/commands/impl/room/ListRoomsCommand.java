package us.unfamousthomas.gameshuffle.commands.impl.room;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

import java.util.List;

public class ListRoomsCommand extends Command {
    public ListRoomsCommand() {
        super("list", Rank.DEV);
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        List<Room> roomList = GameShuffle.getInstance().getMongoManager().getRoomDao().find().asList();

        if(args.size() == 0) {
            roomList.forEach(room -> {
                TextComponent message = new TextComponent("Room (Spawn TP) - " + room.getId());
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + room.getSpawnLocation().getX() + " " + room.getSpawnLocation().getY() + " " + room.getSpawnLocation().getZ()));
                sender.sendMessage(message);
                sender.sendMessage("\n");

                TextComponent message2 = new TextComponent("Room (Delete) - " + room.getId());
                message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/room delete " + room.getId()));
                sender.sendMessage(message2);
                sender.sendMessage("\n");

            });
        } else if (args.size() == 1 && args.get(0).equalsIgnoreCase("total")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eThe total amount of rooms registered in the database is: " + roomList.size()));
        } else if (args.size() == 1 && args.get(0).equalsIgnoreCase("cord")) {
            roomList.forEach(room -> {
                TextComponent message = new TextComponent("Room (" + room.getSpawnLocation().getX() + "; " + room.getSpawnLocation().getY() + "; " + room.getSpawnLocation().getZ() +  ")");
                sender.sendMessage(message);
                sender.sendMessage("\n");
            });
        } else if (args.size() == 1 && args.get(0).equalsIgnoreCase("unfilter")){
            roomList.forEach(room -> {

            });
        } else {
            account.sendMessage(Message.INVALID_ARGUMENTS);
        }
    }
}
