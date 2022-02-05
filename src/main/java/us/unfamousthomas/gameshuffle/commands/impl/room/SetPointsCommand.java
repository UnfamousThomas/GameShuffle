package us.unfamousthomas.gameshuffle.commands.impl.room;

import org.bukkit.entity.Player;
import org.mongodb.morphia.query.Query;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.game.RoomType;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

import java.util.List;

public class SetPointsCommand extends Command {
    public SetPointsCommand() {
        super("points", Rank.DEV);
        maxArgs = 2;
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        String id = args.get(0);

        Query<Room> query = GameShuffle.getInstance().getMongoManager().getServerData().createQuery(Room.class)
                .field("id")
                .equal(id);

        List<Room> rooms = query.asList();

        if(rooms.size() > 1) {
            sender.sendMessage("Error!");
            return;
        }
        Room room = rooms.get(0);

        String points_string = args.get(1).toUpperCase();
        int points;
        try {
            points = Integer.parseInt(points_string);
        } catch (Exception e) {
            account.sendMessage(Message.INCORRECT_USAGE);
            return;
        }

        room.setEarnablePoints(points);

        GameShuffle.getInstance().getMongoManager().getRoomDao().save(room);

        account.sendMessage(Message.SUCCESS);

    }
}
