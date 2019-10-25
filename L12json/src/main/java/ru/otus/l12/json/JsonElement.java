package ru.otus.l12.json;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JsonElement implements JsonComponent {

    private String key;
    private String value;

    public JsonElement(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public JsonElement(String value) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String asJsonString() {
        String key = isNotBlank(this.key) ? "\"" + this.key + "\":" : "";
        String value = "\"" + this.value + "\"";
        return key + value;
    }
}
