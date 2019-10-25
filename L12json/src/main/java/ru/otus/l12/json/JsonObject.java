package ru.otus.l12.json;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class JsonObject implements JsonComponent {

    private String objectName;

    private List<JsonComponent> jsonComponents = new ArrayList<>();

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<JsonComponent> getJsonComponents() {
        return jsonComponents;
    }

    public void setJsonComponents(List<JsonComponent> jsonComponents) {
        this.jsonComponents = jsonComponents;
    }

    @Override
    public String asJsonString() {
        String components = "{" + this.jsonComponents.stream().map(JsonComponent::asJsonString).collect(joining(",")) + "}";
        return isNotBlank(objectName) ? "\"" + objectName + "\":" + components : components;
    }
}
