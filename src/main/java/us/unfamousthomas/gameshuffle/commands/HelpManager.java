package us.unfamousthomas.gameshuffle.commands;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class HelpManager {

	private List<HelpItem> helpItems = Lists.newArrayList();
	private List<ArrayList<HelpItem>> helpPages = Lists.newArrayList();

	public HelpManager() {
		helpPages.add(Lists.newArrayList());
	}

	public void registerHelpItem(Command command) {
		HelpItem helpItem = new HelpItem(command);

		helpItems.add(helpItem);

		if (helpPages.get(helpPages.size() - 1).size() >= 10)
			helpPages.add(Lists.newArrayList());

		helpPages.get(helpPages.size() - 1).add(helpItem);
	}

	public int getNumberOfPages() {
		return helpPages.size();
	}

	public String getHelpPage(int page) {
		if (getNumberOfPages() < page || page < 1)
			return "Invalid page number.";

		StringBuilder pageBuilder = new StringBuilder("Help (Page ").append(page).append("/").append(getNumberOfPages()).append(")\n");

		for (HelpItem helpItem : helpPages.get(page - 1))
			pageBuilder.append("§6§l/").append(helpItem.getName()).append(" §e").append(helpItem.getDescription()).append("\n");

		return pageBuilder.toString();
	}

	public String getCommandHelp(String name) {
		HelpItem item = null;

		for (HelpItem helpItem : helpItems)
			if (helpItem.getName().equalsIgnoreCase(name))
				item = helpItem;


		if (item == null)
			return "Command not found.";

		return "Help for §l" + name +
				"\n\n§6§lName §e" + item.getName() +
				"\n§6§lDescription §e" + item.getDescription() +
				"\n§6§lRequired Rank §e" + item.getCommand().getRank();
	}

}
