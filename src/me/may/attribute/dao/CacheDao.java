package me.may.attribute.dao;

import me.may.attribute.dto.Attribute;

import java.util.HashMap;
import java.util.Map;

public class CacheDao {

    private static Map<String, Attribute> cache = new HashMap<String, Attribute>();

    public static void putPlayerAttribute(String name, Attribute attribute) {
        cache.put(name, attribute);
    }

    public static Attribute getPlayerAttribute(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        } else {
            return null;
        }
    }
}
