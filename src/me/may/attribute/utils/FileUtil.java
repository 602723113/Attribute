package me.may.attribute.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 文件处理工具类
 *
 * @author May_Speed
 */
public class FileUtil {

    /**
     * 保存Yml
     *
     * @param Filec
     * @param file
     */
    public static void saveYml(FileConfiguration Filec, File file) {
        try {
            Filec.save(file);
        } catch (IOException e) {
            System.out.println("错误:" + e.toString());
        }
    }

    /**
     * 读取Yml
     *
     * @param file
     * @return YML
     */
    public static FileConfiguration loadYml(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("错误:" + e.toString());
            }
        }
        FileConfiguration YML = YamlConfiguration.loadConfiguration(file);
        DumperOptions yamlOptions = null;
        try {
            Field f = YamlConfiguration.class.getDeclaredField("yamlOptions");
            f.setAccessible(true);
            yamlOptions = new DumperOptions() {
                public void setAllowUnicode(boolean allowUnicode) {
                    super.setAllowUnicode(false);
                }

                public void setLineBreak(DumperOptions.LineBreak lineBreak) {
                    super.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
                }
            };
            yamlOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
            f.set(YML, yamlOptions);
        } catch (ReflectiveOperationException ex) {
            System.out.println("错误:" + ex.toString());
        }
        return YML;
    }
}
