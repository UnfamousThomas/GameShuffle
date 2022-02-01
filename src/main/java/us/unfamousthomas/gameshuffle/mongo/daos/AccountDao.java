package us.unfamousthomas.gameshuffle.mongo.daos;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;

public class AccountDao extends BasicDAO<Account, String> {

	public AccountDao(Class<Account> entityClass, Datastore ds) {
		super(entityClass, ds);
	}

}
