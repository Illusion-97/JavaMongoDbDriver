package fr.dawan.javamongodbdriver.untyped;

import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

// Importe statiquement une, ou toutes les méthodes d'une classe afin de se dispenser de l'appeler explicitement au besoin
import java.util.ArrayList;
import java.util.List;

import static fr.dawan.javamongodbdriver.conf.ConnexionManager.*;


@RestController
@RequestMapping("/api/{collection}")
@Tag(name = "UNTYPED")
public class MongoController {

    @PostMapping("/insert")
    public InsertOneResult create(@PathVariable String collection, @RequestBody Document toInsert) {
        return getCollection(collection).insertOne(toInsert);
    }

    /*@PostMapping("/insertMany")
    public InsertManyResult create(@PathVariable String collection, @RequestBody List<Document> toInsert) {
        return getCollection(collection).insertMany(toInsert);
    }*/

    @PostMapping("/find")
    public List<Document> find(@PathVariable String collection, @RequestBody MongoDto mongoDto) {
        return getCollection(collection)
                .find(mongoDto.filter())
                .projection(mongoDto.projection())
                .sort(mongoDto.sort())
                .skip(mongoDto.page() * mongoDto.size())
                .limit(mongoDto.size())
                .into(new ArrayList<>());
    }

    /*@PutMapping("/updateOne")
    public UpdateResult updateOne(@PathVariable String collection, @RequestBody MongoDto mongoDto) {
        return getCollection(collection).updateOne(mongoDto.filter(), mongoDto.subject());
    }*/

    /*@PutMapping("/updateMany")
    public UpdateResult updateMany(@PathVariable String collection, @RequestBody MongoDto mongoDto) {
        return getCollection(collection).updateMany(mongoDto.filter(), mongoDto.subject());
    }*/

    /*@PutMapping("/replaceOne")
    public UpdateResult replaceOne(@PathVariable String collection, @RequestBody MongoDto mongoDto) {
        return getCollection(collection)
                .replaceOne(
                        mongoDto.filter(),
                        mongoDto.subject(),
                        new ReplaceOptions().upsert(mongoDto.upsert()));
    }*/

    // Si vous travaillez avec le driver plutôt que spring data, cette methode sera la plus utilisée pour les mises à jour
    @PutMapping("/findOneAndReplace")
    public Document findOneAndReplace(@PathVariable String collection, @RequestBody MongoDto mongoDto) {
        return getCollection(collection)
                .findOneAndReplace(
                        mongoDto.filter(),
                        mongoDto.subject(),
                        new FindOneAndReplaceOptions()
                                .projection(mongoDto.projection())
                                .sort(mongoDto.sort())
                                .upsert(mongoDto.upsert()));
    }

    /*@DeleteMapping("/deleteOne")
    public DeleteResult deleteOne(@PathVariable String collection, @RequestBody Document filter) {
        return getCollection(collection).deleteOne(filter);
    }*/

    /*@DeleteMapping("/deleteMany")
    public DeleteResult deleteMany(@PathVariable String collection, @RequestBody Document filter) {
        return getCollection(collection).deleteMany(filter);
    }*/

    @DeleteMapping("/findOneAndDelete")
    public Document findOneAndDelete(@PathVariable String collection, @RequestBody MongoDto mongoDto) {
        return getCollection(collection)
                .findOneAndDelete(
                        mongoDto.filter(),
                        new FindOneAndDeleteOptions()
                                .projection(mongoDto.projection())
                                .sort(mongoDto.sort()));

    }

}
