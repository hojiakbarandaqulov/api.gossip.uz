package api.giybat.uz.controller;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.attach.AttachDTO;
import api.giybat.uz.service.AttachService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {

    private final AttachService attachService;

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "upload api", description = "Api list attach create")
    public ResponseEntity<AttachDTO> create(@RequestParam("file") MultipartFile file) {
        log.info("upload attach  = {}", file.getOriginalFilename());
        AttachDTO response = attachService.upload(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }


}
