package me.joaocansi.terraboosters.utils.message;

import org.bukkit.command.CommandSender;

import java.util.function.Function;

public interface IMessage {
    void send(CommandSender entity, Function<String, String> t);
    void send(CommandSender entity);
}
