package me.joaocansi.terraboosters.managers;

import lombok.Getter;
import lombok.Setter;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.utils.message.Message;
import me.joaocansi.terraboosters.utils.message.types.Chat;
import me.joaocansi.terraboosters.utils.message.types.Title;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Objects;

@Getter @Setter
public class MessageManager {
    private HashMap<String, Message> messages;

    public MessageManager() {
        messages = new HashMap<>();

        for (String key : Objects.requireNonNull(Main.getPlugin().getConfig().getConfigurationSection("messages")).getKeys(false)) {
            String absolutePath = "messages." + key;
            String message = Main.getPlugin().getConfig().getString(absolutePath);

            assert message != null;
            if (message.contains("{@}"))
                messages.put(key, new Title(ChatColor.translateAlternateColorCodes('&', message)));
            else
                messages.put(key, new Chat(ChatColor.translateAlternateColorCodes('&', message)));
        }
    }

    public Message getMessage(String path) {
        return messages.get(path);
    }
}
