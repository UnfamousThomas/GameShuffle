package us.unfamousthomas.gameshuffle.mongo.objects;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import us.unfamousthomas.gameshuffle.enums.Message;
import us.unfamousthomas.gameshuffle.enums.MessageEnum;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.stats.TotalStats;

import java.util.UUID;

@Entity(value = "users", noClassnameStored = true)
public class Account {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private UUID uuid;

    private Rank rank;

    private MessageEnum.Lang lang;

    private String username;

    private TotalStats totalStats;

    public TotalStats getTotalStats() {
        return totalStats;
    }

    public void setTotalStats(TotalStats totalStats) {
        this.totalStats = totalStats;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUUID() {
        return getUuid();
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUUID(UUID uuid) {
        setUuid(uuid);
    }

    public Rank getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MessageEnum.Lang getLang() {
        return lang;
    }

    public void setLang(MessageEnum.Lang lang) {
        this.lang = lang;
    }

    public void sendMessage(Message message) {
        getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message.get(getLang())));
    }

    public void sendManualMessage(String msg) {
        getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
    public Player getPlayer() {
        return Bukkit.getPlayerExact(this.username);
    }

    public String getPrefixDisplay() {
        String replaced = getRank().getPrefixOnly() + "&l" + getRank().getName() + " " + ChatColor.RESET + getRank().getRankColor() + getUsername() + getRank().getEndOnly();
        return ChatColor.translateAlternateColorCodes('&', replaced);
    }
}
