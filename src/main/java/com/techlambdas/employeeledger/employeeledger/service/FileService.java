package com.techlambdas.employeeledger.employeeledger.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {
    void uploadFile(MultipartFile file) throws IOException;

    void getFile(String fileId, HttpServletResponse response) throws IOException;

    void saveFile(MultipartFile file) throws IOException;

    byte[] getImage(String filename) throws IOException;
}
