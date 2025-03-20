package com.supernova.ai.Controller.document;

import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.document.DocumentService;
import com.supernova.ai.Service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class PdfUploadController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SessionService sessionService;

    @Autowired
    DocumentService documentService;

    @PostMapping("/upload")
    public void uploadPdf(@RequestParam("file") MultipartFile file, @RequestParam("projectName") String projectName) throws IOException {

        documentService.upload(file, projectName);
    }

}
