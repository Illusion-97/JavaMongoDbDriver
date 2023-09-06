package fr.dawan.javamongodbdriver.typed;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import fr.dawan.javamongodbdriver.conf.ConnexionManager;
import fr.dawan.javamongodbdriver.typed.documents.Inventory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@Tag(name = "TYPED")
public class InventoryController {
    private final MongoCollection<Inventory> collection;

    public InventoryController() {
        collection = ConnexionManager.getClient().getDatabase("db")
                .withCodecRegistry(CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(
                                PojoCodecProvider.builder()
                                        .automatic(true)
                                        .build()
                        )
                ))
                .getCollection("inventory", Inventory.class);
    }

    @PostMapping("insertOne")
    public InsertOneResult insertOne(@RequestBody Inventory inventory) {
        // db.inventory.insertOne({})
        return collection.insertOne(inventory);
    }

    @PostMapping("/insertMany")
    public InsertManyResult insertMany(@RequestBody List<Inventory> inventories) {
        // db.inventory.insertMany([{},{}])
        return collection.insertMany(inventories);
    }

    @GetMapping
    public List<Inventory> findAll() {
        // var result = db.inventory.find({})
        // result.forEach(printjson)
        /*List<Inventory> result = new ArrayList<>();
        //collection.find().forEach(inventory -> result.add(inventory));
        collection.find().forEach(result::add);
        return result;*/
        return collection.find().into(new ArrayList<>());
    }

    @GetMapping("/allPaginated")
    public List<Inventory> findAllPaginated(@RequestParam int page, @RequestParam int size) {
        // db.inventory.find({}).skip(page * size).limit(size)
        return collection.find().skip(page * size).limit(size).into(new ArrayList<>());
    }

    @GetMapping("findByItem/{item}")
    public List<Inventory> findByItem(@PathVariable String item) {
        // db.inventory.find({key: value})
        Document document = new Document();
        document.append("item",item);
        return collection.find(document).into(new ArrayList<>());
    }
}
