package us.unfamousthomas.gameshuffle.enums;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Optional;

public enum Rank {
    MEMBER("", 0, ChatColor.GRAY, ChatColor.DARK_GRAY, "&8", "&8"),
    DEV("ARENDAJA", 3, ChatColor.WHITE, ChatColor.YELLOW, "&e&l", "&e");

    private String prefix;
    private int priority;
    private ChatColor chatColor;
    private ChatColor rankColor;
    private String prefixColor;
    private String endColor;

    Rank(String prefix, int priority, ChatColor chatColor, ChatColor rankColor, String prefixColor, String endColor) {
        this.prefix = prefix;
        this.priority = priority;
        this.chatColor = chatColor;
        this.rankColor = rankColor;
        this.endColor = endColor;
        this.prefixColor = prefixColor;
    }

    public static Optional<Rank> fromString(String rank) {
        return Arrays.stream(values()).filter(all -> all.toString().equalsIgnoreCase(rank)).findFirst();
    }

    public String getPrefix() {
        return prefix;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isHigher(Rank rankToCompare) {
        return this.priority > rankToCompare.priority;
    }

    public boolean isHigherOrEquals(Rank rankToCompare) {
        return this.priority >= rankToCompare.priority;
    }

    public boolean isLower(Rank rankToCompare) {
        return this.priority < rankToCompare.priority;
    }

    public boolean isLowerOrEquals(Rank rankToCompare) {
        return this.priority <= rankToCompare.priority;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public ChatColor getRankColor() {
        return rankColor;
    }

    public String getName() {
        return WordUtils.capitalize(toString().toLowerCase().replace("_PLUS", "+").replace('_', ' '));
    }
    public String getPrefixOnly() {
        return prefixColor;
    }

    public String getEndOnly() {
        return endColor;
    }

}
