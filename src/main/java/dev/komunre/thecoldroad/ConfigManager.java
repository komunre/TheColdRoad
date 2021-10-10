package dev.komunre.thecoldroad;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfigEntries;

import java.io.File;

public class ConfigManager {
    public static String configPath = "config/thecoldroad.cfg";
    public static Configuration config;

    public static void init() {
        config = new Configuration(new File(configPath));
    }

    public static void createConfig() {
        try {
            config.load();
            int coldSpeed = 800;
            config.getCategory("cold").get("cold_speed").set(coldSpeed);
        }
        catch (Exception e) {
            TheColdRoad.logger.error("Error in writing config!");
        } finally {
            config.save();
        }
    }

    public static int getInt(String category, String key) {
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                return  config.getInt(key, category, 800, 1, 9999999, "Speed of freeszing");
            }
        } catch (Exception e) {
            TheColdRoad.logger.error("Unable to get integer");
        }
        return 0;
    }
}
