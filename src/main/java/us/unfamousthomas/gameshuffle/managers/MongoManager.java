package us.unfamousthomas.gameshuffle.managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.mongo.objects.Room;
import us.unfamousthomas.gameshuffle.mongo.daos.AccountDao;
import us.unfamousthomas.gameshuffle.mongo.daos.RoomDao;


import java.util.ArrayList;
import java.util.List;

public class MongoManager {
    private MongoClient mc;
    private Morphia morphia;
    private List<MongoCredential> credentials = new ArrayList<>();

    private Datastore serverData;
    private AccountDao accountDao;
    private RoomDao roomDao;

    public void init(String host, int port) {
        ServerAddress serverAddress = new ServerAddress(host, port);
        credentials.add(MongoCredential.createCredential("admin", "admin", "b4VXsan2GuTnss".toCharArray()));

        mc = new MongoClient(serverAddress, credentials);
        morphia = new Morphia();

        initConnections();
    }

    private void initConnections() {
        Morphia morphia = getMorphia();
        morphia.mapPackage("us.unfamousthomas.gameshuffle.mongo.objects");
        serverData = morphia.createDatastore(getClient(), "custom_map");
        serverData.ensureIndexes();
        serverData.ensureCaps();

        registerDaos();

    }

    private void registerDaos() {
        accountDao = new  AccountDao(Account.class, serverData);
        roomDao = new RoomDao(Room.class, serverData);
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    private Morphia getMorphia() {
        return morphia;
    }

    private MongoClient getClient() {
        return mc;
    }

    public Datastore getServerData() {
        return serverData;
    }

}
