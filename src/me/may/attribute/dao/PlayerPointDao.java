package me.may.attribute.dao;

import java.util.HashMap;
import java.util.Map;

public class PlayerPointDao {

    private static Map<String, Integer> points = new HashMap<String, Integer>();

    public static Map<String, Integer> getMap() {
        return points;
    }

    public static boolean hasPoint(String name, int point) {
        if (!points.containsKey(name)) {
            return false;
        }
        if (points.get(name) >= point) {
            return true;
        }
        return false;
    }
}
