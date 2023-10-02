package me.may.attribute.utils;

import com.sucy.skill.SkillAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

public class SkillUtil {

    /**
     * 取所有职业
     *
     * @return Set<String> 所有职业的字符串
     */
    public static Set<String> getAllClasses() {
        return SkillAPI.getClasses().keySet();
    }

    /**
     * 取玩家职业
     *
     * @param player 玩家
     * @return 玩家的职业的名字
     */
    public static String getPlayerClass(Player player) {
        if (SkillAPI.getPlayerData(player) == null || SkillAPI.getPlayerData(player).getClass("class") == null
                || SkillAPI.getPlayerData(player).getClass("class").getData() == null) {
            return "default";
        }
        String Class = SkillAPI.getPlayerData(player).getClass("class").getData().getName();
        if (Class == null) {
            return "default";
        }
        return Class;
    }

    /**
     * 取玩家职业
     *
     * @param name 名字
     * @return 玩家的职业的名字
     */
    public static String getPlayerClass(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            return "default";
        }
        return getPlayerClass(player);
    }
}
