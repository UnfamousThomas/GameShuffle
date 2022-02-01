package us.unfamousthomas.gameshuffle.commands;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandManager implements Listener {

	private static final CommandManager instance = new CommandManager();
	private Map<String, Command> commands = Maps.newHashMap();
	private HelpManager helpManager;


	public static CommandManager getInstance() {
		return instance;
	}

	public static void init() {

		Bukkit.getPluginManager().registerEvents(instance, GameShuffle.getInstance());

		instance.helpManager = new HelpManager();

	}

	public void registerCommand(Command command) {
		this.commands.put(command.name, command);

		for (String alias : command.aliases)
			this.commands.put(alias, command);

		instance.helpManager.registerHelpItem(command);

	}

	public void registerCommands(Command... commands) {
		for (Command command : commands)
			registerCommand(command);

	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		event.setCancelled(true);
		String[] argArray = event.getMessage().split("\\s+");

		if (argArray.length != 0 && argArray[0].startsWith("/")) {
			String commandStr = argArray[0].substring(1);

			List<String> args = new ArrayList<>(Arrays.asList(argArray));
			args.remove(0);

			if (commands.containsKey(commandStr.toLowerCase())) {
				commands.get(commandStr.toLowerCase()).execute(event.getPlayer(), args);
			} else {
				GameShuffle.getInstance().getAccountManager().getAccount(event.getPlayer().getUniqueId(), account -> {
					account.sendMessage(Message.COMMAND_NOT_FOUND);
				});
			}

		}

	}


	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		String[] argArray = event.getCommand().split("\\s+");

		if (argArray.length != 0) {
			String commandStr = argArray[0];

			List<String> args = new ArrayList<>(Arrays.asList(argArray));
			args.remove(0);

			if (commands.containsKey(commandStr.toLowerCase())) {
				Command command = commands.get(commandStr.toLowerCase());
				if (command instanceof ConsoleCommand) {
					command.processConsole((ConsoleCommandSender) event.getSender(), args);
					event.setCancelled(true);
				}
			}

		}
	}

	public HelpManager getHelpManager() {
		return helpManager;
	}
}
