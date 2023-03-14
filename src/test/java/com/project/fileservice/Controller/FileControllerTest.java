package com.project.fileservice.Controller;

import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.fileservice.Service.FileService;
import com.project.fileservice.Utils.CreateS3ObjectSummaryInstance;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class FileControllerTest {
    private AutoCloseable autoCloseable;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FileService fileService;
    @InjectMocks
    private FileController fileController;
    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        fileController = new FileController(fileService);
    }
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }
    @Test
    void listBucketItems() throws Exception {
        List<S3ObjectSummary> s3ObjectSummaryList = new ArrayList<>();
        Owner owner = new Owner("foostringid123132131","displayname");
        String storageClass = "storageClass";
        Date date = new Date();
        String key = "Captura_de_tela_de_2023-03-09_14-54-21.png";
        String eTag = "1709a4c539840ed6e30c5a9d227be0e7";
        String bucketName = "filemanagerpauloneves";
        long size = 130841L;

        S3ObjectSummary s3ObjectSumarry = CreateS3ObjectSummaryInstance.createS3ObjectSumarry(
                owner,
                storageClass,
                date,
                key,
                eTag,
                bucketName,
                size
        );
        s3ObjectSummaryList.add(s3ObjectSumarry);

        given(fileService.listBucketItems()).willReturn(s3ObjectSummaryList);

        MvcResult mvcResult = mockMvc
                .perform(get("/file"))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult
                .getResponse()
                .getContentAsString();

        assertThat(content)
                .contains("foostringid123132131")
                .contains("displayname")
                .contains("storageClass")
                .contains("Captura_de_tela_de_2023-03-09_14-54-21.png")
                .contains("1709a4c539840ed6e30c5a9d227be0e7")
                .contains("filemanagerpauloneves")
                .contains("130841");
    }

    @Test
    void downloadS3Object() throws Exception {

    }

    @Test
    void saveItem() {
    }

    @Test
    void deleteItem() {
    }
}