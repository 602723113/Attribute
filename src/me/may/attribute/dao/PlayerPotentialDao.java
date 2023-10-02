package me.may.attribute.dao;

import me.may.attribute.dto.Attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerPotentialDao {

    private static Map<String, Attribute> points = new ConcurrentHashMap<String, Attribute>();

    public static Map<String, Attribute> getMap() {
        return points;
    }
}
