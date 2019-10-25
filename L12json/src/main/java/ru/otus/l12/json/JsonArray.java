package ru.otus.l12.json;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class JsonArray implements JsonComponent {

    private String arrayKey;
    private List<JsonComponent> components = new ArrayList<>();

    public String getArrayKey() {
        return arrayKey;
    }

    public void setArrayKey(String arrayKey) {
        this.arrayKey = arrayKey;
    }

    public List<JsonComponent> getComponents() {
        return components;
    }

    public void setComponents(List<JsonComponent> components) {
        this.components = components;
    }

    @Override
    public String asJsonString() {
        return "\"" + this.arrayKey + "\":[" + this.components.stream().map(JsonComponent::asJsonString).collect(joining(",")) + "]";
    }
}
