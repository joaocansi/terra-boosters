package me.joaocansi.terraboosters.utils.date;

import me.joaocansi.terraboosters.Main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DateFormatter {
    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(Objects.requireNonNull(Main.getPlugin().getConfig().getString("settings.date_format")));
        return format.format(date);
    }
}
