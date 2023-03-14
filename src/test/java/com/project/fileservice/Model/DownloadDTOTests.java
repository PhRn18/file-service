package com.project.fileservice.Model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DownloadDTOTests {
    @Test
    void itShouldCreateAnDownloadDTOInstance(){
        String test = "foo";
        DownloadDTO downloadDTO = new DownloadDTO(test.getBytes());
        assertThat(downloadDTO.getByteArray()).isEqualTo(test.getBytes());
    }
}
