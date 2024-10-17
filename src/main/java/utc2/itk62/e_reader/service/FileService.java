package utc2.itk62.e_reader.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    String uploadFile(MultipartFile file);

    boolean deleteFile(String fileName);

    File convertMultiartFileToFile(MultipartFile file);
}
