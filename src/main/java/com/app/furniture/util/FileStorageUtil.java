package com.app.furniture.util;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

@Component
@Log4j2
public class FileStorageUtil {

    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;

    public String saveFile(@NonNull MultipartFile sourceFile, String pathType, Integer userId) throws IOException {
        final String fileUploadSubPath = pathType + separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile sourceFile, @NonNull String fileUploadSubPath) {
        final String finalFilePath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalFilePath);
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.error("Unable to create folder {}", fileUploadPath);
                return null;
            }
        }
        String fileExtension = extractFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalFilePath + separator + currentTimeMillis() + '.' + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try (OutputStream outputStream = Files.newOutputStream(targetPath)) {
            outputStream.write(sourceFile.getBytes());
            log.info("Successfully uploaded file: " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return null;
    }

    private String extractFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    public static byte[] readFileToByteArray(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return null;
        }
        try {
            Path filePath = Paths.get(fileUrl);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("No file found in the path {}", fileUrl);
        }
        return null;
    }

}
