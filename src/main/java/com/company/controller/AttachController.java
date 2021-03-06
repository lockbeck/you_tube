package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.service.AttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/attach")
@RestController
@Slf4j
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        log.info("Request for upload media {}",file);
        AttachDTO response = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/open/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("id") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                log.info("Request for open media {}",fileName);
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Failed for open media {}",fileName);
                return new byte[0];
            }
        }
        return null;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        log.info("Request for download media {}",fileName);
        return attachService.download(fileName);
//        return attachService.download(fileName);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }




    @DeleteMapping("/adm/delete/{fileName}")
    public ResponseEntity<String> delete(@PathVariable("fileName") String fileName) {
        log.info("Request for delete media {}",fileName);
        String response = attachService.delete(fileName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("adm/get")
    public ResponseEntity<?>getAll(@RequestParam(value = "page",defaultValue = "0")int page,
                                   @RequestParam(value = "size",defaultValue = "4")int size){
        log.info("Request to get all attach for Admin");
        PageImpl all = attachService.getAll(page, size);
        return ResponseEntity.ok(all);
    }


}
