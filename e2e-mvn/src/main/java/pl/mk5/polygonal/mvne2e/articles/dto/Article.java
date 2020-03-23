package pl.mk5.polygonal.mvne2e.articles.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;

public class Article {
    private final UUID id;

    @JsonCreator
    public Article(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
