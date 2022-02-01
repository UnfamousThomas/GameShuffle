package us.unfamousthomas.gameshuffle.commands;

import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public interface ConsoleCommand {

	void consoleExecute(ConsoleCommandSender sender, List<String> args);

}