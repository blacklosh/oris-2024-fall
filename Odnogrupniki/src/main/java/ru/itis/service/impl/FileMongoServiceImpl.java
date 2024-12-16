package ru.itis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.itis.exception.FileNotFoundException;
import ru.itis.repository.MongoFileRepository;
import ru.itis.service.FileService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.OutputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class FileMongoServiceImpl implements FileService {

    private final MongoFileRepository mongoFileRepository;

    private final UUID defaultImageUuid;

    @Override
    @SneakyThrows
    public UUID uploadFile(Part part) {
        return mongoFileRepository.saveFile(part.getInputStream());
    }

    @Override
    @SneakyThrows
    public void downloadFile(UUID id, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        if(id == null) {
            id = defaultImageUuid;
        }
        try {
            byte[] data = mongoFileRepository.getFile(id);
            os.write(data);
        } catch (FileNotFoundException e) {
            byte[] data = mongoFileRepository.getFile(defaultImageUuid);
            os.write(data);
        }
    }
}
