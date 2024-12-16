package ru.itis.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.itis.exception.IncorrectFileTypeException;
import ru.itis.service.FileService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final String path;

    private final String defaultImageName;

    @Override
    @SneakyThrows
    public UUID uploadFile(Part part) {
        UUID uuid = UUID.randomUUID();
        if (part != null && part.getSize() > 0) {
            String contentType = part.getContentType();

            if (!contentType.equalsIgnoreCase("image/jpeg")) {
                throw new IncorrectFileTypeException();
            }

            part.write(path + File.separator + uuid + ".jpg");
            return uuid;
        }
        return null;
    }

    @Override
    @SneakyThrows
    public void downloadFile(UUID id, HttpServletResponse response) {
        File imageFile = null;
        if(id != null) {
            imageFile = new File(path + File.separator + id + ".jpg");
        }
        if(imageFile == null || !imageFile.exists()) {
            imageFile = new File(path + File.separator + defaultImageName + ".jpg");
        }
        FileInputStream fis = new FileInputStream(imageFile);
        OutputStream os = response.getOutputStream();

        response.setContentType("image/jpeg");
        //response.setContentLength((int) imageFile.getTotalSpace());

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        fis.close();
        os.close();
    }
}
