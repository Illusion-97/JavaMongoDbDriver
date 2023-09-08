package fr.dawan.javamongodbdriver.typed;

import fr.dawan.javamongodbdriver.typed.documents.Inventory;
import org.bson.Document;

public record ReplaceDto(Document filter, Inventory replace) {
}
