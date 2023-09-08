package fr.dawan.javamongodbdriver.untyped;

import org.bson.Document;

public record MongoDto (Document filter, Document subject, Document projection, Document sort, int page, int size, boolean upsert) {
}
