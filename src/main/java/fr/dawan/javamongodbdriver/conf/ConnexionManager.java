package fr.dawan.javamongodbdriver.conf;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

public class ConnexionManager {
    // "mongodb://<username>:<password>@<hostname>:<port>/?authSource=<authenticationDb>"
    private static final ConnectionString CONNECTION_STRING = new ConnectionString("mongodb://localhost:27017");
    private static final MongoCredential MONGO_CREDENTIAL = MongoCredential.createCredential("","", "".toCharArray());
    private static String DB_NAME = "java-mongo-driverjava-mongo-driver";
    private static MongoClient client;

    public static MongoClient getClient() {
        if (client == null) client = MongoClients.create(CONNECTION_STRING);
        return client;
    }

    public static MongoClient getClientByCredential() {
        if (client == null) client = MongoClients.create(MongoClientSettings.builder()
                        .applyConnectionString(CONNECTION_STRING)
                .credential(MONGO_CREDENTIAL)
                .build());
        return client;
    }

    public static MongoIterable<String> getCollectionList() {
        return getClient().getDatabase(DB_NAME).listCollectionNames();
    }

    // A ne pas exposer
    public static MongoIterable<String> getDatabaseList() {
        return getClient().listDatabaseNames();
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        return getClient().getDatabase(DB_NAME).getCollection(collectionName);
    }

}
