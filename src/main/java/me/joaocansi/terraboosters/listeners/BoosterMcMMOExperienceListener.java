package me.joaocansi.terraboosters.listeners;

import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.entities.Booster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BoosterMcMMOExperienceListener implements Listener {
    @EventHandler
    public void onBoosterMcMMOExperience(McMMOPlayerXpGainEvent e) {
        Player p = e.getPlayer();
        Booster booster = Main.getBoosterManager().getBoosterByPlayerSkill(p, e.getSkill().toString());

        if (booster == null)
            return;

        p.sendMessage(e.getRawXpGained() + "");
        float multiplication = Main.getBoosterProductManager().getBoosterMultiplicationById(booster.getBoosterId());
        e.setRawXpGained(e.getRawXpGained()*multiplication);
        p.sendMessage(e.getRawXpGained() + "");
    }
}
