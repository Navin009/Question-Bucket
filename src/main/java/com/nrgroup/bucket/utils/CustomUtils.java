package com.nrgroup.bucket.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUtils {
    public static String multiPartFileToString(MultipartFile file, String defaultString) {
        try {
            assert file != null;
            String result = new String(file.getBytes(), "UTF-8");
            return result;
        } catch (Exception e) {
            log.error("Error converting MultipartFile to String - {} - {}", e.getClass(), e.getMessage());
            return defaultString;
        }
    }

    public static String getResouceFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        if (file.exists()) {
            return filename;
        } else {
            String path = CustomUtils.class.getClassLoader().getResource(filename).getPath();
            if (path != null) {
                return path;
            } else {
                throw new FileNotFoundException("File not found: " + filename);
            }
        }
    }
}
