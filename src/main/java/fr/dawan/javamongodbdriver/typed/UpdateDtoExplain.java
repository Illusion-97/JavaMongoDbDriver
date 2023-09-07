package fr.dawan.javamongodbdriver.typed;

import org.bson.Document;

public class UpdateDtoExplain {
    private final Document filter;
    private final Document update;

    public UpdateDtoExplain(Document filter, Document update) {
        this.filter = filter;
        this.update = update;
    }

    public Document filter() {
        return filter;
    }

    public Document update() {
        return update;
    }
}
