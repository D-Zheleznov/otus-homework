package ru.otus.l12.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class JsonObjectWriter {

    @SuppressWarnings("unchecked")
    public static JsonObject toJsonObject(Object object) {
        JsonObject jsonObject = new JsonObject();
        List<JsonComponent> componentList = new ArrayList<>();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (isEmpty(field.get(object)))
                    continue;


                if (Collection.class.isAssignableFrom(field.getType())) {
//                    JsonArray jsonArray = new JsonArray();
//                    jsonArray.setArrayKey(field.getName());
//                    ((Collection) field.get(object)).forEach(collectionElement -> jsonArray.getComponents().add(toJsonObject(collectionElement)));
                    componentList.add(JsonParser.parseCollection(field, object));

                } else if (field.getType().isArray()) {
//                    JsonArray jsonArray = new JsonArray();
//                    jsonArray.setArrayKey(field.getName());
//
//                    if (field.get(object) instanceof Object[] && !String[].class.isAssignableFrom(field.getType())) {
//                        Arrays.stream((Object[]) field.get(object)).forEach(objectElement -> jsonArray.getComponents().add(toJsonObject(objectElement)));
//                    } else {
//                        final Object[] boxedArray = new Object[Array.getLength(field.get(object))];
//                        for (int index = 0; index < boxedArray.length; index++) {
//                            boxedArray[index] = Array.get(field.get(object), index);
//                        }
//                        Arrays.stream(boxedArray).forEach(primitiveElement -> jsonArray.getComponents().add(new JsonElement(primitiveElement.toString())));
//                    }
                    componentList.add(JsonParser.parseArray(field, object));

                } else if (Map.class.isAssignableFrom(field.getType())) {
//                    JsonObject jsonObject1 = new JsonObject();
//                    JsonArray jsonArray = new JsonArray();
//                    jsonArray.setArrayKey(field.getName());
//                    ((Map) field.get(object)).forEach((key, value) -> jsonArray.getComponents().add(new JsonElement(key.toString(), value.toString())));
                    componentList.add(JsonParser.parseMap(field, object));



                } else if (field.getType().isPrimitive() || String.class.isAssignableFrom(field.getType())) {

//                    componentList.add(new JsonElement(field.getName(), field.get(object).toString()));
                    componentList.add(JsonParser.parsePrimitiveOrString(field, object));
                } else {
                    componentList.add(createSubObject(field.getName(), field.get(object)));
                }
            }
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
            jsonObject.setJsonComponents(componentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}