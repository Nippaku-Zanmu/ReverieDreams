package cc.thonly.touhoumod.script;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Slf4j
public class ScriptObjectMapper {

    @SuppressWarnings("unchecked")
    public static <T> T mapToObject(Object source, Class<T> targetClass) {
        if (source instanceof Map map) {
            return (T) mapToObject(map, targetClass);
        }
        throw new IllegalArgumentException("Source must be a Map or script object.");
    }

    public static <T> T mapToObject(Map<String, Object> source, Class<T> targetClass) {
        try {
            T instance = targetClass.getDeclaredConstructor().newInstance();

            for (Field field : targetClass.getDeclaredFields()) {
                field.setAccessible(true);
                String name = field.getName();

                Object raw = findValueForKey(source, name);
                if (raw == null) continue;

                Object value = convertValue(raw, field.getType(), field.getGenericType());
                field.set(instance, value);
            }

            return instance;
        } catch (Exception e) {
            log.error("Mapping failed for class {}: {}", targetClass.getSimpleName(), e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据驼峰或下划线风格寻找字段名
     */
    private static Object findValueForKey(Map<String, Object> source, String fieldName) {
        if (source.containsKey(fieldName)) return source.get(fieldName);
        for (String key : source.keySet()) {
            if (normalizeName(key).equals(normalizeName(fieldName))) {
                return source.get(key);
            }
        }
        return null;
    }

    private static String normalizeName(String s) {
        return s.replaceAll("[-_]", "").toLowerCase();
    }

    @SuppressWarnings("unchecked")
    private static Object convertValue(Object value, Class<?> targetType, Type genericType) {
        if (value == null) return null;

        if (targetType.isInstance(value)) return value;

        if (targetType == String.class) return value.toString();

        if (targetType == int.class || targetType == Integer.class) return Integer.parseInt(value.toString());
        if (targetType == long.class || targetType == Long.class) return Long.parseLong(value.toString());
        if (targetType == float.class || targetType == Float.class) return Float.parseFloat(value.toString());
        if (targetType == double.class || targetType == Double.class) return Double.parseDouble(value.toString());
        if (targetType == boolean.class || targetType == Boolean.class) return Boolean.parseBoolean(value.toString());

        if (targetType.isEnum()) {
            return Enum.valueOf((Class<Enum>) targetType, value.toString());
        }

        if (Collection.class.isAssignableFrom(targetType)) {
            if (!(value instanceof Collection<?> rawList)) return null;
            Type elementType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
            Class<?> elementClass = (Class<?>) elementType;
            Collection<Object> newList = List.class.isAssignableFrom(targetType)
                    ? new ArrayList<>()
                    : new HashSet<>();
            for (Object elem : rawList) {
                newList.add(convertValue(elem, elementClass, elementType));
            }
            return newList;
        }

        if (Map.class.isAssignableFrom(targetType)) {
            if (!(value instanceof Map<?, ?> rawMap)) return null;
            Type valueType = ((ParameterizedType) genericType).getActualTypeArguments()[1];
            Class<?> valueClass = (Class<?>) valueType;

            Map<String, Object> newMap = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                newMap.put(entry.getKey().toString(), convertValue(entry.getValue(), valueClass, valueType));
            }
            return newMap;
        }

        if (value instanceof Map<?, ?> map) {
            return mapToObject((Map<String, Object>) map, targetType);
        }

        return null;
    }
}
