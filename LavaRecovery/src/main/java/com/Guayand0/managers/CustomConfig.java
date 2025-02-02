//package com.Guayand0.managers;
//
//import com.Guayand0.LavaRecovery;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.configuration.file.YamlConfiguration;
//import java.io.File;
//
//public class CustomConfig {
//
//    private final LavaRecovery plugin;
//    private final String fileName;
//    private FileConfiguration fileConfiguration = null;
//    private File file = null;
//    private final String folderName;
//
//    public CustomConfig(String fileName, String folderName, LavaRecovery plugin) {
//        this.fileName = fileName;
//        this.folderName = folderName;
//        this.plugin = plugin;
//    }
//
//    public FileConfiguration getConfig() {
//        if (fileConfiguration == null) {
//            reloadConfig();
//        }
//        return fileConfiguration;
//    }
//
//    public void reloadConfig() {
//        if (fileConfiguration == null) {
//            if (folderName != null) {
//                file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
//            } else {
//                file = new File(plugin.getDataFolder(), fileName);
//            }
//
//        }
//        fileConfiguration = YamlConfiguration.loadConfiguration(file);
//
//        if (file != null) {
//            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
//            fileConfiguration.setDefaults(defConfig);
//        }
//    }
//
//    /*public String getPath() {
//        return this.fileName;
//    }
//
//    public void registerConfig() {
//        if (folderName != null) {
//            file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
//        } else {
//            file = new File(plugin.getDataFolder(), fileName);
//        }
//
//        if (!file.exists()) {
//            if (folderName != null) {
//                plugin.saveResource(folderName + File.separator + fileName, false);
//            } else {
//                plugin.saveResource(fileName, false);
//            }
//        }
//
//        fileConfiguration = new YamlConfiguration();
//        try {
//            fileConfiguration.load(file);
//        } catch (IOException e) {
//            Bukkit.getConsoleSender().sendMessage(MU.getColoredText("&c[Error] Failed to load the configuration due to an I/O issue."));
//            Bukkit.getConsoleSender().sendMessage(MU.getColoredText("&cFile: " + file.getAbsolutePath()));
//            e.printStackTrace();
//        } catch (InvalidConfigurationException e) {
//            Bukkit.getConsoleSender().sendMessage(MU.getColoredText("&c[Error] The configuration file has an invalid format."));
//            Bukkit.getConsoleSender().sendMessage(MU.getColoredText("&cCheck the YAML syntax in: " + file.getAbsolutePath()));
//            e.printStackTrace();
//        }
//    }
//
//    public void saveConfig() {
//        try {
//            fileConfiguration.save(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }*/
//}