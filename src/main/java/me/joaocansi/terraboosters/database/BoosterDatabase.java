package me.joaocansi.terraboosters.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import me.joaocansi.terraboosters.Main;
import me.joaocansi.terraboosters.entities.Booster;
import me.joaocansi.terraboosters.utils.console.Console;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

    public void disconnect() {
        Date date = new Date();
        try {
            DeleteBuilder<Booster, Integer> deleteBuilder = dao.deleteBuilder();
            deleteBuilder
                    .where()
                    .gt("expiresIn", date.getTime());
            dao.delete(deleteBuilder.prepare());
        } catch (SQLException e) {
            Console.error("Not able to delete boosters in database. Here's the error: \n" + e.getMessage());
        }
    }
}
