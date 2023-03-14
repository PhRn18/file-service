package com.project.fileservice.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.project.fileservice.Model.DownloadDTO;
import com.project.fileservice.Model.FileDTO;
import com.project.fileservice.Utils.CreateS3ObjectSummaryInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class FileServiceTest {
    @Value("${aws.bucket.name}")
    private String bucketName;
    @Mock
    private AmazonS3 s3client;
    private ListObjectsRequest listObjectsRequest;
    private AutoCloseable autoCloseable;
    @InjectMocks
    private FileService fileService;
    @BeforeEach
    void setUp() {
        listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName);
        autoCloseable = MockitoAnnotations.openMocks(this);
        fileService=new FileService(s3client,listObjectsRequest);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    @Test
    void listBucketItems() throws NoSuchFieldException, IllegalAccessException {
        List<S3ObjectSummary> s3ObjectSummaryList = new ArrayList<>();
        S3ObjectSummary s3ObjectSumarry = CreateS3ObjectSummaryInstance.createS3ObjectSumarry(
                new Owner("foostringid123132131","displayname"),
                "storageClass",
                new Date(),
                "Captura_de_tela_de_2023-03-09_14-54-21.png",
                "1709a4c539840ed6e30c5a9d227be0e7",
                "filemanagerpauloneves",
                130841
        );
        s3ObjectSummaryList.add(s3ObjectSumarry);
        ObjectListing objectListing = CreateS3ObjectSummaryInstance.createObjectListting(s3ObjectSummaryList);
        given(s3client.listObjects(listObjectsRequest)).willReturn(objectListing);

        List<S3ObjectSummary> s3ObjectSummaries = fileService.listBucketItems();
        assertThat(s3ObjectSummaries).isEqualTo(s3ObjectSummaryList);
    }

    @Test
    void downloadS3Object() {
        String key = "Captura_de_tela_de_2023-03-09_14-54-21.png";
        InputStream inputStream = new ByteArrayInputStream("foo".getBytes());
        S3Object s3Object = new S3Object();
        s3Object.setBucketName(bucketName);
        s3Object.setObjectContent(inputStream);
        given(s3client.getObject(bucketName, key)).willReturn(s3Object);
        DownloadDTO downloadDTO = fileService.downloadS3Object(key,bucketName);
        assertThat(downloadDTO.getByteArray())
                .isNotNull()
                .hasSizeGreaterThan(0);
    }

    @Test
    void saveItem() throws IOException {
        String filename="foo_file.pdf";
        Long contentLength = 132213L;
        InputStream inputStream = new ByteArrayInputStream(filename.getBytes());
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(filename);
        fileDTO.setContentLength(contentLength);
        fileDTO.setInputStream(inputStream.readAllBytes());
        fileDTO.setContentType("pdf");

        given(s3client.putObject(bucketName,filename,inputStream,new ObjectMetadata())).willReturn(null);
        boolean uploadResult = fileService.saveItem(fileDTO, bucketName);
        assertThat(uploadResult).isTrue();
    }

}