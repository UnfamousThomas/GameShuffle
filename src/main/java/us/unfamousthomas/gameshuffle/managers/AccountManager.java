package us.unfamousthomas.gameshuffle.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.utils.Callback;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class AccountManager {
    private final GameShuffle core = GameShuffle.getInstance();
    private final MongoManager mongoManager = core.getMongoManager();
    private final Collection<Account> online = new HashSet<>();

    public Account getAccount(UUID uuid) {
        for (Account account : online) {
            if (account.getUUID().equals(uuid)) {
                return account;
            }
        }
        return null;
    }

    public HashSet<Account> getAccounts() {
        if (this.online.size() < 1) return null;
        return (HashSet<Account>) this.online;
    }

    public void unloadAccount(Player player) {
        online.remove(getAccount(player.getUniqueId()));
    }

    public void getAccount(UUID uuid, Callback<Account> callback) {
        try {
            if (getAccount(uuid) != null) {
                callback.call(getAccount(uuid));
                return;
            }

            Account account;
            account = mongoManager.getAccountDao().findOne("uuid", uuid);

            callback.call(account);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getAccount(String username, Callback<Account> callback) {
        if (Bukkit.getPlayerExact(username) != null) {
            getAccount(Bukkit.getPlayerExact(username).getUniqueId(), callback);
            return;
        }

        Account account;
        account = mongoManager.getAccountDao().findOne("username", username);
        callback.call(account);

    }

    public void loadAccountOrInsertNew(Player player, Callback<Account> callback) {
        Account account;
        try {
            account = mongoManager.getAccountDao().findOne("uuid", player.getUniqueId());
            if (account == null) {
                account = new Account();
                account.setUsername(player.getName());
                account.setUUID(player.getUniqueId());
                account.setRank(Rank.MEMBER);
                account.setLang(Message.Lang.EESTI);
                insertNewAccount(account);
            }
            online.add(account);
            callback.call(account);
        } catch (Exception exception) {
            callback.fail(exception.getMessage());
        }
    }

    public void saveAccount(Player player) {
        UUID uuid = player.getUniqueId();
        saveAccount(uuid);
    }

    public void saveAccount(UUID uuid) {
        try {
            if (Bukkit.getPlayer(uuid) != null) {
                mongoManager.getAccountDao().save(getAccount(uuid));
            } else {
                getAccount(uuid, data -> {
                    mongoManager.getAccountDao().save(data);
                });
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    public void saveAccount(Account account) {
        mongoManager.getAccountDao().save(account);

    }

    private void insertNewAccount(Account account) {
        mongoManager.getAccountDao().save(account);
    }
}