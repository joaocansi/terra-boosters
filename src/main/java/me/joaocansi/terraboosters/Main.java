package me.joaocansi.terraboosters;

import lombok.AccessLevel;
import lombok.Getter;
import me.joaocansi.terraboosters.commands.BoostersCommand;
import me.joaocansi.terraboosters.database.BoosterDatabase;
import me.joaocansi.terraboosters.listeners.BoosterExperienceListener;
import me.joaocansi.terraboosters.listeners.BoosterMcMMOExperienceListener;
import me.joaocansi.terraboosters.listeners.BoosterInteractListener;
import me.joaocansi.terraboosters.managers.BoosterManager;
import me.joaocansi.terraboosters.managers.BoosterProductManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {
    @Getter(AccessLevel.PUBLIC)
    private static JavaPlugin plugin;

    @Getter(AccessLevel.PUBLIC)
    private static BoosterManager boosterManager;

    @Getter(AccessLevel.PUBLIC)
    private static BoosterProductManager boosterProductManager;

    @Getter
    private BoosterDatabase boosterDatabase;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;

        boosterDatabase = new BoosterDatabase();
        boosterProductManager = new BoosterProductManager();
        boosterManager = new BoosterManager(boosterDatabase);

        Objects.requireNonNull(getCommand("boosters")).setExecutor(new BoostersCommand());
        getServer().getPluginManager().registerEvents(new BoosterInteractListener(), this);
        getServer().getPluginManager().registerEvents(new BoosterMcMMOExperienceListener(), this);
        getServer().getPluginManager().registerEvents(new BoosterExperienceListener(), this);
    }
    @Override
    public void onDisable() {

    }
}
