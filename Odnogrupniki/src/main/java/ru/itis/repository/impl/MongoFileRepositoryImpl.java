package ru.itis.repository.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.Binary;
import ru.itis.exception.FileNotFoundException;
import ru.itis.repository.MongoFileRepository;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
public class MongoFileRepositoryImpl implements MongoFileRepository {

    private final MongoCollection<Document> mongoCollection;

    public MongoFileRepositoryImpl(MongoClient mongoClient) {
         mongoCollection = mongoClient.getDatabase("secondCourseDatabaseMongo")
                 .getCollection("files");
    }

    @Override
    @SneakyThrows
    public UUID saveFile(InputStream inputStream) {
        UUID id = UUID.randomUUID();
        Binary data = new Binary(inputStream.readAllBytes());
        Document document = new Document("file", data)
                .append("id", id.toString());
        mongoCollection.insertOne(document);
        return id;
    }

    @Override
    public byte[] getFile(UUID id) {
        Document selector = new Document("id", id.toString());
        Document result = mongoCollection.find(selector).first();
        if(result != null) {
            return result.get("file", Binary.class).getData();
        }
        throw new FileNotFoundException();
    }
}
