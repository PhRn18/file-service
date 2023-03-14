package com.project.fileservice.Controller;
import com.amazonaws.services.s3.model.*;
import com.project.fileservice.Model.DownloadDTO;
import com.project.fileservice.Model.FileDTO;
import com.project.fileservice.Service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    public FileController(FileService fileService){
        this.fileService=fileService;
    }
    @GetMapping("/ok")
    public ResponseEntity<?> isFileControllerOnline(){
        return ResponseEntity
                .ok()
                .build();
    }
    @GetMapping
    public ResponseEntity<List<S3ObjectSummary>> listBucketItems(){
        return ResponseEntity.ok(fileService.listBucketItems());
    }
    @GetMapping("/download")
    public ResponseEntity<DownloadDTO> downloadS3Object(@RequestParam String key){
        log.info("Baixando item...");
        if(key.isEmpty()){
            log.error("Chave do arquivo nula");
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        return ResponseEntity.ok(fileService.downloadS3Object(key,null));
    }
    @PostMapping
    public ResponseEntity<Boolean> saveItem(@RequestBody FileDTO fileDTO){
        log.info("Salvando arquivo na bucket...");
        return ResponseEntity
                .ok(fileService.saveItem(fileDTO,null));
    }
    @DeleteMapping
    public ResponseEntity<?> deleteItem(@RequestParam String key){
        if(key.isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        log.info("Deletando arquivo da bucket...");
        fileService.deleteItem(key);
        return ResponseEntity
                .ok()
                .build();
    }

}

