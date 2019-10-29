package ru.otus.l12.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

class JsonParser {

    @SuppressWarnings("unchecked")
    static JsonComponent parseCollection(Field field, Object object) throws IllegalAccessException {
        JsonArray jsonArray = new JsonArray();
        jsonArray.setArrayKey(field.getName());
        ((Collection) field.get(object)).forEach(collectionElement -> jsonArray.getComponents().add(JsonObjectWriter.toJsonObject(collectionElement)));
        return jsonArray;
    }

    static JsonComponent parseArray(Field field, Object object) throws IllegalAccessException {
        JsonArray jsonArray = new JsonArray();
        jsonArray.setArrayKey(field.getName());

        if (field.get(object) instanceof Object[] && !String[].class.isAssignableFrom(field.getType())) {
            Arrays.stream((Object[]) field.get(object)).forEach(objectElement -> jsonArray.getComponents().add(JsonObjectWriter.toJsonObject(objectElement)));
        } else {
            final Object[] boxedArray = new Object[Array.getLength(field.get(object))];
            for (int index = 0; index < boxedArray.length; index++) {
                boxedArray[index] = Array.get(field.get(object), index);
            }
            Arrays.stream(boxedArray).forEach(primitiveElement -> jsonArray.getComponents().add(new JsonElement(primitiveElement.toString())));
        }
        return jsonArray;
    }

    @SuppressWarnings("unchecked")
    static JsonComponent parseMap(Field field, Object object) throws IllegalAccessException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.setObjectName(field.getName());
        ((Map) field.get(object)).forEach((key, value) -> jsonObject.getJsonComponents().add(new JsonElement(key.toString(), value.toString())));
        return jsonObject;
    }

    static JsonComponent parsePrimitiveOrString(Field field, Object object) throws IllegalAccessException {
       return new JsonElement(field.getName(), field.get(object).toString());
    }
}
