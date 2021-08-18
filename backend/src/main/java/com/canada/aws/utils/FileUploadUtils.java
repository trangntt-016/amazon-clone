package com.canada.aws.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

public class FileUploadUtils {
    public static void saveFile(String uploadDir, String filename, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try{
            InputStream inputStream = multipartFile.getInputStream();
            if(inputStream!=null){
                Path filePath = uploadPath.resolve(filename);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch(IOException ex){
            throw new IOException("Could not save file: " + filename, ex);
        }
    }

    public static void cleanDirectory(String directory){
        Path filePath = Paths.get(directory);

        try{
            Files.list(filePath).forEach(f -> {
                if(!Files.isDirectory(filePath)){
                    try{
                        Files.delete(f);
                    }
                    catch(IOException e){
                        LOGGER.error("Could not delete file: " + f);
                    }
                }
            });
        }
        catch(Exception ex){
            LOGGER.error("Could not list directory: " + filePath);
        }
    }

    public static void removeDir(String dir) {
        cleanDirectory(dir);

        try {
            Files.delete(Paths.get(dir));
        } catch (IOException e) {
            LOGGER.error("Could not remove directory: " + dir);
        }

    }

}
