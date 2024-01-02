package me.joaocansi.terraboosters.commands;

import lombok.NonNull;
import me.joaocansi.terraboosters.Main;
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
                Main.getMessageManager().getMessage("command_booster_give_syntax").send(sender);
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            String boosterId = args[2];
            int amount;

            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                Main.getMessageManager().getMessage("command_booster_give_syntax").send(sender);
                return false;
            }

            return Main.getBoosterProductManager().giveBooster(sender, player, boosterId, amount);
        }

        if (!(sender instanceof Player)) {
            Main.getMessageManager().getMessage("command_in_game_only").send(sender);
            return false;
        }

        Player p = (Player)sender;
        return Main.getBoosterManager().viewPlayerBoosters(p);
    }
}
