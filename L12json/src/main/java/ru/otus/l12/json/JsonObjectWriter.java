package ru.otus.l12.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class JsonObjectWriter {

    @SuppressWarnings("unchecked")
    public static JsonObject toJsonObject(Object object) {
        JsonObject jsonObject = new JsonObject();
        List<JsonComponent> componentList = new ArrayList<>();
        try {
            parseField(componentList, object);
            jsonObject.setJsonComponents(componentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static JsonComponent createSubObject(String objectName, Object object) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.setObjectName(objectName);
        List<JsonComponent> componentList = new ArrayList<>();
        try {
            parseField(componentList, object);
            jsonObject.setJsonComponents(componentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static void parseField(List<JsonComponent> componentList, Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (isEmpty(field.get(object)))
                continue;

            if (Collection.class.isAssignableFrom(field.getType())) {
                componentList.add(JsonParser.parseCollection(field, object));
            } else if (field.getType().isArray()) {
                componentList.add(JsonParser.parseArray(field, object));
            } else if (Map.class.isAssignableFrom(field.getType())) {
                componentList.add(JsonParser.parseMap(field, object));
            } else if (field.getType().isPrimitive() || String.class.isAssignableFrom(field.getType())) {
                componentList.add(JsonParser.parsePrimitiveOrString(field, object));
            } else {
                componentList.add(createSubObject(field.getName(), field.get(object)));
            }
        }
    }
}