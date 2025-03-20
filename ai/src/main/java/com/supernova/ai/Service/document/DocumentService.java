package com.supernova.ai.Service.document;

import com.supernova.ai.Entity.DocumentEntity;
import com.supernova.ai.Entity.ProjectEntity;
import com.supernova.ai.Entity.UsersEntity;
import com.supernova.ai.Repository.DocumentRepository;
import com.supernova.ai.Repository.ProjectsRepository;
import com.supernova.ai.Repository.admin.AdminRepository;
import com.supernova.ai.Service.session.SessionService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final VectorStore vectorStore;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SessionService sessionService;

    @Autowired
    DocumentRepository documentRepository;

    public DocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void upload(MultipartFile file , String projectName) throws IOException {

        UsersEntity usersEntity = adminRepository.findByEmail(sessionService.getAttribute("userEmail").toString()).get();

        ProjectEntity projectEntity = projectsRepository.findByProjectTitle(projectName).get();

        DocumentEntity documentEntity = new DocumentEntity();

        documentEntity.setUser(usersEntity);
        documentEntity.setProject(projectEntity);
        documentEntity.setTitle(file.getOriginalFilename());
        documentEntity.setCreatedAt(LocalDateTime.now());
        documentEntity.setUpdatedAt(LocalDateTime.now());

        DocumentEntity document =  documentRepository.save(documentEntity);

        processPdfFile(file, document.getDocumentId());
    }




    public void processPdfFile(MultipartFile file, Long docId) throws IOException {

        // Create a temporary file to store the uploaded PDF
        Path tempFile = Files.createTempFile("uploaded_pdf_", ".pdf");
        file.transferTo(tempFile.toFile());

        // Create a FileSystemResource from the temporary file
        Resource pdfResource = new FileSystemResource(tempFile.toFile());

        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder().withPagesPerDocument(1).build();

        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource, config);
        var textSplitter = new TokenTextSplitter(false);


        try {

            List<Document> doc = textSplitter.apply(pdfReader.get());

            vectorStore.accept(doc);

        } finally {
            // Clean up the temporary file
            Files.deleteIfExists(tempFile);
        }
    }

    private String generateDocumentId(String originalFilename) {
        // Remove file extension and sanitize the filename
        String baseName = originalFilename != null ?
                originalFilename.replaceFirst("[.][^.]+$", "") :
                "unnamed_document";

        // Add timestamp to ensure uniqueness
        return baseName + "_" + System.currentTimeMillis();
    }
}
