package com.techlambdas.employeeledger.employeeledger.service;

import com.techlambdas.employeeledger.employeeledger.repo.FileCustomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileCustomRepo fileCustomRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        fileCustomRepo.uploadFile(file);
    }

    @Override
    public void getFile(String fileId, HttpServletResponse response) throws IOException {
        GridFsResource resource=fileCustomRepo.getFile(fileId);
        response.setContentType(resource.getContentType());
        response.setHeader("Disposition","attachment; filename="+resource.getFilename());

        StreamUtils.copy(resource.getInputStream(),response.getOutputStream());
    }
    @Override
    public void saveFile(MultipartFile file) throws IOException {
        if(file==null){
            throw new IOException("Please Enter the File Name");
        }

        Path path = Paths.get(System.getProperty("user.dir"), uploadDir);
        if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Path filePath = path.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());
    }
    public byte[] getImage(String filename) throws IOException {
         Path path= Paths.get(System.getProperty("user.dir"),uploadDir,filename);
         byte[] bytes= Files.readAllBytes(path);
         return bytes;
    }
}
