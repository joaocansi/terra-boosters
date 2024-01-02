package me.joaocansi.terraboosters.utils.skill;

import me.joaocansi.terraboosters.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class SkillTranslator {
    public static String translate(String name) {
        FileConfiguration config = Main.getPlugin().getConfig();
        ConfigurationSection translations = config.getConfigurationSection("skill_translator");

        if (translations == null || !translations.contains(name))
            return name;

        return config.getString("skill_translator." + name);
    }
}
