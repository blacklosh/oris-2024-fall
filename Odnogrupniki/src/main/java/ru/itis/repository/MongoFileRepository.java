package ru.itis.repository;

import java.io.InputStream;
import java.util.UUID;

public interface MongoFileRepository {

    UUID saveFile(InputStream inputStream);

    byte[] getFile(UUID id);

}
