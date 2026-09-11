package at.woodexplsoive.sleep_percentage.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class SleepConfig {
    public static int sleepPercentage = 50;
    public static int checkIntervall = 20;

    private static Configuration cfg;

    public static void load(File file) {
        cfg = new Configuration(file);
        cfg.load();

        sleepPercentage = cfg.getInt(
                "sleepPercentage",
                "general",
                50,
                1,
                100,
                "Percentage of players that have to sleep for the night to skip. (1 - 100)"
        );

        checkIntervall = cfg.getInt(
                "checkIntervall",
                "general",
                20,
                1,
                Integer.MAX_VALUE,
                "Check Intervall in Ticks"
        );

        save();
    }

    public static void save() {
        if (cfg.hasChanged()) cfg.save();
    }
}