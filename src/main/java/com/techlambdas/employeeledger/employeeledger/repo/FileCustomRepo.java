package com.techlambdas.employeeledger.employeeledger.repo;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileCustomRepo {
    void uploadFile(MultipartFile file) throws IOException;

    GridFsResource getFile(String fileId);
}
