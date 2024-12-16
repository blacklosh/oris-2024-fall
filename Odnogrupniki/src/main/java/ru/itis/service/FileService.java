package ru.itis.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.UUID;

public interface FileService {

    UUID uploadFile(Part part);

    void downloadFile(UUID id, HttpServletResponse response);

}
