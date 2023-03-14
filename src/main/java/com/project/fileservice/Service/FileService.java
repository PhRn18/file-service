package com.project.fileservice.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.project.fileservice.Exception.InvalidFileException;
import com.project.fileservice.Exception.S3ConnectionError;
import com.project.fileservice.Model.DownloadDTO;
import com.project.fileservice.Model.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileService {
    @Value("${aws.bucket.name}")
    private String bucketName;
    private final AmazonS3 s3client;
    private final ListObjectsRequest listS3Objects;
    private final List<S3ObjectSummary> s3ObjectSummaries = new ArrayList<>();

    public FileService(AmazonS3 amazonS3,ListObjectsRequest listObjectsRequest){
        this.s3client=amazonS3;
        this.listS3Objects=listObjectsRequest;
    }
    public List<S3ObjectSummary> listBucketItems(){
        log.info("Listando objetos da bucket....");
        try{
            List<S3ObjectSummary> objectSummaryList = s3client
                    .listObjects(listS3Objects)
                    .getObjectSummaries();
            if(this.s3ObjectSummaries.isEmpty()){
                s3ObjectSummaries.addAll(objectSummaryList);
                return objectSummaryList;
            }
            log.info("Pegando do in memory cache");
            return objectSummaryList;
        }catch (Exception e){
            throw new S3ConnectionError("Nao foi possivel se conectar a AWS e retornar a lista de objetos");
        }
    }
    public DownloadDTO downloadS3Object(String key,String overrideBucketName){
        log.info("Baixando objeto da S3...");
        String downloadBucketName = overrideBucketName!=null ? overrideBucketName:bucketName;
        try{
            S3Object s3Response = s3client.getObject(downloadBucketName, key);
            InputStream fileInputStream = s3Response.getObjectContent();
            return new DownloadDTO(fileInputStream.readAllBytes());
        }catch (Exception e){
            throw new S3ConnectionError("Nao foi possivel se conectar a AWS e realizar o download do objeto");
        }
    }
    public boolean saveItem(FileDTO fileDTO){
        log.info("Salvando item na bucket...");
        try{
            ObjectMetadata data = new ObjectMetadata();
            data.setContentType(fileDTO.getContentType());
            data.setContentLength(fileDTO.getContentLength());
            InputStream inputStream = new ByteArrayInputStream(fileDTO.getInputStream());
            String fileName = fileDTO.getFileName();
            s3client.putObject(bucketName,fileName, inputStream,data);
            return true;
        }
        catch (Exception e){
            throw new InvalidFileException("Invalid file");
        }
    }
    public void deleteItem(String key){
        log.info("Deletando item da bucket...");
        try{
            this.s3ObjectSummaries.forEach(s3ObjectSummary -> {
                if(s3ObjectSummary.getKey().equals(key)){
                    s3client.deleteObject(bucketName,key);
                    this.s3ObjectSummaries.remove(s3ObjectSummary);
                }
            });
        }catch (Exception e){
            throw new S3ConnectionError("Nao foi possivel se conectar com a AWS e deletar o item");
        }
    }
}
