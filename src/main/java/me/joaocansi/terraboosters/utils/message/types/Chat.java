package me.joaocansi.terraboosters.utils.message.types;

import me.joaocansi.terraboosters.utils.message.Message;
import org.bukkit.command.CommandSender;

import java.util.function.Function;

public class Chat extends Message {
    public Chat(String message) {
        super(message);
    }

    @Override
    public void send(CommandSender entity, Function<String, String> t) {
        String newMessage = t.apply(getMessage());
        entity.sendMessage(newMessage);
    }

    @Override
    public void send(CommandSender entity) {
        entity.sendMessage(getMessage());
    }
}
