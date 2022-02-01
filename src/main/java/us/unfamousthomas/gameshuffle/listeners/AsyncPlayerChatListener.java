package us.unfamousthomas.gameshuffle.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.unfamousthomas.gameshuffle.GameShuffle;
import us.unfamousthomas.gameshuffle.enums.Rank;
import us.unfamousthomas.gameshuffle.mongo.objects.Account;
import us.unfamousthomas.gameshuffle.stats.TotalStats;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        Player player = e.getPlayer();

        Account account = GameShuffle.getInstance().getAccountManager().getAccount(player.getUniqueId());

        Rank rank = account.getRank();

        TotalStats stats = account.getTotalStats();
        if(stats == null) {
            stats = createDefaultTotalStats();
        }

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent("§c§lRank: §f" + rank.getName()
                + "\n§c§lVõite: §f" + stats.getWins())});

        TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&',  account.getPrefixDisplay() + "§7: " + rank.getChatColor() + ""));

        TextComponent text = new TextComponent(e.getMessage());

        msg.setHoverEvent(hoverEvent);
        msg.addExtra(text);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.spigot().sendMessage(msg));

    }

    public TotalStats createDefaultTotalStats() {

        TotalStats totalStats = new TotalStats();
        totalStats.setWins(0);

        return totalStats;
    }
}
