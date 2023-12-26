package me.joaocansi.terraboosters.commands;

import lombok.NonNull;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.utils.console.Console;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command cmd, @NonNull String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("give") && sender.hasPermission("terraboosters.admin")) {
            if (args.length != 4) {
                sender.sendMessage("/boosters give <player> <booster> <amount>");
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            String boosterId = args[2];
            int amount;

            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage("/boosters give <player> <booster> <amount>");
                return false;
            }

            return Main.getBoosterProductManager().giveBooster(sender, player, boosterId, amount);
        }

        if (!(sender instanceof Player)) {
            Console.warning("This command must be executed in-game.");
            return false;
        }

        Player p = (Player)sender;
        return Main.getBoosterManager().viewPlayerBoosters(p);
    }
}
