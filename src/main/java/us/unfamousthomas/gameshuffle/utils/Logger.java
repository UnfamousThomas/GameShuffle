package us.unfamousthomas.gameshuffle.utils;

import org.bukkit.ChatColor;

public class Logger {

    public static void log(LogLevel level, String msg) {
        switch (level) {
            case INFO:
                System.out.println(ChatColor.translateAlternateColorCodes('&', "&e[&e&lINFO&e]&f " + msg));
                break;
            case ERROR:
                System.out.println(ChatColor.translateAlternateColorCodes('&', "&c[&c&lERROR&c]&f " + msg));
                break;
            case SUCCESS:
                System.out.println(ChatColor.translateAlternateColorCodes('&', "&a[&a&lSUCCESS&a]&f " + msg));
                break;
            case WARNING:
                System.out.println(ChatColor.translateAlternateColorCodes('&', "&3[&3&lWARNING&3]&f " + msg));


        }
    }
}
