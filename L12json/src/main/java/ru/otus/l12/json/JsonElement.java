package ru.otus.l12.json;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;

import static org.apache.commons.lang3.ClassUtils.isPrimitiveWrapper;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JsonElement implements JsonComponent {

    private String key;
    private Object value;

    public JsonElement(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public JsonElement(Object value) {
        this.value = value;
    }

    public JsonElement() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String asJsonString() {
        Object value;
        if (isPrimitiveWrapper(this.value.getClass()) && !Character.class.isAssignableFrom(this.value.getClass())) {
            value = this.value;
        } else if (this.value.getClass().isArray() || Collection.class.isAssignableFrom(this.value.getClass())) {
            value = ArrayUtils.toString(this.value).replace("{", "[").replace("}", "]");
        } else
            value = "\"" + this.value + "\"";

        return (isNotBlank(this.key) ? "\"" + this.key + "\":" : "") + value;
    }
}
