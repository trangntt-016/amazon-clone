package com.canada.aws.api.impl;

import com.canada.aws.api.ProductController;
import com.canada.aws.dto.ProductDto;
import com.canada.aws.model.Product;
import com.canada.aws.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductControllerImpl implements ProductController {
    @Autowired
    ProductServiceImpl productService;

    @Override
    @PostMapping()
    public ResponseEntity<?> createANewProduct(ProductDto productDto){
        try{
            Product product = productService.createAProduct(productDto);
            return ResponseEntity.ok(product);
        }
        catch(Exception ex){
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

//    @PostMapping("/new")
//    public ResponseEntity<String> handleFileUpload(
//            @RequestParam("mainImage") MultipartFile mainImageMultipart,
//            @RequestParam("extraImages") MultipartFile[] extraMultipartFiles,
//            ProductDto productDto) {
//        String message;
//        try {
//            try {
//                String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
//                String uploadDir = "../product-images/" + fileName;
//
////                FileUploadUtil.cleanDir(uploadDir);
//                Path dirPath = Paths.get(uploadDir);
//
//                try {
//                    Files.list(dirPath).forEach(fileD -> {
//                        if (!Files.isDirectory(fileD)) {
//                            try {
//                                Files.delete(fileD);
//                            } catch (IOException ex) {
//
//                            }
//                        }
//                    });
//                } catch (IOException ex) {
//
//                }
////                FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
//                Path uploadPath = Paths.get(uploadDir);
//
//                if (!Files.exists(uploadPath)) {
//                    Files.createDirectories(uploadPath);
//                }
//
//                try (InputStream inputStream = mainImageMultipart.getInputStream()) {
//                    Path filePath = uploadPath.resolve(fileName);
//                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//                } catch (IOException ex) {
//                    throw new IOException("Could not save file: " + fileName, ex);
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("FAIL!");
//            }
//            files.add(mainImageMultipart.getOriginalFilename());
//
//            message = "Successfully uploaded!";
//            return ResponseEntity.status(HttpStatus.OK).body(message);
//        } catch (Exception e) {
//            message = "Failed to upload!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
//        }
//    }
}
