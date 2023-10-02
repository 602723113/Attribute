package me.may.attribute.dao;

import me.may.attribute.api.AttributeAPI;
import me.may.attribute.dto.Attribute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLDao {

    public static void loadPlayerDatas() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Attribute att = null;
        String name = null;
        String sql = "select * from att_potential";
        try {
            conn = BasicDao.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString(2); // player_name
                att = new Attribute(name
                        , 0
                        , 0
                        , 0
                        , 0
                        , 0
                        , 0
                        , 0
                        , 0
                        , rs.getDouble(4) // strenth
                        , rs.getDouble(5) // toughness
                        , rs.getDouble(6) // ability
                        , rs.getDouble(7) // body
                        , rs.getDouble(8)); // physical_power
                PlayerPotentialDao.getMap().put(name, att);
                PlayerPointDao.getMap().put(name, rs.getInt(3));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPlayerData(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select player_name from att_potential where player_name = '" + name + "'";

        try {
            conn = BasicDao.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                ps.close();
                return true;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return false;

    }

    public static void initialPlayerPotential(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into att_potential values(null, '" + name + "', '0', '0', '0', '0', '0', '0')";
        try {
            conn = BasicDao.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPlayerPoint(String name, int amount) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE att_potential SET point = point + " + amount + " WHERE player_name = '" + name + "'";
        try {
            conn = BasicDao.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PlayerPointDao.getMap().put(name, PlayerPointDao.getMap().get(name) + amount);
    }

    public static void clearPlayerPotential(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE att_potential SET point = 0, strenth = 0, toughness = 0, ability = 0, body = 0, physical_power = 0 WHERE player_name = '" + name + "'";
        try {
            conn = BasicDao.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PlayerPointDao.getMap().put(name, 0);
        PlayerPotentialDao.getMap().put(name, new Attribute(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
    }

    public static void resetPlayerPotential(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update att_potential set " +
                "point = point + strenth + toughness + ability + body + physical_power, strenth = 0, toughness = 0, ability = 0, body = 0, physical_power = 0 WHERE player_name = '" + name + "'";
        try {
            conn = BasicDao.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PlayerPointDao.getMap().put(name, (int) (PlayerPointDao.getMap().get(name) + AttributeAPI.getPotentialAttributeAmount(PlayerPotentialDao.getMap().get(name))));
        PlayerPotentialDao.getMap().put(name, new Attribute(name, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
    }

    public static void updatePlayerPotential(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        int point = PlayerPointDao.getMap().get(name);
        Attribute att = PlayerPotentialDao.getMap().get(name);
        String sql = "update att_potential set " +
                "point = " + point +
                ", strenth = " + att.getStrenth() +
                ", toughness = " + att.getToughness() +
                ", ability = " + att.getAbility() +
                ", body = " + att.getBody() +
                ", physical_power = " + att.getPhysicalPower() +
                " WHERE player_name = '" + name + "'";
        try {
            conn = BasicDao.getConnection();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
