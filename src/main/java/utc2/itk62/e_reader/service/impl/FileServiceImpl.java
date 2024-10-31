package utc2.itk62.e_reader.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import utc2.itk62.e_reader.core.error.Error;

import utc2.itk62.e_reader.service.FileService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Slf4j
@Service

public class FileServiceImpl implements FileService {

    @Value("${application.bucket.name}")
    private String bucketName;
    @Value("${cloud.aws.endpointUrl}")
    private String endpointUrl;

    private final S3Client s3Client;

    public FileServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        String fileUrl = "";
        File fileObj = convertMultiartFileToFile(file);
        String fileName = System.currentTimeMillis()+"_" + file.getOriginalFilename();
        fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
        try{
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .acl("public-read")
                            .build(),
                    RequestBody.fromFile(fileObj));
        }catch (Exception e){

        }


        fileObj.delete();
        return fileUrl;
    }

    @Override
    public boolean deleteFile(String fileName) {
        String[] parts = fileName.split("/");
        String fileNameObj = parts[parts.length - 1];
        boolean isSuccess = false;
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileNameObj)
                    .build());
            isSuccess = true;
        } catch (Exception e) {

        }
        return isSuccess;
    }

    private File convertMultiartFileToFile(MultipartFile file) {
            File convertFile = new File(file.getOriginalFilename());
            try(FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            } catch (IOException e) {
                log.error("Error converting multipartFile to file", e);
            }
            return convertFile;
    }
}
