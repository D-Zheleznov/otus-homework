package ru.otus.l17.service.entity;

import java.util.UUID;

abstract class BaseEntity {

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
