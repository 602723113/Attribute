package me.may.attribute.dao;

import me.may.attribute.dto.Attribute;

import java.util.HashMap;
import java.util.Map;

public class ClassAddtionDao {

    private static Map<String, Attribute> addtion = new HashMap<String, Attribute>();

    public static void putClassAddtionAttribute(String className, Attribute att) {
        addtion.put(className, att);
    }

    public static Attribute getClassAddtionAttribute(String className) {
        Attribute att = new Attribute(null);
        if (addtion.containsKey(className)) {
            att = addtion.get(className);
        }
        return att;
    }
}
