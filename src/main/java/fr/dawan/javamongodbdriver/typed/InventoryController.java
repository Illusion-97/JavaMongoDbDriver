package fr.dawan.javamongodbdriver.typed;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import fr.dawan.javamongodbdriver.conf.ConnexionManager;
import fr.dawan.javamongodbdriver.typed.documents.Inventory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
