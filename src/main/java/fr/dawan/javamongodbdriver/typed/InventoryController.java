package fr.dawan.javamongodbdriver.typed;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import fr.dawan.javamongodbdriver.conf.ConnexionManager;
import fr.dawan.javamongodbdriver.typed.documents.Inventory;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @GetMapping("findByStatus/{status}/orByStockLt/{qte}")
    public List<Inventory> findByStatusOrStockLessThan(@PathVariable int qte, @PathVariable String status) {
        // db.inventory.find({$or : [{status: status}, {"instock.qty" : {$lt: qte}}]})


        return collection
                .find(Filters.or(
                        Filters.eq("status",status),
                        Filters.lt("instock.qty",qte)))
                .into(new ArrayList<>());
    }

    @PostMapping("findFiltered")
    public List<Inventory> findAllFiltered(@RequestBody Document filter, @RequestParam int page, @RequestParam int size) {
        return collection.find(filter)
                .skip(page * size)
                .limit(size)
                .into(new ArrayList<>());
    }

    @PostMapping("findFirstOne")
    public Inventory findFirstOne(@RequestBody Document filter) {
        // db.inventory.findOne({})
        return collection.find(filter).first();
    }

    @PostMapping("findAllSortedByStock")
    public List<Inventory> findAllSortedSyStock(@RequestBody Document filter) {
        return collection.find(filter).sort(Sorts.ascending("instock.qty")).into(new ArrayList<>());
    }

    @GetMapping("/allItems")
    public List<Inventory> findAllItems() {
        return collection.find().projection(Projections.include("item")).into(new ArrayList<>());
    }

    @GetMapping("/allStockWithoutId")
    public List<Inventory> findAllStockWithoutId() {
        return collection.find().projection(
                Projections.fields(
                        Projections.include("item","instock"),
                        Projections.excludeId())
        ).into(new ArrayList<>());
    }

    @PutMapping("updateOne")
    public UpdateResult updateOne(@RequestBody Document filtre){
        return collection.updateOne(filtre, Updates.inc("size.h",1));
    }


    @PutMapping("updateOneUpsert")
    public UpdateResult updateOneUpsert(@RequestBody UpdateDto updateDto){
        return collection.updateOne(updateDto.filter(), updateDto.update(), new UpdateOptions().upsert(true));
    }
    @PutMapping("updateMany")
    public UpdateResult updateMany(@RequestBody Document filtre){
        return collection.updateMany(filtre, Updates.inc("size.h",1));
    }


}
