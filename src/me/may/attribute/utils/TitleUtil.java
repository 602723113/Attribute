package me.may.attribute.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class TitleUtil {
    /**
     * 给一个玩家发送Title信息 1.8+
     *
     * @param player   发送的玩家
     * @param fadeIn   淡入时间
     * @param stay     停留时间
     * @param fadeOut  淡出时间
     * @param title    主标题
     * @param subtitle 副标题
     */
    @SuppressWarnings("rawtypes")
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        try {
            if (title != null) { //要发送的title
                title = ChatColor.translateAlternateColorCodes('&', title); //支持&颜色代码
                title = title.replaceAll("%player%", player.getDisplayName());
                Object enumTitle = BasicUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                Object chatTitle = BasicUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\":\"" + title + "\"}"});
                Constructor titleConstructor = BasicUtil.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{BasicUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], BasicUtil.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                Object titlePacket = titleConstructor.newInstance(new Object[]{enumTitle, chatTitle, fadeIn, stay, fadeOut});
                BasicUtil.sendPacket(player, titlePacket);
            }
            if (subtitle != null) { //要发送的子title
                subtitle = ChatColor.translateAlternateColorCodes('&', subtitle); //支持&颜色代码
                subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
                Object enumSubtitle = BasicUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                Object chatSubtitle = BasicUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\":\"" + subtitle + "\"}"});
                Constructor subtitleConstructor = BasicUtil.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{BasicUtil.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], BasicUtil.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                Object subtitlePacket = subtitleConstructor.newInstance(new Object[]{enumSubtitle, chatSubtitle, fadeIn, stay, fadeOut});
                BasicUtil.sendPacket(player, subtitlePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
