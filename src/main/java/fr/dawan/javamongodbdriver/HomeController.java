package fr.dawan.javamongodbdriver;

import com.mongodb.client.MongoIterable;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static fr.dawan.javamongodbdriver.conf.ConnexionManager.getCollectionList;

@RestController
public class HomeController {
    @GetMapping("/collections")
    @Tag(name = "COLLECTIONS")
    public MongoIterable<String> collections() {
        return getCollectionList();
    }
}
