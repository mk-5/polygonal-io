package io.polygonal.verifytask;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ReflectionHelper {
    private ReflectionHelper() {
    }

    @SneakyThrows
    static <T> T merge(T src, T target) {
        Field[] fields = src.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(src);
            try {
                Field targetField = target.getClass().getDeclaredField(field.getName());
                targetField.setAccessible(true);
                targetField.set(target, value);
            } catch (NoSuchFieldException e) {
            }
        }
        return target;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    static void set(Object target, String property, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(property);
            field.setAccessible(true);
            if (value instanceof List && field.getType().isAssignableFrom(Set.class)) {
                value = new HashSet<>((List) value);
            }
            field.set(target, value);
        } catch (NoSuchFieldException e) {
        }
    }

    @SneakyThrows
    static void set(Object target, Map<String, Object> properties) {
        properties.forEach((key, value) -> {
            set(target, key, value);
        });
    }
}
