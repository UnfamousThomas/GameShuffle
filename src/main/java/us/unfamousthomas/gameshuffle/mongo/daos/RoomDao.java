package us.unfamousthomas.gameshuffle.mongo.daos;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;

public class RoomDao extends BasicDAO<Room, String> {

	public RoomDao(Class<Room> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

}
