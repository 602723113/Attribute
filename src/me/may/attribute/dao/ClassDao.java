package me.may.attribute.dao;

import me.may.attribute.dto.Attribute;

import java.util.HashMap;
import java.util.Map;

public class ClassDao {

    private static Map<String, Attribute> classDefault = new HashMap<String, Attribute>();

    public static Map<String, Attribute> getMap() {
        return classDefault;
    }

    public static void putClassDefaultAttribute(String name, Attribute attribute) {
        classDefault.put(name, attribute);
    }
}
