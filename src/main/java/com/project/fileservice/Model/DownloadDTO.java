package com.project.fileservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class DownloadDTO {
    private byte[] byteArray;
}
