package fr.dawan.javamongodbdriver.typed;

import org.bson.Document;

import java.util.List;

public record UpdateDto(Document filter, List<Document> update) { }
