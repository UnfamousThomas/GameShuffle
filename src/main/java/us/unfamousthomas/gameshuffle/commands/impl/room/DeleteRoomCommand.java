package us.unfamousthomas.gameshuffle.commands.impl.room;

import org.bukkit.entity.Player;
import org.mongodb.morphia.query.Query;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

import java.util.List;

public class DeleteRoomCommand extends Command {
    public DeleteRoomCommand() {
        super("delete", Rank.DEV);
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        if(args.size() == 1 && args.get(0).equalsIgnoreCase("purge")) {
            List<Room> roomList = GameShuffle.getInstance().getMongoManager().getRoomDao().find().asList();
            roomList.forEach(room -> {
                GameShuffle.getInstance().getMongoManager().getRoomDao().delete(room);
            });
            account.sendMessage(Message.SUCCESS);
            return;
        }

        String id = args.get(0);

        Query<Room> query = GameShuffle.getInstance().getMongoManager().getServerData().createQuery(Room.class)
                .field("id")
                .equal(id);

        List<Room> rooms = query.asList();

        if(rooms.size() > 1) {
            account.sendMessage(Message.ERROR);
            return;
        }
        Room room = rooms.get(0);


        GameShuffle.getInstance().getMongoManager().getRoomDao().delete(room);

        account.sendMessage(Message.SUCCESS);

    }
}
