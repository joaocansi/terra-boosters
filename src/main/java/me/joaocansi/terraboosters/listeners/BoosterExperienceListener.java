package me.joaocansi.terraboosters.listeners;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.entities.Booster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class BoosterExperienceListener implements Listener {
    @EventHandler
    public void onBoosterMinecraftExperience(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();
        Booster booster = Main.getBoosterManager().getBoosterByPlayerSkill(p, "MINECRAFT_XP");

        if (booster == null)
            return;

        float multiplication = Main.getBoosterProductManager().getBoosterMultiplicationById(booster.getBoosterId());
        e.setAmount((int) (e.getAmount()*multiplication));
    }
}
