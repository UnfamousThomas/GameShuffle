package us.unfamousthomas.gameshuffle.commands;

public class HelpItem {

	private final String name, description;
	private final Command command;

	public HelpItem(Command command) {
		this.command = command;
		this.name = command.name;
		this.description = command.description;
	}

	public String getName() {
		return name;
	}

	public Command getCommand() {
		return command;
	}

	public String getDescription() {
		return description;
	}
}