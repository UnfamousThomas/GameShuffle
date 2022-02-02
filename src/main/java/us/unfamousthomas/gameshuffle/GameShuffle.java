package us.unfamousthomas.gameshuffle;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import us.unfamousthomas.gameshuffle.commands.CommandManager;
import us.unfamousthomas.gameshuffle.commands.impl.TeleportCommand;
import us.unfamousthomas.gameshuffle.commands.impl.room.AddRoomCommand;
import us.unfamousthomas.gameshuffle.listeners.AsyncPlayerChatListener;
import us.unfamousthomas.gameshuffle.listeners.PlayerJoinListener;
import us.unfamousthomas.gameshuffle.listeners.PlayerQuitListener;
import us.unfamousthomas.gameshuffle.managers.AccountManager;
import us.unfamousthomas.gameshuffle.managers.MongoManager;
import us.unfamousthomas.gameshuffle.utils.LogLevel;
import us.unfamousthomas.gameshuffle.utils.Logger;

public final class GameShuffle extends JavaPlugin {
    private MongoManager mongoManager;
    private AccountManager accountManager;

    private static GameShuffle instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.mongoManager = new MongoManager();
        mongoManager.init("54.39.243.170", 27017);
        this.accountManager = new AccountManager();
        registerListeners(
                new PlayerQuitListener(),
                new PlayerJoinListener(),
                new AsyncPlayerChatListener()
        );
        CommandManager.init();
        CommandManager.getInstance().registerCommands(
                //new HelpCommand(),
                new AddRoomCommand(),
                new TeleportCommand()
        );

        Logger.log(LogLevel.SUCCESS, "Successfully started game systems.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static GameShuffle getInstance() {
        return instance;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void registerListeners(Listener... listeners) {
        for (Listener l : listeners) {
            this.getServer().getPluginManager().registerEvents(l, this);
            Logger.log(LogLevel.INFO, "Registered listener: " + l.getClass().getSimpleName());
        }
    }
    

}
