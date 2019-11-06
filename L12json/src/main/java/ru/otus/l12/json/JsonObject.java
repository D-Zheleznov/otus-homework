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
        StringBuilder json = new StringBuilder();
        if (isNotBlank(this.objectName))
            json.append("\"").append(objectName).append("\":");

        if (!this.jsonComponents.isEmpty()) {
            if (this.jsonComponents.get(0) == null) {
                return "null";
            } else if (this.jsonComponents.size() == 1) {
                json.append(this.jsonComponents.get(0).asJsonString());
            } else
                json.append("{").append(this.jsonComponents.stream().map(JsonComponent::asJsonString).collect(joining(","))).append("}");
        } else
            json.append("{}");

        return json.toString();
    }
}
