package us.unfamousthomas.gameshuffle.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UtilMethods {

    public static void sendMessage(Player p, String msg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
