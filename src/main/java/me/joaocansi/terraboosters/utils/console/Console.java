package me.joaocansi.terraboosters.utils.console;

import me.joaocansi.terraboosters.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public final class Console {
    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static void message(String message, String color) {
        String prefix = "[TerraBoosters]";
        String text = color + " " + message;
        console.sendMessage(ChatColor.translateAlternateColorCodes('&', text));
    }

    public static void info(String message) {
        message(message, "&a");
    }

    public static void warning(String message) {
        message(message, "&6");
    }

    public static void error(String message) {
        message(message, "&c");
    }

    public static void disablePlugin() {
        Main.getPlugin().getServer().getPluginManager().disablePlugin(Main.getPlugin());
    }
}
