package us.unfamousthomas.gameshuffle.commands.impl.room;

import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.commands.Command;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.game.RoomType;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;

import java.util.List;

public class AddRoomCommand extends Command {
    public AddRoomCommand() {
        super("room", Rank.DEV);
        addSubcommands(
                new AddSpawnRoomCommand(),
                new ListRoomsCommand(),
                new DeleteRoomCommand(),
                new SetTypeCommand()
        );

        minArgs = 2;
        maxArgs = 2;
    }

    @Override
    protected void run(Player sender, Account account, List<String> args) {
        String id = args.get(0);
        String points = args.get(1);
        int points_int;

        try {
            points_int = Integer.parseInt(points);
        }catch (Exception ex) {
            account.sendMessage(Message.INCORRECT_USAGE);
            return;
        }

        Room room = new Room();
        room.setEarnablePoints(points_int);
        room.setId(id);
        room.setType(RoomType.PARKOUR);

        GameShuffle.getInstance().getMongoManager().getRoomDao().save(room);

        account.sendMessage(Message.SUCCESS);
    }
}
