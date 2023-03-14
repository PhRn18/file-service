package com.project.fileservice.Model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class FileDTOTests {
    public static final String fileName="foo";
    public static final Long contentLength= 100L;
    public static final String contentType = "foo2";
    @Test
    void isShouldReturnAFileDTOInstance(){
        byte[] inputStream = fileName.getBytes();
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(fileName);
        fileDTO.setContentLength(contentLength);
        fileDTO.setContentType(contentType);
        fileDTO.setInputStream(inputStream);
        assertThat(fileDTO.getFileName()).isEqualTo(fileName);
        assertThat(fileDTO.getContentLength()).isEqualTo(contentLength);
        assertThat(fileDTO.getContentType()).isEqualTo(contentType);
        assertThat(fileDTO.getInputStream()).isEqualTo(inputStream);
    }
}
