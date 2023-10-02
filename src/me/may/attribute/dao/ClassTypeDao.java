package me.may.attribute.dao;

import com.google.common.collect.Maps;
import me.may.attribute.dto.ClassAttributeEnum;

import java.util.Map;

public class ClassTypeDao {

    private static Map<String, ClassAttributeEnum> classToType = Maps.newHashMap();
    private static Map<ClassAttributeEnum, Double> type = Maps.newHashMap();

    public static void putClassType(String className, ClassAttributeEnum type) {
        classToType.put(className, type);
    }

    public static ClassAttributeEnum getClassType(String className) {
        if (!classToType.containsKey(className)) {
            return null;
        }
        return classToType.get(className);
    }

    public static Map<ClassAttributeEnum, Double> getMap() {
        return type;
    }

    public static double getAddcitionByString(String enumName) {
        if (!type.containsKey(ClassAttributeEnum.valueOf(enumName))) {
            return 0D;
        }
        return type.get(ClassAttributeEnum.valueOf(enumName));
    }

}
