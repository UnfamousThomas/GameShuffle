package us.unfamousthomas.gameshuffle.commands.impl.room;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.commands.Command;
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
        roomList.forEach(room -> {
            TextComponent message = new TextComponent("Room (Spawn TP) - " + room.getId());
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp "  + room.getSpawnLocation().getX() + " " + room.getSpawnLocation().getY() + " " + room.getSpawnLocation().getZ()));
            sender.sendMessage(message);
            sender.sendMessage("\n");

            TextComponent message2 = new TextComponent("Room (End TP) - " + room.getId());
            message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + room.getFinishLocation().getX() + " " + room.getFinishLocation().getY() + " " + room.getFinishLocation().getZ()));
            sender.sendMessage(message2);
            sender.sendMessage("\n");

        });
    }
}
