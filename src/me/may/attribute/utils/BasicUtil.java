package me.may.attribute.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BasicUtil {

    private static String version;

    static {
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    /**
     * 取玩家NMS对象也就是EntityPlayer类的实例
     *
     * @param player
     * @return
     */
    public static Object getNMSPlayer(Player player) {
        try {
            //取Player的实现类并调用其方法getHandle
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return entityPlayer;
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return player;
    }

    /**
     * 给一名玩家发送NMS数据包
     *
     * @param player
     * @param packet
     */
    public static void sendPacket(Player player, Object packet) {
        //取NMS实例
        Object entityPlayer = getNMSPlayer(player);
        try {
            //取其playerConnection实例
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            //调用方法sendPacket()
            playerConnection.getClass().getMethod("sendPacket", new Class[]{getNMSClass("Packet")}).invoke(playerConnection, packet);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
    }

    /**
     * 取对应的NMS下的类
     *
     * @param className 类名
     * @return
     */
    public static Class<?> getNMSClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + className);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    /**
     * 取服务器在线玩家
     *
     * @return List<Player>
     */
    public static List<Player> getOnlinePlayers() {
        //实例化两个List用于存放Player和World
        List<Player> players = new ArrayList<Player>();
        List<World> worlds = new ArrayList<World>();
        worlds.addAll(Bukkit.getWorlds());
        //遍历所有的世界
        for (int i = 0; i < worlds.size(); i++) {
            //如果第i个世界的玩家是空的则进行下一次循环
            if (worlds.get(i).getPlayers().isEmpty()) {
                continue;
            } else {
                //不是空的则添加到players集合中
                players.addAll(worlds.get(i).getPlayers());
            }
        }
        return players;
    }
}
