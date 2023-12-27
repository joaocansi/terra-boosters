package me.joaocansi.terraboosters.utils.message.types;

import me.joaocansi.terraboosters.utils.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class Title extends Message {
    public Title(String message) {
        super(message);
    }

    @Override
    public void send(CommandSender entity, Function<String, String> t) {
        String newMessage = t.apply(getMessage());
        String[] splitMessage = newMessage.split("\\{@}");

        if (!(entity instanceof Player)) {
            entity.sendMessage(splitMessage[0] + " " + splitMessage[1]);
            return;
        }

        Player player = (Player) entity;
        player.sendTitle(splitMessage[0], splitMessage[1], 10, 70, 20);
    }

    @Override
    public void send(CommandSender entity) {
        String[] splitMessage = getMessage().split("\\{@}");
        if (!(entity instanceof Player)) {
            entity.sendMessage(splitMessage[0] + " " + splitMessage[1]);
            return;
        }

        Player player = (Player) entity;
        player.sendTitle(splitMessage[0], splitMessage[1], 10, 70, 20);
    }
}
