package me.joaocansi.terraboosters.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.entities.Booster;
import me.joaocansi.terraboosters.utils.console.Console;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public final class BoosterDatabase {
    private Dao<Booster, Integer> dao;

    public BoosterDatabase() {
        try {
            connect();
        } catch (SQLException e) {
            Console.error("Not able to connect to the database. Here's the error: \n" + e.getMessage());
        }
    }

    public boolean insert(Booster booster) {
        try {
            dao.create(booster);
            return true;
        } catch (SQLException e) {
            Console.error("Not able to insert booster in database. Here's the error: \n" + e.getMessage());
            return false;
        }
    }

    public List<Booster> getAll() {
        try {
            return this.dao.queryForAll();
        } catch (SQLException e) {
            Console.error("Not able to get all boosters from database. Here's the error: \n" + e.getMessage());
            return null;
        }
    }

    public void deleteById(int id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            Console.error("Not able to delete booster in database. Here's the error: \n" + e.getMessage());
        }
    }

    public void connect() throws SQLException {
        FileConfiguration config = Main.getPlugin().getConfig();
        String type = config.getString("settings.storage.type");
        ConnectionSource connection;

        if (type == null || !type.equalsIgnoreCase("MySQL")) {
            String url = "jdbc:sqlite:" + Main.getPlugin().getDataFolder() + "/storage.db";
            connection = new JdbcConnectionSource(url);
        } else {
            String username = config.getString("settings.storage.username");
            String password = config.getString("settings.storage.password");
            String database = config.getString("settings.storage.database");
            String host = config.getString("settings.storage.host");
            String port = config.getString("settings.storage.port");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            connection = new JdbcConnectionSource(url, username, password);
        }

        TableUtils.createTableIfNotExists(connection, Booster.class);
        dao = DaoManager.createDao(connection, Booster.class);
    }

    public void disconnect() {
        try {
            for (Booster b : Main.getBoosterManager().getBoosters().values())
                if (b.hasExpired())
                    dao.delete(b);
            dao.getConnectionSource().close();
        } catch (Exception e) {
            Console.error("Not able to delete boosters in database. Here's the error: \n" + e.getMessage());
        }
    }
}
