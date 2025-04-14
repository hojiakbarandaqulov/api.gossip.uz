package api.giybat.uz.service;

import api.giybat.uz.dto.ApiResponse;
import api.giybat.uz.dto.attach.AttachDTO;
import api.giybat.uz.entity.AttachEntity;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.AttachRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AttachService {
    @Value("${server.url}")
    private String serverUrl;

    @Value("${attach.upload.url}")
    public String attachUrl;

    private final AttachRepository attachRepository;

    public AttachService(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }

    private static final Map<String, Object> imageExtensionMap = new HashMap<>();

    static {
        imageExtensionMap.put("jpg", new Object());
        imageExtensionMap.put("png", new Object());
        imageExtensionMap.put("jpeg", new Object());
    }

    public AttachDTO upload(MultipartFile file) {
        if (file.isEmpty()) {
            log.warn("Attach error : file not found");
            throw new AppBadException("File not found");
        }

        String pathFolder = getYmDString();
        File folder = new File(attachUrl + "/" + pathFolder);
        if (!folder.exists()) {
            boolean t = folder.mkdirs();
        }

        String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            AttachEntity entity = new AttachEntity();
            entity.setId(key+"."+extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setOriginalName(file.getOriginalFilename());
            entity.setExtension(extension);
            attachRepository.save(entity);

            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUrl + "/" + pathFolder + "/" + entity.getId() + "." + extension);
            Files.write(path, bytes);
            return toDTO(entity);
        } catch (IOException e) {
            log.warn("Attach error : {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public byte[] open_general(String attachId) {
        byte[] data;
        try {
            AttachEntity entity = get(attachId);
            String path = entity.getPath() + "/" + attachId;
            Path file = Paths.get("uploads/" + path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private AttachEntity get(String attachId) {
        return attachRepository.findById(attachId).orElseThrow(() -> {
            throw new AppBadException("Attach not found");
        });
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2024/06/08
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setCreatedDate((entity.getCreatedDate()));
        dto.setExtension(entity.getExtension());
        dto.setPath(entity.getPath());
        dto.setSize(entity.getSize());
        dto.setOriginalName(entity.getOriginalName());
        dto.setUrl(serverUrl + "/attach/upload/" + entity.getId());
        return dto;
    }

}

