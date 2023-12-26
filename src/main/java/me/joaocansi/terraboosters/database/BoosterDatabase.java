package me.joaocansi.terraboosters.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.AccessLevel;
import lombok.Getter;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.entities.Booster;
import me.joaocansi.terraboosters.utils.console.Console;

import java.sql.SQLException;

public final class BoosterDatabase {
    private Dao<Booster, Integer> dao;

    public BoosterDatabase() {
        try {
            String url = "jdbc:sqlite:" + Main.getPlugin().getDataFolder() + "/storage.db";
            ConnectionSource connection = new JdbcConnectionSource(url);
            TableUtils.createTableIfNotExists(connection, Booster.class);
            dao = DaoManager.createDao(connection, Booster.class);
        } catch (SQLException e) {
            Console.error("Not able to connect to the database. Here's the error: \n" + e.getMessage());
        }
    }

    public boolean insertBooster(Booster booster) {
        try {
            dao.create(booster);
            return true;
        } catch (SQLException e) {
            Console.warning("Not able to insert booster in database. Here's the error: \n" + e.getMessage());
            return false;
        }
    }
}
