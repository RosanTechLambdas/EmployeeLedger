package com.techlambdas.employeeledger.employeeledger.controller;

import com.techlambdas.employeeledger.employeeledger.response.AppResponse;
import com.techlambdas.employeeledger.employeeledger.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file)  throws Exception{
        fileService.uploadFile(file);
        HashMap<String,Object> response = new HashMap<>();
        response.put("message","SucessFul to create");
        return AppResponse.successResponse(HttpStatus.OK,response);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFiles(@PathVariable String fileId, HttpServletResponse response) throws Exception{
        fileService.getFile(fileId,response);
        HashMap<String,Object> responses = new HashMap<>();
        responses.put("message","SucessFul to get");
        return AppResponse.successResponse(HttpStatus.OK,responses);
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<?> fileUpload(@RequestPart("file") MultipartFile file)  throws IOException {
        System.out.println("nndjonjnjdnjwndjownjosndwa");
        fileService.saveFile(file);
        HashMap<String,Object> response = new HashMap<>();
        response.put("message","SucessFully posted");
        return AppResponse.successResponse(HttpStatus.OK,response);
    }
    @GetMapping("/fileUpload/{filename}")
    public ResponseEntity<byte[]> fileUpload(@PathVariable String filename) throws IOException {
        byte[] fileBytes = fileService.getImage(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\" " + filename + "\"")
                .body(fileBytes);
    }

}
